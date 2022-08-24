package com.cyryl.kyu4;

import java.util.Arrays;
import java.util.Comparator;

public class SumOfIntervals {

    static final int LEFT = 0;
    static final int RIGHT = 1;

    public static int sumIntervals(int[][] intervals) {
        if(intervals == null || intervals.length == 0)
            return 0;

        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
        int sum = 0;
        int left = intervals[0][LEFT];
        int right = intervals[0][RIGHT];

        for (int i = 1; i < intervals.length; i++) {
            if(intervals[i][LEFT] > right){
                sum += right - left;
                right = intervals[i][RIGHT];
                left = intervals[i][LEFT];
            }else{
                right = Math.max(intervals[i][RIGHT], right);
            }
        }
        sum += right - left;
        return sum;
    }
}
