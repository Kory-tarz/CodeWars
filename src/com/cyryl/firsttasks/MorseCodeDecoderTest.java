package com.cyryl.firsttasks;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class MorseCodeDecoderTest {

//    @Test
//    public void testExampleFromDescription() {
//        assertThat(MorseCodeDecoder.decode(".... . -.--   .--- ..- -.. ."), is("HEY JUDE"));
//    }

    @Test
    public void testExample(){
        assertEquals(MorseCodeDecoder.decodeBits("1100110011001100000011000000111111001100111111001111110000000000000011001111110011111100111111000000110011001111110000001111110011001100000011"), ".... . -.--   .--- ..- -.. .");
        assertEquals(MorseCodeDecoder.decodeBits("00011000"), ".");
        assertEquals(MorseCodeDecoder.decodeBits("0001110"), ".");
        assertEquals(MorseCodeDecoder.decodeBits("111111001100111111000000"), "-.-");
        assertEquals(MorseCodeDecoder.decodeBits("1000100000001000"), ". .   .");
        assertEquals(MorseCodeDecoder.decodeBits("111000111000000"), "..");
        assertEquals(MorseCodeDecoder.decodeBits("111000000000111"), ". .");
    }

}