package com.cyryl.kyu4;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class SecretDetectiveTest {
    private SecretDetective detective;

    @Before public void setup() {
        detective = new SecretDetective();
    }

    @Test public void secret1() {
        char[][] triplets = {
                {'t','u','p'},
                {'w','h','i'},
                {'t','s','u'},
                {'a','t','s'},
                {'h','a','p'},
                {'t','i','s'},
                {'w','h','s'}
        };
        assertEquals("whatisup", detective.recoverSecret(triplets));
    }

    @Test @Ignore
    public void testSetTriplet(){
        char[] triplet = {'t','u','p'};
        detective.setTriplet(triplet);
        assertEquals("t", detective.significantLetters.get('u'));
        assertEquals("", detective.significantLetters.get('t'));
        assertEquals("ut", detective.significantLetters.get('p'));

    }
}