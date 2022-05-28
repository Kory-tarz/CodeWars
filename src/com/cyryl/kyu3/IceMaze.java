package com.cyryl.kyu3;

import java.awt.Point;
import java.util.*;

public class IceMaze {

    private static final char START = 'S';
    private static final char END = 'E';
    private static final char SLIPPERY = ' ';
    private static final char NORMAL = 'x';
    private static final char OBSTACLE = '#';

    public static List<Character> solve(String map) {

        char[][] board = convertMap(map);
        int[][] visited = new int[board.length][board[0].length];
        for(int[] row : visited)
            Arrays.fill(row, -1 >>> 1);

        Point start = findStart(board);
        if(start == null)
            return null;
        board[start.x][start.y] = SLIPPERY;
        visited[start.x][start.y] = 0;

        return play(board, start, visited);
    }

    static char[][] convertMap(String map){
        String[] chunks = map.split("\n");
        char[][] board = new char[chunks.length][chunks[0].length()];

        for(int i=0; i< chunks.length; i++)
            board[i] = chunks[i].toCharArray();

        return board;
    }

    private static Point findStart(char[][] board) {
        for(int i = 0; i < board.length; i++)
            for(int j = 0; j < board[0].length; j++){
                if(board[i][j] == START)
                    return new Point(i, j);
            }
        return null;
    }

    private static List<Character> play(char[][] board, Point start, int[][] visited) {
        Queue<PathTree> queue = new LinkedList<>();
        Queue<PathTree> nextLevelQueue = new LinkedList<>();
        queue.offer(new PathTree(null, start, START, 0, 0));
        PathTree currPath;
        boolean solutionFound = false;
        List<PathTree> solutions = new ArrayList<>();

        while (!queue.isEmpty()){

            currPath = queue.poll();
            if(board[currPath.pos.x][currPath.pos.y] == END) {
                solutionFound = true;
                solutions.add(currPath);
            }else if (!solutionFound)
                tryMoving(currPath, board, visited, nextLevelQueue);

            if(!solutionFound && queue.isEmpty()) {
                queue = nextLevelQueue;
                nextLevelQueue = new LinkedList<>();
            }
        }

        if(!solutionFound)
            return null;
        else return recreatePath(findShortestPath(solutions));

    }

    private static PathTree findShortestPath(List<PathTree> solutions) {
        int minMoves = -1 >>> 1;
        int currDist;
        int bestPath = 0;

        for(int i=0; i<solutions.size(); i++){
            currDist = solutions.get(i).dist;
            minMoves = Math.min(currDist, minMoves);
            if(minMoves == currDist)
                bestPath = i;
        }
        return solutions.get(bestPath);
    }

    /* Try moving in each direction */
    private static void tryMoving(PathTree currPath, char[][] board, int[][] visited, Queue<PathTree> queue) {
        // move up
        moveDir(currPath, board, visited, queue, -1, 0, 'u');
        // move down
        moveDir(currPath, board, visited, queue, 1, 0, 'd');
        // move right
        moveDir(currPath, board, visited, queue, 0, 1, 'r');
        // move left
        moveDir(currPath, board, visited, queue, 0, -1, 'l');
    }

    /* If move is possible visit cell and add point to the queue */
    private static void moveDir(PathTree currPath, char[][] board, int[][] visited, Queue<PathTree> queue, int xDir, int yDir, char move){
        int x = currPath.pos.x;
        int y = currPath.pos.y;
        int count = 0;
        while(x+xDir >= 0 && x+xDir < board.length && y+yDir >= 0 && y+yDir < board[0].length && board[x+xDir][y+yDir] != OBSTACLE){
            x += xDir;
            y += yDir;
            count++;
            if(board[x][y] == NORMAL || board[x][y] == END)
                break;
        }
        if(currPath.level + 1 <= visited[x][y]){
            visited[x][y] = currPath.level + 1;
            queue.offer(new PathTree(currPath, new Point(x,y), move, currPath.dist + count, currPath.level+1));
        }
    }

    private static List<Character> recreatePath(PathTree path) {
        LinkedList<Character> moves = new LinkedList<>();
        while(path.parent != null){
            moves.addFirst(path.move);
            path = path.parent;
        }
        return moves;
    }

    private static class PathTree{
        PathTree parent;
        Point pos;
        char move;
        int dist;
        int level;

        PathTree(PathTree parent, Point pos, char move, int dist, int level){
            this.parent = parent;
            this.move = move;
            this.pos = pos;
            this.dist = dist;
            this.level = level;
        }
    }
}
