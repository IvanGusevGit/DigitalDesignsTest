package com.gmail.ivangusevbox.unpacker.parser;

import com.gmail.ivangusevbox.unpacker.parser.entity.ExpressionToken;
import com.gmail.ivangusevbox.unpacker.parser.entity.TokenData;
import com.gmail.ivangusevbox.unpacker.parser.exception.LexerException;
import com.gmail.ivangusevbox.unpacker.parser.exception.ParserException;

public class ExpressionParser {

    private ExpressionLexer lexer;
    private TokenData currentToken;

    public ExpressionParser(String text) {
        lexer = new ExpressionLexer(text);
        currentToken = null;
    }

    public String parse() throws ParserException {
        try {
            currentToken = lexer.getNext();
            String result = parseLine();
            if (currentToken.expressionToken != ExpressionToken.END) {
                throw new ParserException("expected token END, but found " + currentToken.expressionToken.name());
            }
            return result;
        } catch (LexerException e) {
            throw new ParserException(e.getMessage());
        }
    }

    private String parseLine() throws ParserException, LexerException {
        String result = "";
        switch (currentToken.expressionToken) {
            case TEXT:
            case NUMBER:
                result = parseContent();
                break;
            case CLOSING_PARENTHESIS:
            case END:
                break;
            default:
                throw new ParserException("expected tokens TEXT, NUMBER, but found "
                        + currentToken.expressionToken.name());
        }
        return result;
    }

    private String parseContent() throws ParserException, LexerException {
        String result = "";
        switch (currentToken.expressionToken) {
            case TEXT:
                String text = currentToken.text;
                currentToken = lexer.getNext();
                String ending = parseLine();
                result = text + ending;
                break;
            case NUMBER:
                long count = Long.parseLong(currentToken.text);
                currentToken = lexer.getNext();
                if (currentToken.expressionToken != ExpressionToken.OPENING_PARENTHESIS) {
                    throw new ParserException("expected token OPENING_PARENTHESIS, but found " +
                            currentToken.expressionToken.name());
                }
                currentToken = lexer.getNext();
                String content = parseContent();
                if (currentToken.expressionToken != ExpressionToken.CLOSING_PARENTHESIS) {
                    throw new ParserException("expected token CLOSING_PARENTHESIS, but found " +
                            currentToken.expressionToken.name());
                }
                currentToken = lexer.getNext();
                ending = parseLine();
                StringBuilder builder = new StringBuilder();
                for (long i = 0; i < count; i++) {
                    builder.append(content);
                }
                result = builder.append(ending).toString();
                break;
        }
        return result;
    }
}
