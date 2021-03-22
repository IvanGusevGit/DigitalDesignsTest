package com.gmail.ivangusevbox.unpacker.parser.exception;

public class ParserException extends Exception {
    public ParserException() {
        super("parsing error occurred");
    }

    public ParserException(String message) {
        super("parsing error occurred :: " + message);
    }
}
