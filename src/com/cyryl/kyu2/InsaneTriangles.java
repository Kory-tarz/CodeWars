package com.cyryl.kyu2;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InsaneTriangles {
    static final int GREEN = 0;
    static final int RED = 1;
    static final int BLUE = 2;
    static final int COLORS = 3;

    public static char triangle(final String row){
        List<Integer> rowNum = rowToListOfNumbers(row);
        int distance = findLongestPredictableRow(rowNum);
        while (distance != -1){
            rowNum = calculateFurtherRow(rowNum, distance);
            distance = findLongestPredictableRow(rowNum);
        }
        return bruteForce(rowNum);
    }

    private static int findLongestPredictableRow(List<Integer> row){
        long i = 3;
        if(row.size() <= i){
            return -1;
        }
        while(i * 3 + 1 <= row.size()){
            i = i * 3;
        }
        return (int)i+1;
    }

    private static List<Integer> calculateFurtherRow(List<Integer> row, int distance){
        List<Integer> newRow = new ArrayList<>();
        for (int i = 0; i + distance - 1 < row.size(); i++) {
            newRow.add(getColor(row.get(i), row.get(i+distance-1)));
        }
        return newRow;
    }

    private static char bruteForce(List<Integer> row){
        List<Integer> nextRow;

        while(row.size() > 1){
            nextRow = new ArrayList<>();
            for (int i = 0; i < row.size()-1; i++) {
                nextRow.add(getColor(row.get(i), row.get(i+1)));
            }
            row = nextRow;
        }
        return intToChar(row.get(0));
    }

    private static List<Integer> rowToListOfNumbers(String row){
        return row.chars()
                .mapToObj(InsaneTriangles::intValueOfCharToInt)
                .collect(Collectors.toList());
    }

    private static int getColor(int col, int otherColor){
        if(col == otherColor){
            return col;
        }
        return COLORS - (col + otherColor);
    }

    private static Integer intValueOfCharToInt(int letter){
        return switch (letter) {
            case 'R' -> RED;
            case 'B' -> BLUE;
            case 'G' -> GREEN;
            default -> -1;
        };
    }

    private static char intToChar(int letter){
        return switch (letter) {
            case RED -> 'R';
            case BLUE -> 'B';
            case GREEN -> 'G';
            default -> '\0';
        };
    }
}
