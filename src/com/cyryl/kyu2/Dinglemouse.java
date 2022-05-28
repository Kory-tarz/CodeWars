package com.cyryl.kyu2;

import java.awt.*;
import java.util.Arrays;

public class Dinglemouse {

    private static final char EXPRESS = 'x';
    private static final char EMPTY = ' ';
    private static final char LEFT_TURN = '\\';
    private static final char RIGHT_TURN = '/';
    private static final char VERTICAL = '|';
    private static final char HORIZONTAL = '-';
    private static final char JUNCTION_STRAIGHT = '+';
    private static final char JUNCTION_X= 'X';
    private static final char STATION = 'S';

    private static final int UP_RIGHT = 7;
    private static final int UP_LEFT = 5;
    private static final int DOWN_LEFT = 3;
    private static final int DOWN_RIGHT = 1;

    private static final int[][] DIRS = {{0,1},{1,1},{1,0},{1,-1},{0,-1},{-1,-1},{-1,0},{-1,1}};
    private static final int DIR_X = 0;
    private static final int DIR_Y = 1;

    public static int trainCrash(final String track, final String aTrain, final int aTrainPos, final String bTrain, final int bTrainPos, final int limit) {

        System.out.println(track);
        System.out.println();
        System.out.println();;

        char[][] board = convertTrack(track);
        Train[] trains = new Train[2];

        trains[0] = createTrain(board, aTrain, aTrainPos);
        trains[1] = createTrain(board, bTrain, bTrainPos);

        int timer = 0;
        boolean collision = false;

        print(board, trains[0], trains[1]);

        while (timer < limit && !collision){
            collision = trains[0].checkCollisions(trains[1]);
            collision = collision || trains[1].checkCollisions(trains[0]);

            if(!collision) {
                timer++;
                for (int i = 0; i < trains.length-1; i++)
                    trains[i].move(board);
            }
            print(board, trains[0], trains[1]);
            if(timer > 20)
                return -1;
        }
        print(board, trains[0], trains[1]);

        return collision ? timer : -1;
    }

    public static void print(char[][] board, Train a, Train b){
        char[][] trainedBoard = new char[board.length][];

        for(int i=0; i< board.length; i++){
            trainedBoard[i] = Arrays.copyOf(board[i], board[i].length);
        }

        for(Point p : a.body)
            trainedBoard[p.x][p.y] = 'a';
        trainedBoard[a.body[0].x][a.body[0].y] = 'A';
        for(Point p : b.body)
            trainedBoard[p.x][p.y] = 'b';
        trainedBoard[b.body[0].x][b.body[0].y] = 'B';

        printArr(trainedBoard);
    }

    public static void printArr(char[][] arr){
        for(int i=0; i<arr.length; i++){
            for(int j=0; j<arr[i].length; j++)
                System.out.print(arr[i][j]);
            System.out.println();
        }
    }

    private static char[][] convertTrack(final String track){
        String[] chunks = track.split("\n");

        char[][] board = new char[chunks.length][];

        for(int i=0; i<chunks.length; i++){
            board[i] = chunks[i].toCharArray();
        }

        return board;
    }

    private static Train createTrain(char[][] board, String trainInfo, int trainPos){
        Point start = findTrackZero(board);
        int[] trainData = findNextTrain(board, start, trainPos, 0);

        return new Train(board,trainInfo, new Point(trainData[0], trainData[1]), trainData[2]);
    }

    private static Point findTrackZero(char[][] board){
        for(int y=0; y<board[0].length; y++)
            if(board[0][y] != EMPTY)
                return new Point(0, y); // first track in top line is always right corner '/' or train
        return new Point(-1, -1);
    }

    private static int[] findNextTrain(char[][] board, Point start, int cellNr, int dir){
        int cell = 0;
        Point curr = start;
        int trackDir = dir;

        while(cell != cellNr) {
            curr = nextTrack(board, curr, trackDir);
            trackDir = trackDirection(board, curr, trackDir);
            cell++;
        }
        return new int[]{curr.x, curr.y, trackDir};
    }

    private static Point nextTrack(char[][] board, Point prev, int trackDir){

        if(trackDir % 2 == 1){ // diagonal direction
            // first check straight paths if they exist it is only possible to get there from prev track

            if(!isEmpty(board, prev.x + DIRS[getNextDir(trackDir)][DIR_X], prev.y + DIRS[getNextDir(trackDir)][DIR_Y])) {
                return new Point(prev.x + DIRS[getNextDir(trackDir)][DIR_X], prev.y + DIRS[getNextDir(trackDir)][DIR_Y]);
            }
            if(!isEmpty(board, prev.x + DIRS[trackDir-1][DIR_X], prev.y + DIRS[trackDir-1][DIR_Y])) {
                return new Point(prev.x + DIRS[trackDir-1][DIR_X], prev.y + DIRS[trackDir-1][DIR_Y]);
            }
            // else continue diagonally is the only option
        }
        // continue straight or continue diagonally
        return new Point(prev.x + DIRS[trackDir][DIR_X], prev.y + DIRS[trackDir][DIR_Y]);
    }

    private static int getNextDir(int dir){
        return (dir+1)%DIRS.length;
    }

    private static int reverseDir(int dir){
        return (dir+4)%DIRS.length; // equal -1 * DIRS
    }

    private static boolean isEmpty(char[][] board, int x, int y){
        if(x >= board.length || x < 0 || y >= board[x].length || y < 0)
            return true;
        return board[x][y] == EMPTY;
    }

    private static int trackDirection(char[][] board, Point track, int reachingDir){
        switch (board[track.x][track.y]){
            case LEFT_TURN:
                if(DOWN_RIGHT-1 <= reachingDir && reachingDir <= DOWN_RIGHT+1) // we are coming from the top
                    return DOWN_RIGHT;
                else // we are coming from the bottom
                    return UP_LEFT;
            case RIGHT_TURN:
                if(DOWN_LEFT-1 <= reachingDir && reachingDir <= DOWN_LEFT+1) // we are coming from the top
                    return DOWN_LEFT;
                else
                    return UP_RIGHT;
            case HORIZONTAL:
                if(reachingDir % 2 == 0)
                    return reachingDir;
                else
                    return findDirId(0, DIRS[reachingDir][DIR_Y]);
            case VERTICAL:
                if(reachingDir % 2 == 0)
                    return reachingDir;
                else
                    return findDirId(DIRS[reachingDir][DIR_X], 0);
            default:
                return reachingDir; // other tracks lead only in one direction
        }
    }

    private static int findDirId(int x, int y) {
        for(int i=0; i<DIRS.length; i++){
            if(DIRS[i][DIR_X] == x && DIRS[i][DIR_Y] == y)
                return i;
        }
        return -1; // bad input
    }

    private static class Train{

        private final boolean clockwiseDir;
        private boolean express;
        private Point[] body;
        private int dir;
        int waitTimer;

        private static final int HEAD = 0;

        public Train(char[][] board, String train, Point pos, int trackDir){
            body = new Point[train.length()];
            body[HEAD] = pos;
            waitTimer = board[pos.x][pos.y] == STATION ? 0 : -1;
            clockwiseDir = Character.isLowerCase(train.charAt(0));
            express = Character.toLowerCase(train.charAt(0)) == EXPRESS;
            this.setBody(board, trackDir);
        }

        private void setBody(char[][] board, int trackDir){
            dir = clockwiseDir ? trackDir : reverseDir(trackDir); // direction of a train

            trackDir = reverseDir(dir); // carriages are in opposite direction to train movement
            Point currPart = nextTrack(board, body[HEAD], trackDir);
            for(int i=1; i<body.length; i++){
                body[i] = currPart;
                trackDir = trackDirection(board, currPart, trackDir);
                currPart = nextTrack(board, currPart, trackDir);
            }
        }

        private void setWaitTimer(){
            waitTimer = express ? 0 : body.length-1;
        }

        public void move(char[][] board){
            if(board[body[HEAD].x][body[HEAD].y] == STATION){
                if(waitTimer <= 0)
                    setWaitTimer();
                else {
                    waitTimer--;
                }
            }
            if(waitTimer > 0)
                return;

            System.out.println("MY P: " + body[HEAD] + " BOARD IS: " + board[body[HEAD].x][body[HEAD].y]);
            System.out.println("MOVING IN DIR: " + DIRS[dir][DIR_X] + ", " + DIRS[dir][DIR_Y]);
            Point head = nextTrack(board, body[HEAD], dir);
            dir = trackDirection(board, head, dir);
            for(int i=body.length-1; i>0; i--){
                body[i] = body[i-1];
            }
            body[HEAD] = head;
        }

        public boolean checkCollisions(Train otherTrain){
            Point head = body[HEAD];

            for(int i=0; i<otherTrain.body.length; i++){
                if(head.equals(otherTrain.body[i]))
                    return true;
            }
            return selfCollision();
        }

        private boolean selfCollision(){
            Point head = body[HEAD];

            for(int i=1; i< body.length; i++)
                if(head.equals(body[i]))
                    return true;
            return false;
        }
    }
}
