package com.cyryl.firsttasks;

public class Parentheses {
    public static boolean validParentheses(String parens) {
        boolean valid = true;
        int calculation = 0;
        int i=0;

        while(i<parens.length() && valid) {
            if (parens.charAt(i) == '(') {
                calculation++;
            } else if (parens.charAt(i) == ')') {
                calculation--;
                if (calculation < 0) {
                    valid = false;
                }
            }
            i++;
        }
        return (calculation == 0 && valid);
    }
}
