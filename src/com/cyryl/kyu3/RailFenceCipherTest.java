package com.cyryl.kyu3;

import org.junit.Test;

import static org.junit.Assert.*;

public class RailFenceCipherTest {

    @Test
    public void encodeSampleTests() {
        String[][] encodes = {{"WEAREDISCOVEREDFLEEATONCE", "WECRLTEERDSOEEFEAOCAIVDEN"},  // 3 rails
                {"Hello, World!", "Hoo!el,Wrdl l"},    // 3 rails
                {"Hello, World!", "H !e,Wdloollr"},    // 4 rails
                {"", ""}                               // 3 rails (even if...)
        };
        int[] rails = {3,3,4,3};
        for (int i=0 ; i<encodes.length ; i++) {
            assertEquals(encodes[i][1], RailFenceCipher.encode(encodes[i][0], rails[i]));
        }
    }

    @Test
    public void decodeSampleTests() {
        String[][] decodes = {{"WECRLTEERDSOEEFEAOCAIVDEN", "WEAREDISCOVEREDFLEEATONCE"},    // 3 rails
                {"H !e,Wdloollr", "Hello, World!"},    // 4 rails
                {"", ""}                               // 3 rails (even if...)
        };
        int[] rails = {3,4,3};
        for (int i=0 ; i<decodes.length ; i++) {
            assertEquals(decodes[i][1], RailFenceCipher.decode(decodes[i][0], rails[i]));
        }
    }

    @Test
    public void exampleTests(){
        String Example = "EPXMLAE";
        assertEquals("EXAMPLE",RailFenceCipher.decode(Example, 3));
    }

//    @Test
//    public void exampleTests(){
//        //String Example = "EPXMLAE";
//        String Example = "EXAMPLE";
//        assertEquals("EPXMLAE",RailFenceCipher.decode(Example, 3));
//    }

    @Test
    public void example1Tests(){
        String Example = "EPXML1AE";
        assertEquals("EXAMPLE1",RailFenceCipher.decode(Example, 3));
    }

    @Test
    public void example2Tests(){
        String Example = "EP2XML1AE";
        assertEquals("EXAMPLE12",RailFenceCipher.decode(Example, 3));
    }

    @Test
    public void example3Tests(){
        String Example = "EP2XML13AE";
        assertEquals("EXAMPLE123",RailFenceCipher.decode(Example, 3));
    }

}