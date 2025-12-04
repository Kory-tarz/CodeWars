package com.cyryl.kyu1.loopover;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class LoopoverTest {
    @Test
    public void fixedTest1() {
        List<String> moves = Loopover.solve(new char[][]{{'A', 'B'}, {'C', 'D'}}, new char[][]{{'A', 'B'}, {'C', 'D'}});
        System.out.println(moves.stream().collect(Collectors.joining(", ")));
    }

    @Test
    public void fixedTest2() {
        List<String> moves = Loopover.solve(new char[][]{{'D', 'A'}, {'C', 'B'}}, new char[][]{{'A', 'B'}, {'C', 'D'}});
        System.out.println(moves.stream().collect(Collectors.joining(", ")));
    }

    @Test
    public void fixedTest3() {
        List<String> moves = Loopover.solve(new char[][]{{'D', 'B', 'E'}, {'C', 'A', 'I'}, {'G', 'H', 'F'}},
                new char[][]{{'A', 'B', 'C'}, {'D', 'E', 'F'}, {'G', 'H', 'I'}});
        System.out.println(moves.stream().collect(Collectors.joining(", ")));
        //Looper.check("ACDBE\nFGHIJ\nKLMNO\nPQRST", false);
    }

    @Test
    public void fixedTest4() {
        List<String> moves = Loopover.solve(new char[][]{{'D', 'B', 'E', 'K'}, {'C', 'A', 'I', 'J'}, {'G', 'H', 'F', 'L'}},
                new char[][]{{'A', 'B', 'C', 'D'}, {'E', 'F','G', 'H'}, {'I', 'J', 'K', 'L'}});
        System.out.println(moves.stream().collect(Collectors.joining(", ")));
    }

    @Test
    public void fixedTest5() {
        List<String> moves = Loopover.solve(new char[][]{{'A', 'B', 'C'}, {'D', 'F', 'E'}},
                new char[][]{{'A', 'B', 'C'}, {'D', 'E', 'F'}});
        System.out.println(moves.stream().collect(Collectors.joining(", ")));
    }

    @Test
    public void fixedTest6() {
        char[][] mixedBoard = readBoard("ACDBE\n" +
                "FGHIJ\n" +
                "KLMNO\n" +
                "PQRST");
        char[][] solvedBoard = readBoard("ABCDE\n" +
                "FGHIJ\n" +
                "KLMNO\n" +
                "PQRST");
        List<String> moves = Loopover.solve(mixedBoard, solvedBoard);
        System.out.println(moves.stream().collect(Collectors.joining(", ")));
    }
    @Test
    public void fixedTest7() {
        char[][] mixedBoard = readBoard("LDA\n" +
                "QRM\n" +
                "NBI\n" +
                "KEH\n" +
                "JCO\n" +
                "PGF");
        char[][] solvedBoard = readBoard("ABC\n" +
                "DEF\n" +
                "GHI\n" +
                "JKL\n" +
                "MNO\n" +
                "PQR");
        List<String> moves = Loopover.solve(mixedBoard, solvedBoard);
        System.out.println(moves.stream().collect(Collectors.joining(", ")));
    }

    private char[][] readBoard(String str) {
        String[] rows = str.split("\n");
        char[][] board = new char[rows.length][];
        for (int i = 0; i < rows.length; i++) {
            board[i] = rows[i].toCharArray();
        }
        return board;
    }
}