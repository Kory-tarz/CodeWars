package com.cyryl.kyu4;

public class NextSmallerNumber {
    public static long nextSmaller(long n)
    {
        char[] number = String.valueOf(n).toCharArray();
        char temp;
        int[] numberOfDigits = new int[10];
        int newFirstNumber;
        String sameStart;
        String differentEnd;

        System.out.println(n);

        for(int i = number.length-1; i > 0; i--){
            numberOfDigits[Character.getNumericValue(number[i])]++;
            if(number[i-1] > number[i]){
                newFirstNumber = findSmallestBiggerDigit(numberOfDigits, Character.getNumericValue(number[i-1]));
                if(newFirstNumber <= 0 && i-1 == 0)
                    return -1;

                numberOfDigits[newFirstNumber]--;
                numberOfDigits[Character.getNumericValue(number[i-1])]++;

                sameStart = String.valueOf(n).substring(0,i-1);
                differentEnd = buildNewNumber(numberOfDigits, newFirstNumber, number.length-i);

                return Long.parseLong(sameStart + differentEnd);
            }
        }
        return -1;
    }

    public static int findSmallestBiggerDigit(int[] digits, int number){
        for(int i=number-1; i>= 0; i--)
            if (digits[i] > 0)
                return i;
        return -1;
    }

    public static String buildNewNumber(int[] digits, int firstNumber, int length){
        StringBuilder result = new StringBuilder();
        result.append(firstNumber);
        int currentDigit = 9;
        while(length > 0){
            while(digits[currentDigit] > 0){
                result.append(currentDigit);
                length--;
                digits[currentDigit]--;
            }
            currentDigit--;
        }
        return result.toString();
    }
}
