package com.cyryl.kyu1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ImmortalTest {

    @Test void tests(){
        Immortal.printSomething(14, 28);
    }

    @Test
    public void example() {
        assertEquals(5, Immortal.elderAge(8, 5, 1, 100));
        assertEquals(224, Immortal.elderAge(8, 8, 0, 100007));
        assertEquals(11925, Immortal.elderAge(25, 31, 0, 100007));
        assertEquals(4323, Immortal.elderAge(5, 45, 3, 1000007));
        assertEquals(1586, Immortal.elderAge(31, 39, 7, 2345));
        assertEquals(808451, Immortal.elderAge(545, 435, 342, 1000007));
        // You need to run this test very quickly before attempting the actual tests :)
        assertEquals(5456283, Immortal.elderAge(28827050410L, 35165045587L, 7109602, 13719506));
    }
}