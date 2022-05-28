package com.cyryl.kyu3;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class Central_Pixels_FinderTest extends Central_Pixels_Finder
{
    // Wrapper for the attempted solution that sorts the returned array:
    public int[] solution(int c)
    {
        int[] r = central_pixels(c);
        Arrays.sort(r);
        return r;
    }

    @Test
    public void Example_In_The_Picture() throws Exception
    {
        int[] image_data = {1,1,4,4,4,4,2,2,2,2,
                1,1,1,1,2,2,2,2,2,2,
                1,1,1,1,2,2,2,2,2,2,
                1,1,1,1,1,3,2,2,2,2,
                1,1,1,1,1,3,3,3,2,2,
                1,1,1,1,1,1,3,3,3,3};

        // Only one red pixel has the maximum depth of 3:
        set(10, 6, image_data);
        int[] red_ctr = { 32 };
        assertArrayEquals(red_ctr, solution(1));

        // Multiple blue pixels have the maximum depth of 2:
        set(10, 6, image_data);
        int[] blue_ctr = { 16,17,18,26,27,28,38 };
        assertArrayEquals(blue_ctr, solution(2));

        // All the green pixels have depth 1, so they are all "central":
        set(10, 6, image_data);
        int[] green_ctr = { 35,45,46,47,56,57,58,59 };
        assertArrayEquals(green_ctr, solution(3));

        // Similarly, all the purple pixels have depth 1:
        set(10, 6, image_data);
        int[] purple_ctr = { 2,3,4,5 };
        assertArrayEquals(purple_ctr, solution(4));

        // There are no pixels with colour 5:
        set(10, 6, image_data);
        int[] non_existent_ctr = { };
        assertArrayEquals(non_existent_ctr, solution(5));

        // Changing one pixel can make a big difference to the result:
        pixels[32] = 3;
        int[] new_ctr = { 11,21,41,43 };
        assertArrayEquals(new_ctr, solution(1));
    }
}
