package com.cyryl.firsttasks;

import org.junit.Test;

import static org.junit.Assert.*;

public class ParenthesesTest {

    @Test
    public void sampleTest() {
        // assertEquals("expected", "actual");
        assertEquals(true,Parentheses.validParentheses( "()" ));
        assertEquals(false,Parentheses.validParentheses( "())" ));
        assertEquals(true,Parentheses.validParentheses( "32423(sgsdg)" ));
        assertEquals(false,Parentheses.validParentheses( "(dsgdsg))2432" ));
        assertEquals(true,Parentheses.validParentheses( "adasdasfa" ));
    }
}
