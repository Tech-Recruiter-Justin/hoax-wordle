package com.sandboxvr.wordle.entities;

import com.sandboxvr.wordle.exceptions.UnknownWordException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GuessEngineTests {
    private static String SINGLE_ANSWER = "HELLO";
    private static String ANSWERS = "HELLO,WORLD,QUITE,FANCY,FRESH,PANIC,CRAZY,BUGGY";
    private HoaxGuessEngine guessEngineWithSingleAnswer;
    private HoaxGuessEngine guessEngineWithMultiAnswers;

    @BeforeEach
    void setup(){
        guessEngineWithSingleAnswer = new HoaxGuessEngine(SINGLE_ANSWER, 5, 6);
        guessEngineWithMultiAnswers = new HoaxGuessEngine(ANSWERS, 5, 6);
    }

    @Test
    void Correct_Word_Is_Guessed_In_One_Round_Prints_Singular_Rounds_Taken_Msg() throws UnknownWordException {
        guessEngineWithSingleAnswer.guessWord("HELLO");
        String hint = guessEngineWithSingleAnswer.checkLetterHint("HELLO");
        assertEquals("XXXXX",hint);
    }

    @Test
    void Unknown_Word_Is_Guessed_Throws_Exception() {
        Exception e = assertThrows(UnknownWordException.class, () -> { guessEngineWithMultiAnswers.guessWord("MILOW"); });
        assertEquals("Unknown word", e.getMessage());
    }

    @Test
    void Find_Worst_Answers_After_Each_Guess_And_Return_Hints_Correctly_Example_1() throws UnknownWordException {
        makeAGuess("HELLO","_____","FANCY,PANIC,CRAZY,BUGGY","HELLO,WORLD,QUITE,FRESH",",");
        makeAGuess("QUITE","_____","FANCY,CRAZY","HELLO,WORLD,QUITE,FRESH,PANIC,BUGGY",",");
        makeAGuess("PANIC","_?__?","CRAZY","FANCY,HELLO,WORLD,QUITE,FRESH,PANIC,BUGGY",",");
        Exception e = assertThrows(UnknownWordException.class, () -> { makeAGuess("EMAIL","","","",""); });
        assertEquals("Unknown word", e.getMessage());
        makeAGuess("CRAZY","XXXXX","CRAZY","FANCY,HELLO,WORLD,QUITE,FRESH,PANIC,BUGGY",",");
        assertEquals("in 4 rounds.", guessEngineWithMultiAnswers.checkEndGameResult());
    }

    @Test
    void Find_Worst_Answers_After_Each_Guess_And_Return_Hints_Correctly_Example_2() throws UnknownWordException {
        makeAGuess("HELLO","_____","FANCY,PANIC,CRAZY,BUGGY","HELLO,WORLD,QUITE,FRESH",",");
        makeAGuess("WORLD","_____","FANCY,PANIC,BUGGY","HELLO,WORLD,QUITE,FRESH,CRAZY",",");
        makeAGuess("FRESH","_____","PANIC,BUGGY","HELLO,WORLD,QUITE,FRESH,CRAZY,FANCY",",");
        makeAGuess("CRAZY","?_?__","PANIC","HELLO,WORLD,QUITE,FRESH,CRAZY,FANCY,BUGGY",",");
        makeAGuess("QUITE","__?__","PANIC","HELLO,WORLD,QUITE,FRESH,CRAZY,FANCY,BUGGY",",");
        makeAGuess("FANCY","_XX?_","PANIC","HELLO,WORLD,QUITE,FRESH,CRAZY,FANCY,BUGGY",",");
        assertEquals("The correct word is PANIC.", guessEngineWithMultiAnswers.checkEndGameResult());
    }

    void makeAGuess(String word, String expected, String allRemaining, String allFiltered, String delimiter) throws UnknownWordException {
        guessEngineWithMultiAnswers.guessWord(word);
        String actual = guessEngineWithMultiAnswers.checkLetterHint(word);
        assertEquals(expected, actual);
        List<String> remainingAnswers = guessEngineWithMultiAnswers.getWorstAnswers();
        assertTrue(remainingAnswers.containsAll(Arrays.asList(allRemaining.split(delimiter))));
        List<String> filteredOut = Arrays.asList(allFiltered.split(delimiter));
        for (String out : filteredOut) {
            assertFalse(remainingAnswers.contains(out));
        }
    }

}