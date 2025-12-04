package com.cyryl.kyu4;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Skyscrapers4 {

    private final int[] clues;
    private final Cell[][] board;
    private final int size;

    public Skyscrapers4(int[] clues, int size) {
        this.clues = clues;
        this.size = size;
        board = new Cell[size][size];
    }

    public static int[][] solvePuzzle (int[] clues) {
        return new Skyscrapers4(clues, 4).solvePuzzle();
    }

    private int[][] solvePuzzle() {
        return null;
    }

    private class Cell {
        int x, y;
        List<Integer> allowed;
        List<Integer> suspects;

        Cell(int x, int y) {
            this.x = x;
            this.y = y;
            allowed = IntStream.rangeClosed(1, size).boxed().collect(Collectors.toList());
            suspects = new ArrayList<>();
        }


    }
}
