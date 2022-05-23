package com.sandboxvr.wordle.entities;

import com.sandboxvr.wordle.exceptions.EmptyAnswerException;
import com.sandboxvr.wordle.exceptions.UnknownWordException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HoaxGuessEngine implements GuessEngine {
    private static int WORD_LENGTH = 5;
    private static int MAX_GUESS = 6;

    private boolean endGame = false;
    private boolean won = false;
    private List<String> allAnswers;
    private List<String> worstAnswers;
    private int guessCount = 0;

    public HoaxGuessEngine(String answers, int wordLen, int maxGuess) throws EmptyAnswerException {
        this.WORD_LENGTH = wordLen;
        this.MAX_GUESS = maxGuess;
        this.allAnswers = Arrays.asList(answers.toUpperCase().split(","));
        this.worstAnswers = this.allAnswers;
        if (answers == "" || allAnswers.get(0) == "") {
            throw new EmptyAnswerException();
        }
    }

    public void guessWord(String word) throws UnknownWordException {
        word = word.toUpperCase();
        if (!isValid(word)) {
            throw new UnknownWordException();
        }
        incrementGuessCountAndMarkEndGame();
        if (worstAnswers.size() == 1 && word.equals(worstAnswers.get(0))) {
            endGame = true;
            won = true;
        }
        if (worstAnswers.size() > 1) {
            findWorstPossibleAnswers(word);
        }
    }

    private boolean isValid(String word) {
        return allAnswers.contains(word) ? true : false;
    }

    private void incrementGuessCountAndMarkEndGame() {
        guessCount++;
        if (guessCount >= MAX_GUESS) endGame = true;
    }

    private void findWorstPossibleAnswers(String word) {
        findWorstPossibleAnswersByLeastHits(word);
        findWorstPossibleAnswersByLeastNearHit(word);
    }

    private void findWorstPossibleAnswersByLeastHits(String word) {
        int leastHits = WORD_LENGTH;
        int hits = 0;
        List<String> filteredAnswers = new ArrayList<>();
        for (String answer : this.worstAnswers) {
            for (int i = 0; i < WORD_LENGTH; i++) {
                if (word.charAt(i) == answer.charAt(i)) hits++;
            }
            if (hits < leastHits) {
                leastHits = hits;
                filteredAnswers.clear();
            }
            if (hits == leastHits) {
                filteredAnswers.add(answer);
            }
            hits = 0;
        }
        this.worstAnswers = filteredAnswers;
    }

    private void findWorstPossibleAnswersByLeastNearHit(String word) {
        int leastNearHit = 6;
        int nearHit = 0;
        List<String> filteredAnswers = new ArrayList<>();
        for (String answer : this.worstAnswers) {
            for (int i = 0; i < 5; i++) {
                if (answer.matches(".*" + word.charAt(i) + ".*")) nearHit++;
            }
            if (nearHit < leastNearHit) {
                leastNearHit = nearHit;
                filteredAnswers.clear();
            }
            if (nearHit == leastNearHit) {
                filteredAnswers.add(answer);
            }
            nearHit = 0;
        }
        this.worstAnswers = filteredAnswers;
    }

    protected List<String> getWorstAnswers() {
        return worstAnswers;
    }

    public String checkLetterHint(String word) {
        word = word.toUpperCase();
        String answer = worstAnswers.get(0);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < WORD_LENGTH; i++) {
            char current = word.charAt(i);
            if (current == answer.charAt(i)) sb.append("X");
            else if (answer.matches(".*" + current + ".*")) sb.append("?");
            else sb.append("_");
        }
        return sb.toString();
    }

    public boolean checkEndGame() {
        return endGame;
    }

    public String checkEndGameResult() {
        if (won) {
            String reply = "in " + guessCount + " round";
            if (guessCount > 1) { return reply + "s."; }
            return reply + ".";
        }
        return "The correct word is " + worstAnswers.get(0) + ".";
    }

}
