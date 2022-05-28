package com.cyryl.kyu3;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.*;

public class AlphabeticAnagramsTest {

    @Test
    public void testKnownInputs() {
        AlphabeticAnagrams anagram = new AlphabeticAnagrams();

//        assertEquals("Position for 'A' incorrect", BigInteger.ONE, anagram.listPosition("A"));
        assertEquals("Position for 'ABAB' incorrect", BigInteger.valueOf(2), anagram.listPosition("ABAB"));
//        assertEquals("Position for 'AAAB' incorrect", BigInteger.ONE, anagram.listPosition("AAAB"));
//        assertEquals("Position for 'BAAA' incorrect", BigInteger.valueOf(4), anagram.listPosition("BAAA"));
//        assertEquals("Position for 'QUESTION' incorrect", BigInteger.valueOf(24572), anagram.listPosition("QUESTION"));
//        assertEquals("Position for 'CBBAAA' incorrect", BigInteger.valueOf(60), anagram.listPosition("CBBAAA"));
//        assertEquals("Position for 'BOOKKEEPER' incorrect", BigInteger.valueOf(10743), anagram.listPosition("BOOKKEEPER"));
    }

}