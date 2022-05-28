package com.cyryl.firsttasks;

public class RowSumOddNumbers {
    public static int rowSumOddNumbers(int n) {
        int numberOfOddNumbers = (n-1)*n/2;
        int nextOddNumber = numberOfOddNumbers*2 + 1;
        int sumOnNRow = 0;
        for(int i=0; i < n; i++){
            sumOnNRow += nextOddNumber;
            nextOddNumber += 2;
        }
        return sumOnNRow;
    }
}
