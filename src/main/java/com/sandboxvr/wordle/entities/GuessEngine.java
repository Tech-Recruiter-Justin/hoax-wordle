package com.sandboxvr.wordle.entities;

import com.sandboxvr.wordle.exceptions.UnknownWordException;

public interface GuessEngine {
    void guessWord(String word) throws UnknownWordException;

    String checkLetterHint(String word);

    boolean checkEndGame();

    String checkEndGameResult();

}
