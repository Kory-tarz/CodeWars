package com.cyryl.firsttasks;

import java.util.Scanner;

public class SplitStrings {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String stringToSplit = scanner.next();
        String[] solutionArray = solution(stringToSplit);
        for(int i=0; i<solutionArray.length; i++){
            System.out.println(solutionArray[i]);
        }
    }

    public static String[] solution(String s){

        String stringToSplit = s;
        int stringLength;
        int firstIndex;
        int lastIndex;
        final int SPLIT_LENGTH = 2;
        int solutionArrayLength;
        int solutionIndex = 0;
        String[] solutionArray;

        stringLength = stringToSplit.length();
        solutionArrayLength = (int) Math.ceil(stringLength/2.0);
        solutionArray = new String[solutionArrayLength];

        if(stringLength == Integer.MAX_VALUE){
            solutionArray[solutionArrayLength-1] = stringToSplit.substring(stringLength-1,stringLength) + '_';
            stringLength--;
        }else if(stringLength % 2 != 0){
            stringToSplit = stringToSplit + '_';
            stringLength++;
        }

        firstIndex = 0;
        lastIndex = firstIndex + SPLIT_LENGTH;

        while(lastIndex <= stringLength){
            solutionArray[solutionIndex] = (stringToSplit.substring(firstIndex, lastIndex));
            firstIndex += SPLIT_LENGTH;
            lastIndex += SPLIT_LENGTH;
            solutionIndex++;
        }
        return solutionArray;
    }
}
