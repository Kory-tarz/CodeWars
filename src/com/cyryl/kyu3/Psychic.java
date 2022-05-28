package com.cyryl.kyu3;

public class Psychic {

    static final double DOUBLE_UNIT = 0x1.0p-53;
    static final long addend = 0xBL;
    static final long multiplier = 0x5DEECE66DL;
    static final long mask = (1L << 48)-1;
    public static double guess() {

        double result = Math.random();
        long resultUndoubled = (long)(result/DOUBLE_UNIT);
        int firstPart = (int) (resultUndoubled >> 27); // 26 bits .. 48 - 26 = 22;
        int secondPart = (int) (resultUndoubled & ((1 << 27)-1)); // 27 bits .. 48 - 27 = 21;


        long oldSeed = brutForceSeed(firstPart, secondPart);
        long currSeed = getNextSeed(oldSeed);

        return calculateNextNum(currSeed);
    }

    private static long getNextSeed(long oldSeed){
        return (oldSeed * multiplier + addend) & mask;
    }

    private static int next(int bits, long seed){
        long nextseed;
        nextseed = (seed * multiplier + addend) & mask;
        return (int)(nextseed >>> (48 - bits));
    }

    private static double calculateNextNum(long seed){
        return (((long)(next(26, seed)) << 27) + next(27, getNextSeed(seed))) * DOUBLE_UNIT;
    }

    private static long brutForceSeed(int firstNum, int secondNum){

        long oldSeedBeg = (long)firstNum << 22;
        long seed;

        for(long i=0; i<(1 << 22); i++){
            seed = oldSeedBeg | i;
            if(((seed * multiplier + addend) & mask) >> 21 == secondNum)
                return seed;
        }
        return -1;
    }
}
