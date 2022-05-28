package com.cyryl.kyu4;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StripCommentsTest {

    @Test
    public void innyWtf(){
        assertEquals(
                "a\nc\nd",
                StripComments.stripComments( "a #b\nc\nd $e f g", new String[] { "#", "$" } )
        );
    }

    @Test
    public void wtfTest(){
        assertEquals(
                "ddc\n" +
                        "c\n" +
                        "\n" +
                        "fedc\n" +
                        "",
                StripComments.stripComments(
                        "ddc-\n" +
                                "c-abbd\n" +
                                "-a--\n" +
                                "fedc\n" +
                                "--", new String[] { "#", "!", "-", "$" } )
        );
    }

    @Test
    public void shouldFail(){
        assertEquals(
                "<a\n" +
                        "[ ]b\n" +
                        "c>",
                StripComments.stripComments("<a\n[ ]b\nc>", new String[] {"%", "&"})
        );

        assertEquals(
                "fec\n" +
                        "\n" +
                        "d\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "d\n" +
                        "\n" +
                        "ebeb[\n" +
                        "\n" +
                        "]",
                StripComments.stripComments("fec\n" +
                        "\n" +
                        "d\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "d\n" +
                        "\n" +
                        "ebeb[\n" +
                        "\n" +
                        "]", new String[] {"%", "&"})
        );
    }

    @Test
    public void stripComments() throws Exception {
        assertEquals(
                "apples, pears\ngrapes\nbananas",
                StripComments.stripComments( "apples, pears # and bananas\ngrapes\nbananas !apples", new String[] { "#", "!" } )
        );


        assertEquals(
                "",
                StripComments.stripComments("", new String[] {"%", "&"})
        );

        assertEquals(
                "",
                StripComments.stripComments(" ", new String[] {"%", "&"})
        );

        assertEquals(
                "a   a",
                StripComments.stripComments("a   a  %b", new String[] {"%", "&"})
        );

        assertEquals(
                "a\n\n\nb",
                StripComments.stripComments("a\n\n\nb", new String[] {"%", "&"})
        );

    }

}