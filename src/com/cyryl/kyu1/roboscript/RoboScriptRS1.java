package com.cyryl.kyu1.roboscript;

import java.awt.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class RoboScriptRS1 {

    private static final int[][] DIRS = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    private final char[] code;
    private int direction;
    private final Set<Point> visitedPositions;
    private final Point minPos;
    private final Point maxPos;
    private Point currPos;

    public RoboScriptRS1(String code) {
        this.code = code.toCharArray();
        direction = 0;
        currPos = new Point(0, 0);
        minPos = new Point(0, 0);
        maxPos = new Point(0, 0);
        visitedPositions = new HashSet<>();
        visitedPositions.add(currPos);
    }

    public static String execute(String code) {
        return new RoboScriptRS1(code).execute();
    }

    public String execute() {
        int currCommand = 0;
        while (currCommand < code.length) {
            char command = code[currCommand];
            currCommand++;
            StringBuilder sb = new StringBuilder();
            while (currCommand < code.length && Character.isDigit(code[currCommand])) {
                sb.append(code[currCommand]);
                currCommand++;
            }
            int count = sb.isEmpty() ? 1 : Integer.parseInt(sb.toString());
            if (command == 'F') {
                moveForward(count);
            } else {
                changeDirection(count, command);
            }
        }
        return drawMovement();
    }

    private String drawMovement() {
        Point shift = new Point(minPos.x, minPos.y);
        char[][] board = new char[maxPos.x - minPos.x + 1][maxPos.y - minPos.y + 1];
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[x].length; y++) {
                Point point = new Point(x + shift.x, y + shift.y);
                if (visitedPositions.contains(point)) {
                    board[x][y] = '*';
                } else {
                    board[x][y] = ' ';
                }
            }
        }
        return Arrays.stream(board)
                .map(String::new)
                .collect(Collectors.joining("\r\n"));
    }

    private void moveForward(int count) {
        for (int step = 0; step < count; step++) {
            currPos = new Point(currPos.x + DIRS[direction][0], currPos.y + DIRS[direction][1]);
            visitedPositions.add(currPos);
            minPos.x = Math.min(currPos.x, minPos.x);
            minPos.y = Math.min(currPos.y, minPos.y);
            maxPos.x = Math.max(currPos.x, maxPos.x);
            maxPos.y = Math.max(currPos.y, maxPos.y);
        }
    }

    private void changeDirection(int count, char dir) {
        int rotation = dir == 'R' ? 1 : -1;
        direction = ((direction + rotation * count % DIRS.length) + DIRS.length) % DIRS.length;
    }
}
