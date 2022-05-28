package com.cyryl.kyu3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.Assert;

public class IceMazeTest {

    @Test
    public void splitTest(){

        String map =
                " #    \n" +
                "x   E \n" +
                "      \n" +
                "     S\n" +
                "      \n" +
                " #    ";

        char[][] charMap = IceMaze.convertMap(map);

        for(char[] row : charMap){
            for(char c : row)
                System.out.print(c);
            System.out.println();
        }
    }

    @Test
    public void pokemon(){
        String map = "        #     \n" +
                "   #          \n" +
                "         #    \n" +
                " #            \n" +
                "#       #     \n" +
                "             #\n" +
                "      #       \n" +
                "  #          E\n" +
                "             #\n" +
                "       #      \n" +
                "     #   #    \n" +
                "              \n" +
                "#############S";
        Assert.assertEquals(List.of('u', 'l', 'u', 'r', 'u', 'r', 'd', 'l', 'u', 'l', 'd', 'r', 'd', 'r', 'u', 'r'), IceMaze.solve(map));
    }

    @Test
    public void index(){
        String map = "###    #                \n" +
                "  #x #  ##     #     ## \n" +
                "    #             x  # #\n" +
                "     ##     #     S  ## \n" +
                "               #    #   \n" +
                "     ##      #          \n" +
                "#     # #      # ##     \n" +
                "#     #    ##        #  \n" +
                "      #      x         #\n" +
                "#     #        ##       \n" +
                "  x      # #     x# #x #\n" +
                "#x  x      #            \n" +
                "  #     #  #  x#  # x ##\n" +
                "    # #    x       E    \n" +
                " #    x#           #    \n" +
                " ##       # #      #    \n" +
                "    ####     #x         \n" +
                "    #  #   #      #  ## \n" +
                "#         # #      #    \n" +
                " x       #        #   #x\n" +
                "    ##    # x    #  #   \n" +
                "#  #           ##  #    \n" +
                "        ##     x  #  #  \n" +
                "       x    # xx # ##  x\n" +
                " #  # #   ## #     #    \n" +
                "        #  #    x  ## # \n" +
                "  #           #   ## ## \n" +
                " #    #   x    #     ###\n" +
                " ##    # #     #  #  # #";

        IceMaze.solve(map);

    }

    @Test
    public void exmpleTests() {

        String map = "    x \n" +
                "  #   \n" +
                "   E  \n" +
                " #    \n" +
                "    # \n" +
                "S    #";
        System.out.printf("A simple spiral\n%s\n", map);
        Assert.assertEquals(List.of('u','r','d','l','u','r'), IceMaze.solve(map));

        map =   " #    \n" +
                "x   E \n" +
                "      \n" +
                "     S\n" +
                "      \n" +
                " #    ";
        System.out.printf("Ice puzzle has one-way routes\n%s\n", map);
        Assert.assertEquals(List.of('l','u','r'), IceMaze.solve(map));

        map =   "E#    \n" +
                "      \n" +
                "      \n" +
                "      \n" +
                "      \n" +
                " #   S";
        System.out.printf("The end is unreachable\n%s\n", map);
        Assert.assertEquals(null, IceMaze.solve(map));

        map =   "E#   #\n" +
                "      \n" +
                "#     \n" +
                "  #   \n" +
                " #    \n" +
                " S    ";
        System.out.printf("Tiebreak by least number of moves first\n%s\n", map);
        Assert.assertEquals(List.of('r','u','l','u'), IceMaze.solve(map));

        map =   "    E \n" +
                "     #\n" +
                "      \n" +
                "# #   \n" +
                "    # \n" +
                " #  S ";
        System.out.printf("Then by total distance traversed\n%s\n", map);
        Assert.assertEquals(List.of('l','u','r','u','r'), IceMaze.solve(map));

        map =   "E#xxx\n" +
                "x#x#x\n" +
                "x#x#x\n" +
                "x#x#x\n" +
                "xxx#S";
        System.out.printf("Covering all tiles with x should reduce the problem to simple pathfinding\n%s\n", map);
        Assert.assertEquals(List.of('u','u','u','u','l','l','d','d','d','d','l','l','u','u','u','u'), IceMaze.solve(map));

        map =   "E# \n" +
                "#S#\n" +
                " # ";
        System.out.printf("There is no escape\n%s\n", map);
        Assert.assertEquals(null, IceMaze.solve(map));
    }
}
