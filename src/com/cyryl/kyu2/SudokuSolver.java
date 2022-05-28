package com.cyryl.kyu2;

import java.awt.Point;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.IntStream;

public class SudokuSolver {

    private int[][] grid;
    private final int SUDOKU_SIZE = 9;
    private final int SUDOKU_SQUARE_SIZE = 3;
    private SudokuField[][] sudokuFields;
    private boolean isSolutionFound;

    public SudokuSolver(int[][] grid) {

        isGridValid(grid);
        sudokuFields = new SudokuField[SUDOKU_SIZE][SUDOKU_SIZE];
        setUpFields(sudokuFields);
        setUpInitialValues(sudokuFields, grid);
        isSolutionFound = false;
        this.grid = grid.clone();

    }

    // BOARD VALIDATION

    private boolean isGridValid(int[][] grid) {
        if(!isSizeValid(grid))
            throw new IllegalArgumentException("Invalid grid size");
        if(!isUniquelySolvable(grid))
            throw new IllegalArgumentException("Not enough given values to create unique solution");
        return true;
    }

    private boolean isSizeValid(int[][] grid){
        for(int i=0; i< grid.length; i++)
            if(grid[i].length != SUDOKU_SIZE)
                return false;
        return grid.length == SUDOKU_SIZE;
    }

    private boolean isUniquelySolvable(int[][] grid){
        int numberCount = 0;
        final int MIN_NUMBER_COUNT = 17;

        for(int i=0; i<grid.length; i++){
            for(int j=0; j< grid[0].length; j++) {
                if (1 <= grid[i][j] && grid[i][j] <= 9)
                    numberCount++;
                else if(grid[i][j] != 0)
                    throw new IllegalArgumentException("Invalid cell value");
            }
        }
        if(numberCount == SUDOKU_SIZE * SUDOKU_SIZE)
            throw new IllegalArgumentException("No empty cells");

        return numberCount >= MIN_NUMBER_COUNT;
    }

    private void setUpFields(SudokuField[][] sudokuFields){
        for(int i=0; i< sudokuFields.length; i++)
            for (int j=0; j< sudokuFields[0].length; j++)
                sudokuFields[i][j] = new SudokuField();
    }

    private void setUpInitialValues(SudokuField[][] sudokuFields, int[][] grid){

        for(int i=0; i< grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] != 0) {
                    sudokuFields[i][j].setNumber(grid[i][j]);
                    markLines(sudokuFields, i, j, grid[i][j]);
                    markSquare(sudokuFields, i, j, grid[i][j]);
                }
            }
        }
    }

    // Updating Values inside Sudoku

    private void markSquare(SudokuField[][] sudokuFields, int posX, int posY, int number){
        int rowCorner = posX/3 * SUDOKU_SQUARE_SIZE;
        int colCorner = posY/3 * SUDOKU_SQUARE_SIZE;

        for(int row = rowCorner; row < rowCorner+SUDOKU_SQUARE_SIZE; row++)
            for(int col = colCorner; col < colCorner+SUDOKU_SQUARE_SIZE; col++) {
                if(sudokuFields[row][col].getNumber() == number && row != posX && col != posY)
                    throw new IllegalArgumentException("Repeating number inside a square");
                sudokuFields[row][col].updatePossibleNumbers(number);
            }
    }

    private void markLines(SudokuField[][] sudokuFields, int posX, int posY, int number){
        // mark number in a column
        for(int row = 0; row<sudokuFields.length; row++) {
            if (sudokuFields[row][posY].getNumber() == number && row != posX)
                throw new IllegalArgumentException("Repeating number inside a column");
            sudokuFields[row][posY].updatePossibleNumbers(number);
        }
        // mark number in a row
        for(int col = 0; col<sudokuFields.length; col++) {
            if (sudokuFields[posX][col].getNumber() == number && col != posY)
                throw new IllegalArgumentException("Repeating number inside a row");
            sudokuFields[posX][col].updatePossibleNumbers(number);
        }

    }

    // Steps in solution

    public int[][] solve() {
        trySolve(sudokuFields);
        if(!isSolutionFound)
            throw new IllegalArgumentException("No Solution");
        return grid;
    }

    private void trySolve(SudokuField[][] curState){
        Point cell;
        boolean solved = false;
        SudokuField[][] newState;
        int number;
        boolean isPossible;

        while(!solved) {

            if(findImpossibleField(curState))
                return;
            cell = findStartingPos(curState);

            if (curState[cell.x][cell.y].possibilitiesCount() == 0)
                solved = true;
            else if (curState[cell.x][cell.y].possibilitiesCount() == 1) {
                number = curState[cell.x][cell.y].getPossibleNumber();
                isPossible = enterNumber(curState, cell, number);
                if(!isPossible)
                    return;
            } else {
                number = curState[cell.x][cell.y].getPossibleNumber();

                newState = copyState(curState);
                draw(newState);
                isPossible = enterNumber(newState, cell, number);
                if(isPossible)
                   trySolve(newState);
            }
        }
        if(isSolutionFound) {
            throw new IllegalArgumentException("Multiple solutions found");
        }else{
            isSolutionFound = true;
            fillGridWithSolution(curState);
        }
    }

    private void draw(SudokuField[][] sudokuFields){
        for(int i=0; i< sudokuFields.length; i++) {
            for (int j = 0; j < sudokuFields[0].length; j++) {
                System.out.print(sudokuFields[i][j].getNumber() + " ");
            }
            System.out.println();
        }
        System.out.println("-----------------");
    }

    private boolean findImpossibleField(SudokuField[][] sudokuFields){
        for(int i=0; i< sudokuFields.length; i++)
            for (int j=0; j< sudokuFields[0].length; j++)
                if(sudokuFields[i][j].possibilitiesCount() == 0 && sudokuFields[i][j].getNumber() == 0)
                    return true;
        return false;
    }

    private SudokuField[][] copyState(SudokuField[][] sudokuFields){
        SudokuField[][] copiedFields = new SudokuField[SUDOKU_SIZE][SUDOKU_SIZE];
        for(int i=0; i< sudokuFields.length; i++)
            for (int j=0; j< sudokuFields[0].length; j++)
                copiedFields[i][j] = sudokuFields[i][j].clone();

        return copiedFields;
    }

    private void fillGridWithSolution(SudokuField[][] sudokuFields){
        for(int i=0; i< sudokuFields.length; i++)
            for (int j=0; j< sudokuFields[0].length; j++)
                grid[i][j] = sudokuFields[i][j].getNumber();
    }

    private Point findStartingPos(SudokuField[][] sudokuFields){
        Point curPoint = new Point();
        int minCount = 9;
        int count;

        for(int i=0; i< sudokuFields.length; i++)
            for (int j=0; j< sudokuFields[0].length; j++){
                count = sudokuFields[i][j].possibilitiesCount();
                if(minCount > count && count > 0){
                    minCount = count;
                    curPoint.x = i;
                    curPoint.y = j;
                    if(minCount == 1)
                        return curPoint;
                }
            }
        return curPoint;
    }

    private boolean enterNumber(SudokuField[][] sudokuFields, Point point, int number){
        sudokuFields[point.x][point.y].setNumber(number);
        try {
            markLines(sudokuFields, point.x, point.y, number);
            markSquare(sudokuFields, point.x, point.y, number);
        }
        catch (IllegalArgumentException exception) {
            return false;
        }
        return true;
    }

    private class SudokuField implements Cloneable {
        public Set<Integer> availableNumbers;
        private int number;

        public SudokuField(){
            availableNumbers = new HashSet<>(IntStream.rangeClosed(1,9).boxed().toList());
            number = 0;
        }

        public void updatePossibleNumbers(int number){
            availableNumbers.remove(number);
        }

        public void setNumber(int number){
            this.number = number;
            availableNumbers.clear();
        }

        public int getNumber() {
            return number;
        }

        public int getPossibleNumber(){
            Iterator<Integer> iter = availableNumbers.iterator();
            int possibleNum;

            if(!iter.hasNext())
                return number;
            possibleNum = iter.next();
            availableNumbers.remove(possibleNum);
            return possibleNum;
        }

        public int possibilitiesCount(){
            return availableNumbers.size();
        }

        @Override
        protected SudokuField clone() {
            try {
                SudokuField field = (SudokuField) super.clone();
                field.availableNumbers = new HashSet<>();
                field.availableNumbers.addAll(this.availableNumbers);
                field.number = this.number;
                return field;
            }
            catch (CloneNotSupportedException ignored){
            }
            return new SudokuField();
        }

    }
}
