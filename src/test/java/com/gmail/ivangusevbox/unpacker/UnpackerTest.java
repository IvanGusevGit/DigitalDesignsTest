package com.gmail.ivangusevbox.unpacker;

import com.gmail.ivangusevbox.unpacker.parser.exception.ParserException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnpackerTest {

    public void testOK(String expected, String input) {
        assertDoesNotThrow(() -> {
            String result = Unpacker.unpack(input);
            assertEquals(expected, result);
        });
    }

    public void testFail(String string) {
        Exception e = assertThrows(ParserException.class, () -> {
            Unpacker.unpack(string);
        });
    }

    @Test
    public void testCorrect() {
        testOK("abacaba", "abacaba");
        testOK("aa", "2[a]");
        testOK("xtrtrtrz", "x3[tr]z");
        testOK("abbbbc", "a2[2[b]]c");
        testOK("AbcXXXcXXX", "Ab2[c3[X]]");
        testOK("aaaaaaaaaa", "10[a]");
    }

    @Test
    public void testInvalidInsertion() {
        testFail("2[");
        testFail("a22[b]]c");
        testFail("sdghsdg[[]]");
        testFail("AB233[a[s]]");
    }

    @Test
    public void testUnexpectedCharacter() {
        testFail("2[ывыв$%^]");
        testFail("abab#$#sdds");
    }
}