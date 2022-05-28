package com.cyryl.kyu2;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class SudokuSolverTest {

//    @Test
//    public void copyTest() throws CloneNotSupportedException {
//
//        SudokuSolver.SudokuField[] fields = new SudokuSolver.SudokuField[2];
//        fields[0] = new SudokuSolver.SudokuField();
//        fields[1] = new SudokuSolver.SudokuField();
//        SudokuSolver.SudokuField[] copyFields = new SudokuSolver.SudokuField[2];
//        copyFields[0] = fields[0].clone();
//        copyFields[1] = fields[1].clone();
//
//        fields[1].updatePossibleNumbers(2);
//        copyFields[1].updatePossibleNumbers(5);
//
//        System.out.println(fields[1].getAvailableNumbers());
//        System.out.println(copyFields[1].getAvailableNumbers());
//
//    }


    @Test
    public void sampleTest() {

        int[][] puzzle   = {{0, 0, 6, 1, 0, 0, 0, 0, 8},
                {0, 8, 0, 0, 9, 0, 0, 3, 0},
                {2, 0, 0, 0, 0, 5, 4, 0, 0},
                {4, 0, 0, 0, 0, 1, 8, 0, 0},
                {0, 3, 0, 0, 7, 0, 0, 4, 0},
                {0, 0, 7, 9, 0, 0, 0, 0, 3},
                {0, 0, 8, 4, 0, 0, 0, 0, 6},
                {0, 2, 0, 0, 5, 0, 0, 8, 0},
                {1, 0, 0, 0, 0, 2, 5, 0, 0}},

                solution = {{3, 4, 6, 1, 2, 7, 9, 5, 8},
                        {7, 8, 5, 6, 9, 4, 1, 3, 2},
                        {2, 1, 9, 3, 8, 5, 4, 6, 7},
                        {4, 6, 2, 5, 3, 1, 8, 7, 9},
                        {9, 3, 1, 2, 7, 8, 6, 4, 5},
                        {8, 5, 7, 9, 4, 6, 2, 1, 3},
                        {5, 9, 8, 4, 1, 3, 7, 2, 6},
                        {6, 2, 4, 7, 5, 9, 3, 8, 1},
                        {1, 7, 3, 8, 6, 2, 5, 9, 4}};

        assertArrayEquals(solution, new SudokuSolver(puzzle).solve());
    }

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void setUpInvalidValue() {
        int[][] puzzle = {{0, 0, 6, 1, 0, 0, 0, 0, 8},
                {0, 8, 0, 0, 9, 0, 0, 3, 0},
                {2, 0, 0, 0, 0, 5, 4, 0, 0},
                {4, 0, 0, 0, 0, 1, 8, 0, 0},
                {0, 3, 0, 0, 7, 0, 0, 4, 0},
                {0, 0, 7, 9, 0, 0, 0, 0, 3},
                {0, 0, 8, 4, 0, -1, 0, 0, 6},
                {0, 2, 0, 0, 5, 0, 0, 8, 0},
                {1, 0, 0, 0, 0, 2, 5, 0, 0}};

        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Invalid cell value");
        SudokuSolver sudokuSolver = new SudokuSolver(puzzle);
    }

    @Test
    public void validationNoUniqueSolution() {
        int[][] puzzle = {{0, 0, 6, 1, 0, 0, 0, 0, 0},
                {0, 8, 0, 0, 9, 0, 0, 0, 0},
                {2, 0, 0, 0, 0, 0, 4, 0, 0},
                {4, 0, 0, 0, 0, 1, 8, 0, 0},
                {0, 3, 0, 0, 7, 0, 0, 0, 0},
                {0, 0, 0, 9, 0, 0, 0, 0, 0},
                {0, 0, 8, 4, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 5, 0, 0, 8, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0}};

        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Not enough given values to create unique solution");
        SudokuSolver sudokuSolver = new SudokuSolver(puzzle);
    }

    @Test
    public void validationRepeatingNumberInASquare() {
        int[][] puzzle = {{0, 0, 6, 1, 0, 0, 0, 0, 8},
                {6, 8, 0, 0, 9, 0, 0, 3, 0},
                {2, 0, 0, 0, 0, 5, 4, 0, 0},
                {4, 0, 0, 0, 0, 1, 8, 0, 0},
                {0, 3, 0, 0, 7, 0, 0, 4, 0},
                {0, 0, 7, 9, 0, 0, 0, 0, 3},
                {0, 0, 8, 4, 0, 0, 0, 0, 6},
                {0, 2, 0, 0, 5, 0, 0, 8, 0},
                {1, 0, 0, 0, 0, 2, 5, 0, 0}};

        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Repeating number inside a square");
        SudokuSolver sudokuSolver = new SudokuSolver(puzzle);
    }

    @Test
    public void validationRepeatingNumberInARow() {
        int[][] puzzle = {{0, 0, 6, 1, 0, 8, 0, 0, 8},
                {0, 8, 0, 0, 9, 0, 0, 3, 0},
                {2, 0, 0, 0, 0, 5, 4, 0, 0},
                {4, 0, 0, 0, 0, 1, 8, 0, 0},
                {0, 3, 0, 0, 7, 0, 0, 4, 0},
                {0, 0, 7, 9, 0, 0, 0, 0, 3},
                {0, 0, 8, 4, 0, 0, 0, 0, 6},
                {0, 2, 0, 0, 5, 0, 0, 8, 0},
                {1, 0, 0, 0, 0, 2, 5, 0, 0}};

        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Repeating number inside a row");
        SudokuSolver sudokuSolver = new SudokuSolver(puzzle);
    }

    @Test
    public void validationRepeatingNumberInAColumn() {
        int[][] puzzle = {{0, 0, 6, 1, 0, 0, 0, 0, 8},
                {0, 8, 0, 0, 9, 0, 0, 3, 0},
                {2, 0, 0, 0, 0, 5, 4, 0, 0},
                {4, 0, 0, 0, 0, 1, 8, 0, 0},
                {0, 3, 0, 0, 7, 0, 0, 4, 0},
                {0, 0, 7, 9, 0, 0, 0, 0, 3},
                {0, 0, 8, 4, 0, 0, 0, 0, 6},
                {0, 2, 0, 0, 5, 0, 0, 8, 0},
                {1, 0, 0, 0, 0, 2, 5, 0, 3}};

        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Repeating number inside a column");
        SudokuSolver sudokuSolver = new SudokuSolver(puzzle);
    }

    @Test
    public void invalidGrid(){
        int[][] puzzle = {{1, 5, 3, 6, 8, 4, 2, 7, 9},
                {9, 8, 6, 7, 2, 5, 4, 3, 1},
                {4, 7, 2, 3, 1, 9, 8, 6, 5},
                {6, 2, 8, 1, 9, 3, 5, 4, 7},
                {5, 9, 1, 4, 7, 8, 3, 2, 6},
                {7, 3, 4, 5, 6, 2, 1, 9, 8},
                {8, 6, 5, 2, 4, 7, 9, 1, 3},
                {2, 1, 9, 8, 3, 6, 7, 5, 4},
                {3, 4, 7, 9, 5, 1, 6, 8, 2}};

        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("No empty cells");
        SudokuSolver sudokuSolver = new SudokuSolver(puzzle);
    }
}