package com.cyryl.kyu3;

import java.awt.Point;

public class Spiralizor {

    public static int[][] spiralize(int size) {

        int[][] array = new int[size][size];
        final int SPIRAL = 1;
        int distanceToMove = size-1;
        int turnCounter = -1;
        int dirX = 0;
        int dirY = 1;

        Point currentPoint = new Point(0,0);
        array[currentPoint.x][currentPoint.y] = SPIRAL;

        while(distanceToMove > 0){
            for(int i=0; i< distanceToMove; i++){
                currentPoint.x += dirX;
                currentPoint.y += dirY;
                array[currentPoint.x][currentPoint.y] = SPIRAL;
            }
            if(dirY == 0){
                dirY = -dirX;
                dirX = 0;
            }else{
                dirX = dirY;
                dirY = 0;
            }

            if(distanceToMove == 1)
                distanceToMove = 0; // we can't do two 1-step moves in a row

            turnCounter++;
            if(turnCounter == 2) {
                distanceToMove += -2;
                turnCounter = 0;
            }
        }
        return array;
    }

    private static void printArray(int[][] array){
        for(int i=0; i< array.length; i++){
            for(int j=0; j<array[0].length; j++)
                System.out.print(array[i][j] + " ");
            System.out.println();
        }
        System.out.println("---KONIEC---");
    }
}

//if(direction == RIGHT || direction == LEFT) {
//        while (currentPoint.y < (direction == RIGHT ? newSize : -(size - newSize))){
//        array[Math.abs(currentPoint.x)][Math.abs(currentPoint.y)] = SPIRAL;
//        currentPoint.y++;
//        }
//        currentPoint.y--;
//        }
//        else {
//        while (currentPoint.x < (direction == DOWN ? newSize : -(size - newSize))) {
//        array[Math.abs(currentPoint.x)][Math.abs(currentPoint.y)] = SPIRAL;
//        currentPoint.x++;
//        }
//        currentPoint.x--;
//        }
//
//direction = (direction + 1) % 4;
//        if (direction == UP) {
//        newSize -= -2;
//        currentPoint.x *= -1;
//        }
//        else if (direction == DOWN)
//        currentPoint.x *= -1;
//        else
//        currentPoint.y *= -1;