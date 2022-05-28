package com.cyryl.kyu4;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Runes {
    public static int solveExpression( final String expression ) {
        final int CALCULATION = 0;
        final int ANSWER = 1;
        String missingNumbers = getMissingNumbers(expression);
        String leftExpression;
        String operator;
        String rightExpression;
        String answer;
        String testedNumber;

        String[] fullExpression = expression.split("=");

        String regex = "(\\-?[\\d\\?]+)([*+-])(.+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(fullExpression[CALCULATION]);

        matcher.find();

        leftExpression = matcher.group(1);
        operator = matcher.group(2);
        rightExpression = matcher.group(3);
        answer = fullExpression[ANSWER];

        if(!canBeZero(leftExpression) || !canBeZero(rightExpression) || !canBeZero(answer))
            missingNumbers = missingNumbers.replaceAll("0", "");

        for(int i=0; i < missingNumbers.length(); i++){
            testedNumber = Character.toString(missingNumbers.charAt(i));
            if(calculate(operator,
                    getNumber(leftExpression.replaceAll("\\?", testedNumber)),
                    getNumber(rightExpression.replaceAll("\\?", testedNumber)))
                ==
                    getNumber(answer.replaceAll("\\?", testedNumber))
            ){
                return Integer.parseInt(testedNumber);
            }
        }
        return -1;
    }

    public static int getNumber(String exp){
        int number = 0;
        int sign = 1;
        if (exp.startsWith("-")){
            sign = -1;
            exp=exp.substring(1, exp.length());
        }
        for(int i=exp.length()-1; i>=0; i--){
            number += Character.getNumericValue(exp.charAt(i)) * (int) Math.pow(10, exp.length()-1-i);
        }
        return number * sign;
    }

    public static int calculate(String operator, int a, int b){
        if(operator.equals("+"))
            return a+b;
        if(operator.equals("-"))
            return a-b;
        if(operator.equals("*"))
            return a*b;
        return -1;
    }

    public static String getMissingNumbers(String expression){
        String numbers = "0123456789";
        for(int i=0; i<expression.length();i++){
            if(Character.isDigit(expression.charAt(i)))
                numbers = numbers.replaceAll(Character.toString(expression.charAt(i)), "");
        }
        return numbers;
    }

    public static boolean canBeZero(String expression){
        System.out.println(expression);
        if(expression.startsWith("-?"))
            return false;
        if(expression.startsWith("?") && expression.length() > 1)
            return false;
        return true;
    }
}
