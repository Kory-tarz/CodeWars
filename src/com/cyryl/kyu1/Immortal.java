package com.cyryl.kyu1;

public class Immortal {
    /** set true to enable debug */
    static boolean debug = false;

    static long elderAge(long n, long m, long lost, long time) {
        if(n > m){
            long temp = n;
            n = m;
            m = temp;
        }


        return 0;
    }

    static long findSmallestPower(long number){
        long nextPower = 2*2-1;
        long power = 1;

        while(nextPower <= number){
            nextPower = ((nextPower + 1) << 1) - 1;
            power++;
        }
        return power;
    }





    static void printSomething(long n, long m){
        int[][] arr = new int[(int)n][(int)m];

        System.out.println("---------------");
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                arr[i][j] = i ^ j;
                System.out.print(arr[i][j]);
                if(arr[i][j]/10 == 0){
                    System.out.print("   ");
                }
                else if(arr[i][j]/100 == 0){
                    System.out.print("  ");
                }
                else if(arr[i][j]/1000 == 0){
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
        System.out.println("---------------");
    }
}
