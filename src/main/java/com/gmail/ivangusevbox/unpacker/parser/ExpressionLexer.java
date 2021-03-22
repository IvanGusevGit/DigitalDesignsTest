package com.gmail.ivangusevbox.unpacker.parser;

import com.gmail.ivangusevbox.unpacker.parser.entity.ExpressionToken;
import com.gmail.ivangusevbox.unpacker.parser.entity.TokenData;
import com.gmail.ivangusevbox.unpacker.parser.exception.LexerException;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionLexer {

    private String text;
    private Matcher matcher;
    private Map<ExpressionToken, Pattern> patternMap;

    public ExpressionLexer(final String text) {
        this.text = text;
        this.matcher = Pattern.compile("").matcher("");
        patternMap = new HashMap<>();
        patternMap.put(ExpressionToken.TEXT, Pattern.compile("[a-zA-Z][a-zA-Z]*"));
        patternMap.put(ExpressionToken.NUMBER, Pattern.compile("[0-9][0-9]*"));
        patternMap.put(ExpressionToken.OPENING_PARENTHESIS, Pattern.compile("[\\[]"));
        patternMap.put(ExpressionToken.CLOSING_PARENTHESIS, Pattern.compile("[\\]]"));
    }

    public TokenData getNext() throws LexerException {
        if (text.isEmpty()) {
            return new TokenData(ExpressionToken.END, "");
        }
        for (var token : ExpressionToken.values()) {
            var regex = patternMap.get(token);
            if (regex == null) {
                throw new LexerException("unable to process next token");
            }
            matcher.usePattern(regex);
            matcher.reset(text);
            if (matcher.lookingAt()) {
                var match = text.substring(0, matcher.end());
                text = text.substring(matcher.end());
                return new TokenData(token, match);
            }
        }
        throw new LexerException("unable to process next token");
    }

}
