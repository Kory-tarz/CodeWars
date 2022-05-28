package com.cyryl.kyu2;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class DinglemouseTest {

    @Test
    public void example(){

        String track = "                                /------------\\             \n" +
                "/-------------\\                /             |             \n" +
                "|             |               /              S             \n" +
                "|             |              /               |             \n" +
                "|        /----+--------------+------\\        |\n" +
                "\\       /     |              |      |        |             \n" +
                " \\      |     \\              |      |        |             \n" +
                " |      |      \\-------------+------+--------+---\\         \n" +
                " |      |                    |      |        |   |         \n" +
                " \\------+--------------------+------/        /   |         \n" +
                "        |                    |              /    |         \n" +
                "        \\------S-------------+-------------/     |         \n" +
                "                             |                   |         \n" +
                "/-------------\\              |                   |         \n" +
                "|             |              |             /-----+----\\    \n" +
                "|             |              |             |     |     \\   \n" +
                "\\-------------+--------------+-----S-------+-----/      \\  \n" +
                "              |              |             |             \\ \n" +
                "              |              |             |             | \n" +
                "              |              \\-------------+-------------/ \n" +
                "              |                            |               \n" +
                "              \\----------------------------/               \n";

//        String trainA = "aaaA";
        String trainA = "Aaaa";
        int posA = 2;
        //int posA = 147;
        String trainB = "Bbbbbbbbbbb";
        int posB = 288;
        int limit = 1000;

        Assert.assertEquals(516, Dinglemouse.trainCrash(track, trainA, posA, trainB, posB, limit));
    }
}