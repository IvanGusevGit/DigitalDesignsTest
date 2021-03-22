package com.gmail.ivangusevbox.unpacker.parser.entity;

public class TokenData {
    public ExpressionToken expressionToken;
    public String text;

    public TokenData(ExpressionToken expressionToken, String text) {
        this.expressionToken = expressionToken;
        this.text = text;
    }
}
