package com.cyryl.kyu4;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class RunesTest {

    @Test
    public void testSample() {
        System.out.println(Integer.valueOf('A'));
        System.out.println(Integer.valueOf('a'));
        System.out.println(Integer.valueOf('Z'));
        System.out.println(Integer.valueOf('z'));
        System.out.println("KONIEC");
        assertEquals( "Answer for expression '123*45?=5?088' " , 6 , Runes.solveExpression("123*45?=5?088") );
        assertEquals( "Answer for expression '??*??=302?' " , 5 , Runes.solveExpression("??*??=302?") );
        assertEquals( "Answer for expression '?*11=??' " , 2 , Runes.solveExpression("?*11=??") );
        assertEquals( "Answer for expression '??*1=??' " , 2 , Runes.solveExpression("??*1=??") );
    }

    @Test
    public void testError(){
        assertEquals( "Answer for expression '??+??=??' " , -1 , Runes.solveExpression("??+??=??") );
    }

    @Test
    public void testNegativeNumbers(){
        assertEquals( "Answer for expression '-5?*-1=5?' " , 0 , Runes.solveExpression("-5?*-1=5?") );
        assertEquals( "Answer for expression '19--45=5?' " , -1 , Runes.solveExpression("19--45=5?") );
    }

    @Test
    public void testSimple(){
        assertEquals( "Answer for expression '1+1=?' " , 2 , Runes.solveExpression("1+1=?") );
    }

    @Test
    public void getThatNumber(){
        assertEquals(1234, Runes.getNumber("1234"));
    }
    @Test
    public void getThatNegativeNumber(){
        assertEquals(-5678123, Runes.getNumber("-5678123"));
    }

    @Test
    public void getTheseMissingNumbers(){
        assertEquals("0123", Runes.getMissingNumbers("456+789=??"));
    }

    @Test
    public void canBeZero(){
        assertEquals(true, Runes.canBeZero("?"));
        assertEquals(false, Runes.canBeZero("?123"));
        assertEquals(false, Runes.canBeZero("-?32"));
        assertEquals(true, Runes.canBeZero("3???1"));
    }

}