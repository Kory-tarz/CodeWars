package com.cyryl.kyu2;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class BreakPiecesTest {

    @Test
    public void simpleTest() {
        String shape = String.join("\n", new String[]
                {"+------------+",
                "|            |",
                "|            |",
                "|            |",
                "+------+-----+",
                "|      |     |",
                "|      |     |",
                "+------+-----+"});
        String expected[] = {String.join("\n", new String[]
                {"+------------+",
                "|            |",
                "|            |",
                "|            |",
                "+------------+"}),
                String.join("\n", new String[]
                        {"+------+",
                        "|      |",
                        "|      |",
                        "+------+"}),
                String.join("\n", new String[]
                        {"+-----+",
                        "|     |",
                        "|     |",
                        "+-----+"})};
        String actual[] = BreakPieces.process(shape);
        Arrays.sort(expected);
        Arrays.sort(actual);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void emptySpaceTest() {
        String shape = String.join("\n", new String[]
                       {"+-------------------+   \n" +
                        "|                   |   \n" +
                        "|                   |   \n" +
                        "|  +----------------+   \n" +
                        "|  |                    \n" +
                        "|  |                    \n" +
                        "+--+                    \n" +
                        "                    +--+\n" +
                        "                    |  |\n" +
                        "                    |  |\n" +
                        "   +----------------+  |\n" +
                        "   |                   |\n" +
                        "   |                   |\n" +
                        "   +-------------------+"});
        String expected[] = {String.join("\n", new String[]
                       {"                 +--+\n" +
                        "                 |  |\n" +
                        "                 |  |\n" +
                        "+----------------+  |\n" +
                        "|                   |\n" +
                        "|                   |\n" +
                        "+-------------------+"}),
                String.join("\n", new String[]
                               {"+-------------------+\n" +
                                "|                   |\n" +
                                "|                   |\n" +
                                "|  +----------------+\n" +
                                "|  |\n" +
                                "|  |\n" +
                                "+--+"})};
        String actual[] = BreakPieces.process(shape);
        Arrays.sort(expected);
        Arrays.sort(actual);
        Assert.assertEquals(expected, actual);
    }


}