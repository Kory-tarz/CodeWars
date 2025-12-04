package com.cyryl.kyu1.loopover;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Loopover {

    private final char[][] currentBoard;
    private final char[][] solvedBoard;

    private static final int ROW = 0;
    private static final int COLUMN = 1;
    private static final int LEFT = -1;
    private static final int RIGHT = 1;
    private static final int UP = -1;
    private static final int DOWN = 1;

    private boolean lastEffort = false;

    public static List<String> solve(char[][] mixedUpBoard, char[][] solvedBoard) {
        return new Loopover(mixedUpBoard, solvedBoard).solve();
    }

    public Loopover(char[][] mixedUpBoard, char[][] solvedBoard) {
        currentBoard = createCurrentBoard(mixedUpBoard);
        this.solvedBoard = solvedBoard;
        printBoard();
    }

    private boolean isSolvable() {
        Function<char[][], Set<Integer>> mapper = board ->
                Arrays.stream(board).flatMapToInt(row -> new String(row).chars()).boxed().collect(Collectors.toSet());
        Set<Integer> mixedBoardChars = mapper.apply(currentBoard);
        Set<Integer> solvedBoardChars = mapper.apply(solvedBoard);
        if (mixedBoardChars.size() != currentBoard.length * currentBoard[0].length) {
            return false;
        }
        return solvedBoardChars.containsAll(mixedBoardChars);
    }

    public List<String> solve() {
        if (!isSolvable()) {
            return null;
        }
        List<String> moves = solveFirstRows();
        printBoard();
        List<String> lastMoves = solveLastRow();
        if (lastMoves == null) {
            return null;
        }
        moves.addAll(lastMoves);
        printBoard();
        return moves;
    }

    private List<String> solveFirstRows() {
        List<String> moves = new ArrayList<>();
        for (int i = 0; i < solvedBoard.length - 1; i++) {
            for (int j = 0; j < solvedBoard[i].length; j++) {
                int[] targetPos = new int[]{i, j};
                int[] currPos = findCurrPosition(solvedBoard[targetPos[ROW]][targetPos[COLUMN]]);
                moves.addAll(moveToPos(currPos, targetPos));
            }
        }
        return moves;
    }

    private List<String> moveToPos(int[] currPos, int[] targetPos) {
        List<String> moves = new ArrayList<>();
        if (currPos[ROW] == targetPos[ROW] && currPos[COLUMN] == targetPos[COLUMN]) {
            return moves;
        }
        moves.addAll(diggingOperation(currPos, targetPos));
        moves.addAll(shuffleOperation(currPos, targetPos));
        moves.addAll(landingOperation(currPos, targetPos));
        return moves;
    }

    private List<String> diggingOperation(int[] currPos, int[] targetPos) {
        List<String> moves = new ArrayList<>();
        if (currPos[ROW] != targetPos[ROW]) {
            return moves;
        }
        int initialColumn = currPos[COLUMN];
        moves.add(moveDown(initialColumn));
        currPos[ROW] += DOWN;
        if (currPos[COLUMN] - 1 == targetPos[COLUMN]) {
            moves.add(moveRight(currPos[ROW]));
            currPos[COLUMN] = (currPos[COLUMN] + RIGHT) % currentBoard[0].length;
        } else {
            moves.add(moveLeft(currPos[ROW]));
            currPos[COLUMN] = (currPos[COLUMN] + LEFT) % currentBoard[0].length;
        }
        moves.add(moveUp(initialColumn));
        return moves;
    }

    private List<String> shuffleOperation(int[] currPos, int[] targetPos) {
        List<String> moves = new ArrayList<>();
        if (currPos[COLUMN] != targetPos[COLUMN]) {
            return moves;
        }
        if (currPos[COLUMN] + 1 < currentBoard[0].length) {
            moves.add(moveRight(currPos[ROW]));
            currPos[COLUMN] += RIGHT;
        } else {
            moves.add(moveLeft(currPos[ROW]));
            currPos[COLUMN] += LEFT;
        }
        return moves;
    }

    private List<String> landingOperation(int[] currPos, int[] targetPos) {
        List<String> moves = new ArrayList<>();
        for (int i = 0; i < currPos[ROW] - targetPos[ROW]; i++) {
            moves.add(moveDown(targetPos[COLUMN]));
        }
        if (currPos[COLUMN] < targetPos[COLUMN]) {
            while (currPos[COLUMN] != targetPos[COLUMN]) {
                moves.add(moveRight(currPos[ROW]));
                currPos[COLUMN] += RIGHT;
            }
        } else {
            while (currPos[COLUMN] != targetPos[COLUMN]) {
                moves.add(moveLeft(currPos[ROW]));
                currPos[COLUMN] += LEFT;
            }
        }
        while (currPos[ROW] > targetPos[ROW]) {
            moves.add(moveUp(currPos[COLUMN]));
            currPos[ROW] += UP;
        }
        return moves;
    }

    private List<String> solveLastRow() {
        List<String> moves = new ArrayList<>();
        int lastRow = currentBoard.length - 1;
        moves.addAll(moveLastRow(lastRow));
        for (int j = 1; j < solvedBoard[lastRow].length - 2; j++) {
            int[] targetPos = new int[]{lastRow, j};
            int[] currPos = findCurrPosition(solvedBoard[targetPos[ROW]][targetPos[COLUMN]]);
            moves.addAll(digAndKeep(currPos, targetPos));
            moves.addAll(moveLastRow(lastRow));
        }
        if (!isSolved()) {
            moves.addAll(lastEffort(lastRow));
        }
        return isSolved() ? moves : null;
    }

    private List<String> lastEffort(int lastRow) {
        List<String> moves = new ArrayList<>();
        if (lastEffort) {
            return moves;
        } else {
            lastEffort = true;
        }
        if (currentBoard[0].length % 2 == 0) {
            moves.addAll(lastEffortEvenBottom(new int[]{lastRow, currentBoard[0].length - 2}));
            List<String> tryAgain = solveLastRow();
            if (tryAgain == null) {
                return new ArrayList<>();
            } else {
                moves.addAll(tryAgain);
            }
        } else if (currentBoard.length % 2 == 0) {
            moves.addAll(lastEffortEvenSide(new int[]{lastRow, currentBoard[0].length - 1}));
        }
        return moves;
    }

    private List<String> lastEffortEvenSide(int[] currPos) {
        List<String> moves = new ArrayList<>();

        moves.add(moveUp(currPos[COLUMN]));
        // bring the start
        do {
            moves.add(moveRight(currPos[ROW]));
            moves.add(moveUp(currPos[COLUMN]));
            moves.add(moveLeft(currPos[ROW]));
            moves.add(moveUp(currPos[COLUMN]));
        }
        while (currentBoard[currPos[ROW]][currPos[COLUMN]] != solvedBoard[currPos[ROW]][currPos[COLUMN]]);
        return moves;
    }

    private List<String> lastEffortEvenBottom(int[] currPos) {
        List<String> moves = new ArrayList<>();
        int lastRow = currPos[ROW];
        moves.add(moveDown(currPos[COLUMN]));
        // bring the start
        moves.add(moveLeft(lastRow));
        moves.add(moveLeft(lastRow));
        // take the start
        moves.add(moveUp(currPos[COLUMN]));
        // move hostage
        moves.add(moveRight(lastRow));
        // bring the down the start
        moves.add(moveDown(currPos[COLUMN]));
        // move hostage
        moves.add(moveRight(lastRow));
        // return the hostage
        moves.add(moveUp(currPos[COLUMN]));
        return moves;
    }

    private List<String> digAndKeep(int[] currPos, int[] targetPos) {
        List<String> moves = new ArrayList<>();
        if (currPos[ROW] == targetPos[ROW] && currPos[COLUMN] == targetPos[COLUMN]) {
            return moves;
        }
        int lastRow = currPos[ROW];
        char friend = solvedBoard[targetPos[ROW]][targetPos[COLUMN] - 1];
        char hostage = solvedBoard[currPos[ROW] - 1][currPos[COLUMN]];
        moves.add(moveDown(currPos[COLUMN]));
        while (currentBoard[lastRow][currPos[COLUMN] - 1] != friend) {
            moves.add(moveRight(lastRow));
        }
        moves.add(moveUp(currPos[COLUMN]));
        int[] hostagePos = findCurrPosition(hostage);
        if (hostagePos[COLUMN] < currPos[COLUMN]) {
            while (currentBoard[lastRow][(currPos[COLUMN] + 1) % currentBoard[lastRow].length] != hostage) {
                moves.add(moveLeft(lastRow));
            }
            moves.add(moveDown(currPos[COLUMN]));
            moves.add(moveLeft(lastRow));
            moves.add(moveUp(currPos[COLUMN]));
        } else {
            while (currentBoard[lastRow][currPos[COLUMN] - 1] != hostage) {
                moves.add(moveRight(lastRow));
            }
            moves.add(moveDown(currPos[COLUMN]));
            moves.add(moveRight(lastRow));
            moves.add(moveUp(currPos[COLUMN]));
        }
        return moves;
    }

    private List<String> moveLastRow(int lastRow) {
        List<String> moves = new ArrayList<>();
        while (currentBoard[lastRow][0] != solvedBoard[lastRow][0]) {
            moves.add(moveLeft(lastRow));
        }
        return moves;
    }

    private boolean isSolved() {
        for (int i = 0; i < currentBoard.length; i++) {
            for (int j = 0; j < currentBoard[i].length; j++) {
                if (currentBoard[i][j] != solvedBoard[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    private char[][] createCurrentBoard(char[][] mixedUpBoard) {
        char[][] currentBoard = new char[mixedUpBoard.length][mixedUpBoard[0].length];
        for (int i = 0; i < currentBoard.length; i++) {
            currentBoard[i] = mixedUpBoard[i].clone();
        }
        return currentBoard;
    }

    private int[] findCurrPosition(char target) {
        for (int i = 0; i < currentBoard.length; i++) {
            for (int j = 0; j < currentBoard[i].length; j++) {
                if (currentBoard[i][j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[2];
    }

    private String moveLeft(int rowNumber) {
        moveRow(rowNumber, LEFT);
        return "L" + rowNumber;
    }

    private String moveRight(int rowNumber) {
        moveRow(rowNumber, RIGHT);
        return "R" + rowNumber;
    }

    private String moveDown(int columnNumber) {
        moveColumn(columnNumber, DOWN);
        return "D" + columnNumber;
    }

    private String moveUp(int columnNumber) {
        moveColumn(columnNumber, UP);
        return "U" + columnNumber;
    }

    private void moveRow(int rowNumber, int direction) {
        int currCell = 0;
        char currValue = currentBoard[rowNumber][currCell];
        int length = currentBoard[0].length;
        do {
            int nextCell = getNextCell(currCell, direction, length);
            char nextValue = currentBoard[rowNumber][nextCell];
            currentBoard[rowNumber][nextCell] = currValue;
            currCell = nextCell;
            currValue = nextValue;
        } while (currCell != 0);
    }

    private void moveColumn(int columnNumber, int direction) {
        int currCell = 0;
        char currValue = currentBoard[currCell][columnNumber];
        int length = currentBoard.length;
        do {
            int nextCell = getNextCell(currCell, direction, length);
            char nextValue = currentBoard[nextCell][columnNumber];
            currentBoard[nextCell][columnNumber] = currValue;
            currCell = nextCell;
            currValue = nextValue;
        } while (currCell != 0);
    }

    private int getNextCell(int currCell, int direction, int length) {
        return (((currCell + direction) % length) + length) % length;
    }

    private void printBoard() {
        System.out.println("Board: ");
        for (int i = 0; i < currentBoard.length; i++) {
            for (int j = 0; j < currentBoard[i].length; j++) {
                System.out.print(currentBoard[i][j]);
            }
            System.out.println();
        }
    }
}
