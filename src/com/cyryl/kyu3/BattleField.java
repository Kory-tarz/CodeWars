package com.cyryl.kyu3;

public class BattleField {
    static int[][] validationField;
    static final int FIELD_SIZE = 10;
    static final int MAX_SHIP_SIZE = 4;

    public static boolean fieldValidator(int[][] field) {
        validationField = new int[FIELD_SIZE][FIELD_SIZE];
        int[] numberOfShips = new int[MAX_SHIP_SIZE+1];

        for(int i=0; i<field.length; i++){
            for(int j=0; j<field[0].length; j++){
                if(field[i][j] == 1){
                    // Field was locked before or ship is too long
                    if(validationField[i][j] < 0 || validationField[i][j] >= MAX_SHIP_SIZE)
                        return false;

                    // If this field was marked by another part of the ship before, we remember that this ship is longer
                    numberOfShips[validationField[i][j]]--;
                    validationField[i][j]++;
                    numberOfShips[validationField[i][j]]++;
                    setLockedSquares(i, j);
                }
            }
        }
        return (numberOfShips[1] == 4 && numberOfShips[2] == 3 && numberOfShips[3] == 2 && numberOfShips[4] == 1);
    }

    public static void setLockedSquares(int row, int column){
        // east square
        if(column+1 < FIELD_SIZE && validationField[row][column+1] != -1){
            validationField[row][column+1] = validationField[row][column];
        }
        // south-west square
        if(row+1 < FIELD_SIZE && column-1 >= 0){
            validationField[row+1][column-1] = -1;
        }
        // south square
        if(row+1 < FIELD_SIZE){
            validationField[row+1][column] = validationField[row][column];
        }
        // south-east square
        if(row+1 < FIELD_SIZE && column+1 < FIELD_SIZE){
            validationField[row+1][column+1] = -1;
        }
    }
}
