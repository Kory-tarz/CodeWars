package com.cyryl.kyu4;

import org.junit.Test;

import java.beans.BeanProperty;

import static org.junit.Assert.assertEquals;

public class TenPinBowlingTest {


    @Test
    public void BasicTests() {
        // assertEquals("expected", "actual");

        assertEquals(300, TenPinBowling.bowlingScore("X X X X X X X X X XXX"));
    }

    @Test
    public void SpareTest(){
        assertEquals(110, TenPinBowling.bowlingScore("1/ 1/ 1/ 1/ 1/ 1/ 1/ 1/ 1/ 1/1"));
    }

    @Test
    public void SimpleTest(){
        System.out.println("Maybe this bowler should put bumpers on...\n ");
        assertEquals(20, TenPinBowling.bowlingScore("11 11 11 11 11 11 11 11 11 11"));
    }

    @Test public void testStrike(){
        assertEquals(30, TenPinBowling.bowlingScore("X 11 11 11 11 11 11 11 11 11"));
    }

    @Test
    public void SpareTestNoFinish(){
        assertEquals(101, TenPinBowling.bowlingScore("1/ 1/ 1/ 1/ 1/ 1/ 1/ 1/ 1/ 11"));
    }

    @Test
    public void failingTest(){
        assertEquals(150, TenPinBowling.bowlingScore("5/ 4/ 3/ 2/ 1/ 0/ X 9/ 4/ 8/8"));
    }
}