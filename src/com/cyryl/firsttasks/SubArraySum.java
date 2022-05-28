package com.cyryl.firsttasks;

public class SubArraySum {
    public static int sequence(int[] arr) {
        int currentSum = 0;
        int maxSum = 0;
        for (int number : arr){
            currentSum += number;
            if (currentSum < 0){
                currentSum = 0;
            }else if (currentSum > maxSum){
                maxSum = currentSum;
            }
        }
        return maxSum;
    }
}
