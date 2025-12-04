package com.cyryl.kyu1.roboscript;

import java.util.Map;
import java.util.Objects;

public class SyntaxHighlighting {
    private static final Map<Character, String> LETTER_COLORS = Map.of(
            'F', "pink",
            'L', "red",
            'R', "green"
    );

    private static final String DIGIT_COLOR = "orange";

    private static final String HIGHLIGHT = "<span style=\"color: %s\">%s</span>";

    public static String highlight(String code) {
        StringBuilder resultBuilder = new StringBuilder();
        int currCode = 0;
        while (currCode < code.length()){
            char c = code.charAt(currCode);
            String color = Character.isDigit(c) ? DIGIT_COLOR : LETTER_COLORS.get(c);
            if (color == null) {
                currCode++;
                resultBuilder.append(c);
            } else {
                StringBuilder currHighlight = new StringBuilder().append(c);
                while (currCode + 1 < code.length() && Objects.equals(getColor(code.charAt(currCode + 1)), color)) {
                    currCode++;
                    c = code.charAt(currCode);
                    currHighlight.append(c);
                }
                resultBuilder.append(String.format(HIGHLIGHT, color, currHighlight));
            }
            currCode++;
        }
        return resultBuilder.toString();
    }

    private static String getColor(char c) {
        return Character.isDigit(c) ? DIGIT_COLOR : LETTER_COLORS.get(c);
    }

}
