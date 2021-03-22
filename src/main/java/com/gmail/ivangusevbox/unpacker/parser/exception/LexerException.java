package com.gmail.ivangusevbox.unpacker.parser.exception;

public class LexerException extends Exception {
    public LexerException() {
        super("lexing error occurred");
    }

    public LexerException(String message) {
        super("lexing error occurred :: " + message);
    }
}
