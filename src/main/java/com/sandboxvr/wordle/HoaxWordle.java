package com.sandboxvr.wordle;

import com.sandboxvr.wordle.entities.GuessEngine;
import com.sandboxvr.wordle.entities.HoaxGuessEngine;
import com.sandboxvr.wordle.exceptions.UnknownWordException;
import com.sandboxvr.wordle.readers.TxtAnswerReader;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class HoaxWordle {
	private static final String GUESS_PROMPT = "Please guess a word";
	static Scanner userInput = new Scanner(System.in);
	static boolean endGame = false;

	public static void main(String[] args) throws FileNotFoundException {
		String fileAbsolutePath = args[0];
		String txtDelimiter = args[1];
		int wordLen = Integer.parseInt(args[2]);
		int maxGuess = Integer.parseInt(args[3]);

		TxtAnswerReader answerReader = new TxtAnswerReader(fileAbsolutePath, txtDelimiter, wordLen);
		GuessEngine guessEngine = new HoaxGuessEngine(answerReader.readAllAnswers(), wordLen, maxGuess);

		System.out.println(GUESS_PROMPT);
		while (endGame == false) {
			String guess = userInput.next();
			try {
				guessEngine.guessWord(guess);
			} catch (UnknownWordException e) {
				System.out.println(e.getMessage());
				continue;
			}
			System.out.println(guessEngine.checkLetterHint(guess));
			endGame = guessEngine.checkEndGame();
		}
		System.out.println(guessEngine.checkEndGameResult());
	}

}
