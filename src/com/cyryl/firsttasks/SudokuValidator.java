package com.cyryl.firsttasks;

public class SudokuValidator {
    public static boolean check(int[][] sudoku) {

        if (checkZero(sudoku))
            return false;

        for(int i=0; i < sudoku.length; i += 3){
            for(int j=0; j < sudoku[0].length; j += 3){
                if (!isSquareSolved(sudoku, i, j))
                    return false;
            }
        }

        for(int i=0; i < sudoku.length; i++){
            if(!isColumnLineSolved(sudoku, i))
                return false;
            if(!isRowLineSolved(sudoku, i))
                return false;
        }
        return true;
    }

    private static boolean checkZero(int[][] sudoku){
        boolean containsZero = false;
        for(int i=0; i < sudoku.length; i++){
            for(int j=0; j < sudoku[0].length; j++){
                if(sudoku[i][j] == 0)
                    containsZero = true;
            }
        }
        return containsZero;
    }

    private static boolean isSquareSolved(int[][] sudoku, int row, int column){
        boolean[] numbers = new boolean[10];
        numbers[0] = true;

        for(int i = row; i < row+3; i++){
            for(int j = column; j < column+3; j++){
                numbers[sudoku[i][j]] = true;
            }
        }
        return isAllTrue(numbers);
    }

    private static boolean isColumnLineSolved(int[][] sudoku, int column){
        boolean[] numbers = new boolean[10];
        numbers[0] = true;
        for(int i=0; i< sudoku.length; i++){
            numbers[sudoku[i][column]] = true;
        }
        return isAllTrue(numbers);
    }

    private static boolean isRowLineSolved(int[][] sudoku, int row){
        boolean[] numbers = new boolean[10];
        numbers[0] = true;
        for(int i=0; i< sudoku[0].length; i++){
            numbers[sudoku[row][i]] = true;
        }
        return isAllTrue(numbers);
    }

    private static boolean isAllTrue(boolean[] boolTable){
        for (boolean val : boolTable){
            if (!val)
                return false;
        }
        return true;
    }
}
