package com.sandboxvr.wordle.readers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TxtAnswerReader {

    private static String file;
    private static Scanner scanner;
    private static int wordLen;

    public TxtAnswerReader(String file, String delimiter, int wordLen) throws FileNotFoundException {
        this.file = file;
        this.scanner = new Scanner(new File(this.file));
        this.scanner.useDelimiter(delimiter);
        this.wordLen = wordLen;
    }

    public String readAllAnswers() {
        int line = 1;
        StringBuilder sb = new StringBuilder();
        while(scanner.hasNext()) {
            String nextLine = scanner.nextLine();
            if (!nextLine.trim().matches("[a-zA-Z]{" + wordLen +"}")) {
                throw new InputMismatchException("Answers in TXT file must only contain a-z A-Z characters and be " + wordLen + " characters long. Check line " + line + "." );
            }
            sb.append(nextLine);
            sb.append(",");
            line++;
        }
        return sb.toString();
    }
}
