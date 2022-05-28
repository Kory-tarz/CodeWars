package com.cyryl.kyu3;

import java.math.BigInteger;
import java.util.SortedMap;
import java.util.TreeMap;

public class AlphabeticAnagrams {

    public BigInteger listPosition(String word) {

        BigInteger rank = BigInteger.ONE;
        BigInteger suffixPermCount = BigInteger.ONE;
        java.util.Map<Character, BigInteger> charCounts =
                new java.util.HashMap<>();
        for (int i = word.length() - 1; i > -1; i--) {
            char x = word.charAt(i);
            System.out.println("CHARACTER: " + x);
            BigInteger xCount = charCounts.containsKey(x) ? charCounts.get(x).add(BigInteger.ONE) : BigInteger.ONE;
            charCounts.put(x, xCount);
            for (java.util.Map.Entry<Character, BigInteger> e : charCounts.entrySet()) {
                if (e.getKey() < x) {
                    System.out.println("rankup");
                    rank = rank.add(suffixPermCount.multiply(e.getValue()).divide(xCount));
                }
                System.out.println(rank);
            }
            suffixPermCount = suffixPermCount.multiply(BigInteger.valueOf(word.length() - i));
            System.out.println("SUFFIX: " + suffixPermCount);
            suffixPermCount = suffixPermCount.divide(xCount);
            System.out.println("SUFFIX: " + suffixPermCount);
        }
        return rank;

    }

//    public BigInteger listPosition(String word) {
//        SortedMap<Integer, Integer> map = new TreeMap<>();
//        BigInteger result = BigInteger.ZERO;
//        int count = 0;
//
//        for(int i=word.length()-1; i>=0; i--){
//
//            result = result.add(partialPosition(map, charToInt(word.charAt(i)), count));
//            count++;
//        }
//
//        return result;
//    }

    private BigInteger partialPosition(SortedMap<Integer, Integer> map, int newElement, int elementCount){

        if(map.isEmpty()){
            map.put(newElement, 1);
            return BigInteger.ONE;
         }

        BigInteger result;
        int newElementPos;
        BigInteger bottomResult = BigInteger.ONE;
        BigInteger multiplier = BigInteger.ZERO;
        BigInteger notResults;

        if(!map.containsKey(newElement))
            map.put(newElement, 1);
        else
            map.put(newElement, map.get(newElement)+1);

        newElementPos = getElementPosition(map, newElement);

        if(newElementPos == 0)
            return BigInteger.ZERO;

        for(int key : map.keySet()){

            if(key == newElement)
                break;

            for(int key2 : map.keySet()) {
                if (key != key2)
                    bottomResult = bottomResult.multiply(factorial(map.get(key2)));
                else
                    bottomResult = bottomResult.multiply(factorial(map.get(key2) - 1));
            }

            notResults = factorial(elementCount).divide(bottomResult);
            bottomResult = BigInteger.ONE;
            multiplier = multiplier.add(notResults);
        }

        result = multiplier;

        return result;
    }

    private int getElementPosition(SortedMap<Integer, Integer> map, int element){
        int index = 0;
        for(int number : map.keySet()){
            if (element == number)
                return index;
            else
                index += map.get(number);
        }
        return -1;
    }

    private BigInteger factorial(int number){
        if(number == 1 || number == 0)
            return BigInteger.ONE;

        BigInteger result = BigInteger.ONE;
        for(int i=2; i<=number; i++)
            result = result.multiply(BigInteger.valueOf(i));
        return result;
    }

    private int charToInt(char c){
        return c - 'A';
    }
}