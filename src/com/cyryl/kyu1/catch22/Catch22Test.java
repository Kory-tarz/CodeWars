package com.cyryl.kyu1.catch22;

import org.junit.Test;

import static org.junit.Assert.fail;

public class Catch22Test {
    @Test
    public void evaluate() throws Throwable {
        Yossarian yossarian = Catch22.loophole();
        if (! yossarian.isCrazy()) fail("Keep fighting!");
    }
}
