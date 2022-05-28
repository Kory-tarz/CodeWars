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

        String trainA = "Aaaa";
        int posA = 147;
        String trainB = "Bbbbbbbbbbb";
        int posB = 288;
        int limit = 1000;

        Assert.assertEquals(516, Dinglemouse.trainCrash(track, trainA, posA, trainB, posB, limit));
    }

    @Test
    public void startOnStation(){
        String track = "/------S----------\\\n" +
                "|                 |\n" +
                "|                 |\n" +
                "|                 |\n" +
                "|                 |\n" +
                "\\----------S------/";

        String trainA = "aaaaaaaaaaaaA";
        int posA = 7;
        String trainB = "xxxX";
        int posB = 30;
        int limit = 1000;

        Assert.assertEquals(34, Dinglemouse.trainCrash(track, trainA, posA, trainB, posB, limit));
    }

    @Test
    public void outOfBounds(){

        String track = "/-------\\ \n" +
                "|       | \n" +
                "|       | \n" +
                "|       | \n" +
                "\\-------S--------\\\n" +
                "        |        |\n" +
                "        |        |\n" +
                "        |        |\n" +
                "        \\--------/";

        String trainA = "Ccccccccc";
        int posA = 0;
        String trainB = "Aaaaa";
        int posB = 18;
        int limit = 1000;

        Assert.assertEquals(12, Dinglemouse.trainCrash(track, trainA, posA, trainB, posB, limit));
    }
}