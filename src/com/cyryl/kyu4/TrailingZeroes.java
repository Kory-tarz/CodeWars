package com.cyryl.kyu4;

import java.math.BigInteger;

public class TrailingZeroes {
    public static BigInteger trailingZeros(BigInteger num, BigInteger base) {
        BigInteger primeFactor = highestPrimeFactorial(base);
        BigInteger result = BigInteger.ZERO;

        if(num.compareTo(base) < 0)
            return primeFactor.compareTo(num) <= 0 ? BigInteger.ONE : BigInteger.ZERO;

        System.out.println(primeFactor);

        while (num.compareTo(BigInteger.ZERO) > 0){
            num = num.divide(primeFactor);
            System.out.println(num);
            result = result.add(num);
        }
        return result;
    }

    private static BigInteger highestPrimeFactorial(BigInteger num){
        BigInteger result = num;
        while (result.mod(BigInteger.TWO).equals(BigInteger.ZERO)){
            result = result.divide(BigInteger.TWO);
        }
        if (result.equals(BigInteger.ONE))
            return BigInteger.TWO;

        BigInteger sqrt = result.sqrt();
        BigInteger max = BigInteger.ONE;

        for(BigInteger i = BigInteger.valueOf(3); i.compareTo(sqrt) <= 0; i = i.add(BigInteger.TWO)){
            while (result.mod(i).equals(BigInteger.ZERO)){
                max = i;
                result = result.divide(i);
            }
        }
        return max.equals(BigInteger.ONE) ? result : max;
    }
}
