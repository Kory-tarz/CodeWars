package com.cyryl.kyu4;

import org.junit.Test;

import static org.junit.Assert.*;

public class NextSmallerNumberTest {
    @Test
    public void basicTests() {
        assertEquals(12, NextSmallerNumber.nextSmaller(21));
        assertEquals(130000, NextSmallerNumber.nextSmaller(300001));
        assertEquals(305126, NextSmallerNumber.nextSmaller(305162));
        assertEquals(9071, NextSmallerNumber.nextSmaller(9107));
        assertEquals(790, NextSmallerNumber.nextSmaller(907));
        assertEquals(513, NextSmallerNumber.nextSmaller(531));
        assertEquals(-1, NextSmallerNumber.nextSmaller(1027));
        assertEquals(414, NextSmallerNumber.nextSmaller(441));
        assertEquals(123456789, NextSmallerNumber.nextSmaller(123456798));
        assertEquals(2351, NextSmallerNumber.nextSmaller(2513));
        assertEquals(7351, NextSmallerNumber.nextSmaller(7513));
        assertEquals(351, NextSmallerNumber.nextSmaller(513));
        assertEquals(1072, NextSmallerNumber.nextSmaller(1207));
        //assertEquals(12778362995240, 12778362995402);
    }

}