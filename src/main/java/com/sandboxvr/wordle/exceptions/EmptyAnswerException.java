package com.sandboxvr.wordle.exceptions;

public class EmptyAnswerException extends Exception{
    public EmptyAnswerException() { super("There is no valid answer in the word bank. Please add at least one valid word to the txt before running the app again."); }
}
