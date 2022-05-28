package com.cyryl.kyu2;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class BreakPieces {

    private static final char CORNER = '+';
    private static final char HOR_SIDE = '-';
    private static final char VER_SIDE = '|';
    private static final char EMPTY = ' ';

    static final int[] GO_LEFT = {0, -1};
    static final int[] GO_RIGHT = {0, 1};
    static final int[] GO_UP = {-1, 0};
    static final int[] GO_DOWN = {1, 0};

    static final int[][] DIRECTIONS = {GO_RIGHT, GO_DOWN, GO_LEFT, GO_UP};

    static final int DIR_RIGHT = 0; // starting direction

    static final int X_DIR = 0;
    static final int Y_DIR = 1;


    public static String[] process(String shape) {

        shape.chars().filter(ch -> ch == '1').count();
        char[][] board = convertShapeStringToCharArray(shape);
        boolean[][] visited = new boolean[board.length][board[0].length];
        List<String> shapes = new ArrayList<>();

        Point corner = findNextValidCorner(board, 0, visited);

        while (corner != null){

            shapes.add(followShape(board, corner, visited));
            board[corner.x][corner.y] = EMPTY;
            System.out.println(shapes.get(shapes.size()-1));
            corner = findNextValidCorner(board, corner.x, visited);
        }

        return shapes.toArray(new String[0]);
    }

    private static String followShape(char[][] board, Point start, boolean[][] visited){
        Point currPoint = start;
        Point nextPoint;
        int currDir = DIR_RIGHT;
        int newDir;
        char[][] justShape = new char[board.length][];
        int minY = start.y;

        for(int i = 0; i< board.length; i++){
            justShape[i] = new char[board[i].length];
            for(int j = 0; j <board[i].length; j++)
                justShape[i][j] = ' ';
        }

        do{
            nextPoint = findNextCornerInDir(board, currPoint, DIRECTIONS[currDir]);
            minY = Math.min(nextPoint.y, minY);
            visited[nextPoint.x][nextPoint.y] = true;
            newDir = getNewShapeDir(board, nextPoint, currDir);

            saveToShape(board, justShape, currPoint, nextPoint, currDir);

            if(newDir == currDir){
                justShape[nextPoint.x][nextPoint.y] = board[nextPoint.x + DIRECTIONS[newDir][X_DIR]][nextPoint.y + DIRECTIONS[newDir][Y_DIR]];
            }else
                justShape[nextPoint.x][nextPoint.y] = CORNER;

            currPoint = nextPoint;
            currDir = newDir;

        }while(!currPoint.equals(start));

        //printShape(justShape);

        return convertShapeToString(justShape, minY);
    }

    private static String convertShapeToString(char[][] justShape, int min) {
        StringBuilder strBuilder = new StringBuilder();
        String current;

        for(char[] line : justShape) {
            current = String.valueOf(line).stripTrailing();
            if(!current.equals("")) {
                if(min > 0)
                    current = current.substring(min);
                strBuilder.append(current).append("\n");
            }
        }

        return strBuilder.toString().stripTrailing();
    }

    private static void saveToShape(char[][] board, char[][] shape, Point start, Point end, int dir){

        for(int x = start.x+DIRECTIONS[dir][X_DIR], y = start.y+DIRECTIONS[dir][Y_DIR];
            x != end.x || y != end.y;
            x += DIRECTIONS[dir][X_DIR], y += DIRECTIONS[dir][Y_DIR])
        {
            shape[x][y] = board[x][y];
        }
    }

    private static int getNewShapeDir(char[][] board, Point point, int dir){

        int nextDir = getOppositeDir(dir);

        do {
            nextDir = getNextDir(nextDir);
        }while(isCellEqual(board, point.x + DIRECTIONS[nextDir][X_DIR], point.y + DIRECTIONS[nextDir][Y_DIR], EMPTY));

        return nextDir;
    }

    private static boolean isCellEqual(char[][] board, int x, int y, char value){
        if(x < 0 || y < 0 || x >= board.length || y >= board[x].length)
            return value == EMPTY;
        return board[x][y] == value;
    }

    private static int getNextDir(int dir){
        int nextDir = (dir-1)%DIRECTIONS.length;
        if(nextDir < 0) // fix negative modulo
            nextDir += DIRECTIONS.length;
        return nextDir;
    }

    private static int getOppositeDir(int currDir){
        return (currDir+2) % DIRECTIONS.length;
    }

    private static Point findNextCornerInDir(char[][] board, Point start, int[] dir) {
        if(dir[X_DIR] == 0) {
            for (int y = start.y + dir[Y_DIR]; y < board[start.x].length && y >= 0; y += dir[Y_DIR]) {
                if (board[start.x][y] == CORNER)
                    return new Point(start.x, y);
            }
        }else { // yDir == 0
            for(int x = start.x + dir[X_DIR]; x < board.length && x >= 0; x += dir[X_DIR])
                if(board[x][start.y] == CORNER)
                    return new Point(x, start.y);
        }
        return new Point(-1, -1); // impossible to reach if shapes are correct
    }

    private static Point findNextValidCorner(char[][] board, int start, boolean[][] visited) {
        for(int i = start; i < board.length; i++)
            for(int j = 0; j < board[i].length; j++)
                if(board[i][j] == CORNER && isValidStartingCorner(board, i, j, visited))
                    return new Point(i, j);
        return null;
    }

    /* To be a valid starting corner, point must have path right and path down,
     additionally it has either at least one additional path, or it hasn't been visited before.
     */
    private static boolean isValidStartingCorner(char[][] board, int x, int y, boolean[][] visited) {
        int allPaths = 0;
        int requiredPath = 0;

        if(isCellEqual(board, x-1, y, VER_SIDE))
            allPaths++;
        if(isCellEqual(board, x+1, y, VER_SIDE)) {
            allPaths++;
            requiredPath++;
        }
        if(isCellEqual(board, x, y+1, HOR_SIDE)) {
            allPaths++;
            requiredPath++;
        }
        if(isCellEqual(board, x, y-1, HOR_SIDE))
            allPaths++;

        if(requiredPath == 2){
            if(allPaths > requiredPath)
                return true;
            else
                return !visited[x][y];
        }else
            return false;
    }

    static char[][] convertShapeStringToCharArray(String shape){
        String[] chunks = shape.split("\n");
        char[][] board = new char[chunks.length][];

        for(int i=0; i< chunks.length; i++) {
            board[i] = new char[chunks[i].length()];
            board[i] = chunks[i].toCharArray();
        }

        return board;
    }
}
