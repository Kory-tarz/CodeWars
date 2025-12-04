package com.cyryl.kyu1.roboscript;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class RoboScriptRS1Test {
    @Test
    public void sampleTests() {
        assertPathEquals("", "*");
        assertPathEquals("FFFFF", "******");
        assertPathEquals("FFFFFLFFFFFLFFFFFLFFFFFL", "******\r\n*    *\r\n*    *\r\n*    *\r\n*    *\r\n******");
        assertPathEquals("LFFFFFRFFFRFFFRFFFFFFF", "    ****\r\n    *  *\r\n    *  *\r\n********\r\n    *   \r\n    *   ");
        assertPathEquals("LF5RF3RF3RF7", "    ****\r\n    *  *\r\n    *  *\r\n********\r\n    *   \r\n    *   ");
    }


    private static void assertPathEquals(String code, String expected) {
        String  actual   = RoboScriptRS1.execute(code);
        boolean areEqual = expected.equals(actual);

        if (!areEqual) {
            System.out.println(String.format(
                    "--------------\nYou returned:\n%s\nExpected path of MyRobot:\n%s\n--------------\n",
                    actual, expected));
        }
        assertTrue("Nope...", areEqual);
    }
}
