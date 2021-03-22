package com.gmail.ivangusevbox.unpacker;

import com.gmail.ivangusevbox.unpacker.parser.ExpressionParser;
import com.gmail.ivangusevbox.unpacker.parser.exception.ParserException;

public class Unpacker {

    public static String unpack(String text) throws ParserException {
        return new ExpressionParser(text).parse();
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Expected 1 argument: <packed string>");
            return;
        }
        String text = args[0];
        try {
            String result = unpack(text);
            System.out.println(result);
        } catch (ParserException e) {
            System.err.println(e.getMessage());
        }
    }
}
