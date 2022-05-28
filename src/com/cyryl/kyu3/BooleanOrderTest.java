package com.cyryl.kyu3;

import static org.junit.Assert.*;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.runners.JUnit4;
import java.math.BigInteger;


public class BooleanOrderTest {

    @Test
    public void sampleTests() {
        assertEquals(new BigInteger("2"),            new BooleanOrder("tft","^&").solve());
        assertEquals(new BigInteger("16"),           new BooleanOrder("ttftff","|&^&&").solve());
        assertEquals(new BigInteger("339"),          new BooleanOrder("ttftfftf","|&^&&||").solve());
        assertEquals(new BigInteger("851"),          new BooleanOrder("ttftfftft","|&^&&||^").solve());
        assertEquals(new BigInteger("2434"),         new BooleanOrder("ttftfftftf","|&^&&||^&").solve());
        assertEquals(new BigInteger("945766470799"), new BooleanOrder("ttftfftftffttfftftftfftft","|&^&&||^&&^^|&&||^&&||&^").solve());
    }

    @Test
    public void failingTest(){
        assertEquals(new BigInteger("16"),           new BooleanOrder("ttftff","|&^&&").solve());
    }

    @Test
    public void kataTest(){
        assertEquals(new BigInteger("2"),            new BooleanOrder("ttt","&&").solve());
    }

    @Test
    public void shortTest(){
        assertEquals(new BigInteger("1"),            new BooleanOrder("tf","|").solve());
    }

    @Test
    public void partTest(){
        assertEquals(new BigInteger("0"),            new BooleanOrder("tff","&&").solve());
    }

    @Test
    public void invertedTest(){
        assertEquals(new BigInteger("2"),            new BooleanOrder("ftt","||").solve());
    }
}