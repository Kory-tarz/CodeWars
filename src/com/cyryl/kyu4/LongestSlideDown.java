package com.cyryl.kyu4;

import java.util.stream.IntStream;

public class LongestSlideDown {

    public static int longestSlideDown(int[][] pyramid) {

        IntStream
                .iterate(pyramid.length - 1, i -> i-1)
                .limit(pyramid.length - 1)
                .forEach(i -> IntStream
                        .rangeClosed(0, i-1)
                        .forEach(j ->
                                pyramid[i-1][j] += Math.max(pyramid[i][j], pyramid[i][j+1])
                        )
                );

        IntStream
                .iterate(0, i->i+1)
                .limit(pyramid.length - 1)
                .forEach(i -> {
                    IntStream.rangeClosed(0, i)
                                    .forEach(j -> System.out.print(pyramid[i][j] + " "));
                    System.out.println();
                    }
                );

        return pyramid[0][0];


//        for (int i = pyramid.length - 1; i > 0; i--) {
//            for (int j = 0; j < pyramid[i].length - 1; j++) {
//                pyramid[i-1][j] += Math.max(pyramid[i][j], pyramid[i][j+1]);
//            }
//        }
//        return pyramid[0][0];
    }


}
