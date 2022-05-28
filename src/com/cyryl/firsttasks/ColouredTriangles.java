package com.cyryl.firsttasks;


public class ColouredTriangles {

    static final int GREEN = 0;
    static final int RED = 1;
    static final int BLUE = 2;

    public static char triangle(final String row){

        int[] digitsOfInputLength = convertToBase3(row.length()-1);
        int[] digitsTemp;
        int result = 0;
        int sign = (row.length() % 2)*2 -1;

        for(int i=0; i<row.length(); i++){
            digitsTemp = convertToBase3(i);
            result = (result + (lucasTheorem(digitsOfInputLength, digitsTemp) * charToInt(row.charAt(i)))) % 3;
        }
        result = (3 + sign * (result % 3)) % 3;
        return intToChar(result);
    }

    private static int lucasTheorem(int[] n, int[] m){
        int result = 1;
        for(int i=0; i<n.length; i++){
            result = (result * calculateBinomial(n[i], (i < m.length) ? m[i] : 0)) % 3;
        }

        return result;
    }

    private static int[] convertToBase3(int number){
        if(number == 0)
            return new int[1];
        int length = ((int)(Math.log(number)/Math.log(3))+1);
        int[] result = new int[length];
        int i = 0;
        while (i < length && number > 0){
            result[i] += number % 3;
            number = number / 3;
            i++;
        }

        return result;
    }

    private static int calculateBinomial(int n, int k){
        if (n < k)
            return 0;
        else return switch (n) {
            case 0, 1 -> 1;
            case 2 -> (k==1) ? 2 : 1;
            default -> 0;
        };
    }

    private static int charToInt(char letter){
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

//public class ColouredTriangles {
//
//    static final int sumRGB = 'R' + 'G' + 'B';
//    static int[] middleR = new int[5];
//    static int[] middleG = new int[5];
//    static int[] middleB = new int[5];
//
//    private static void calculate(){
//        middleR[0] = 'R' + 'R' + 'R';
//        middleR[1] = 'G' + 'R' + 'R';
//        middleR[2] = 'B' + 'R' + 'R';
//        middleR[3] = 'B' + 'R' + 'B';
//        middleR[4] = 'G' + 'R' + 'G';
//
//        middleB[0] = 'B' + 'B' + 'B';
//        middleB[1] = 'G' + 'B' + 'B';
//        middleB[2] = 'B' + 'B' + 'R';
//        middleB[3] = 'R' + 'B' + 'R';
//        middleB[4] = 'G' + 'B' + 'G';
//
//        middleG[0] = 'G' + 'G' + 'G';
//        middleG[1] = 'G' + 'G' + 'R';
//        middleG[2] = 'G' + 'G' + 'B';
//        middleG[3] = 'B' + 'G' + 'B';
//        middleG[4] = 'R' + 'G' + 'R';
//    }
//
//    public static char triangle(final String row) {
//
//        calculate();
//
//        StringBuilder stringBuilder = new StringBuilder("");
//        String currentLevel = row;
//        int sum;
//        int firstIndex;
//
//
//        while(currentLevel.length() >= 3){
//            sum = currentLevel.charAt(0)+currentLevel.charAt(1);
//            firstIndex = 0;
//            while(firstIndex < currentLevel.length()-2) {
//                sum += currentLevel.charAt(firstIndex+2);
//                if (currentLevel.charAt(firstIndex+1) == 'R'){
//                    if(sum == middleR[0] || sum==sumRGB)
//                        stringBuilder.append('R');
//                    else if(sum == middleR[3] || sum == middleR[1])
//                        stringBuilder.append('G');
//                    else
//                        stringBuilder.append('B');
//                }else if (currentLevel.charAt(firstIndex+1) == 'B'){
//                    if(sum == middleB[0] || sum==sumRGB)
//                        stringBuilder.append('B');
//                    else if(sum == middleB[3] || sum == middleB[1])
//                        stringBuilder.append('G');
//                    else
//                        stringBuilder.append('R');
//                }else {
//                    if(sum == middleG[0] || sum==sumRGB)
//                        stringBuilder.append('G');
//                    else if(sum == middleG[3] || sum == middleG[1])
//                        stringBuilder.append('R');
//                    else
//                        stringBuilder.append('B');
//                }
//                sum -= currentLevel.charAt(firstIndex);
//                firstIndex++;
//            }
//            currentLevel = stringBuilder.toString();
//            stringBuilder.setLength(0);
//        }
//        if (currentLevel.length()==1)
//            return currentLevel.charAt(0);
//        else
//            return solveForTwoLetters(currentLevel);
//    }
//
//    private static char solveForTwoLetters(String lastRow) {
//        if (lastRow.charAt(0) == lastRow.charAt(1))
//            return lastRow.charAt(0);
//        else
//            return getDifferentChar(lastRow.charAt(0), lastRow.charAt(1));
//    }
//
//    public static char getDifferentChar(char a, char b){
//
//        return (char)(sumRGB - a - b);
//    }
//}

//    public static char triangle(final String row) {
//
//
//        StringBuilder stringBuilder = new StringBuilder("");
//        Map<Character, Integer> map = new HashMap<>(3);
//        String currentLevel = row;
//        int count;
//        int firstIndex;
//
//        clearMap(map);
//
//        while(currentLevel.length() >= 3){
//            map.put(currentLevel.charAt(0), 1);
//            map.put(currentLevel.charAt(1), map.get(currentLevel.charAt(1))+1);
//            firstIndex = 0;
//            while(firstIndex+2 < currentLevel.length()) {
//                map.put(currentLevel.charAt(firstIndex+2), map.get(currentLevel.charAt(firstIndex+2)) + 1);
//                count = map.get(currentLevel.charAt(firstIndex+1));
//                if (count == 2) {
//                    if (currentLevel.charAt(firstIndex) != currentLevel.charAt(firstIndex+1))
//                        stringBuilder.append(currentLevel.charAt(firstIndex));
//                    else
//                        stringBuilder.append(currentLevel.charAt(firstIndex+2));
//                }else if (count == 1){
//                    if (map.get(currentLevel.charAt(firstIndex)) == 1)
//                        stringBuilder.append(currentLevel.charAt(firstIndex+1));
//                    else
//                        stringBuilder.append(getDifferentChar(currentLevel.charAt(firstIndex), currentLevel.charAt(firstIndex+1)));
//                }else{
//                    stringBuilder.append(currentLevel.charAt(firstIndex+1));
//                }
//                map.put(currentLevel.charAt(firstIndex), map.get(currentLevel.charAt(firstIndex)) - 1);
//                firstIndex++;
//            }
//            currentLevel = stringBuilder.toString();
//            stringBuilder.setLength(0);
//            clearMap(map);
//        }
//        if (currentLevel.length()==1)
//            return currentLevel.charAt(0);
//        else
//            return solveForTwoLetters(currentLevel);
//    }
//
//    private static char solveForTwoLetters(String lastRow) {
//        if (lastRow.charAt(0) == lastRow.charAt(1))
//            return lastRow.charAt(0);
//        else
//            return getDifferentChar(lastRow.charAt(0), lastRow.charAt(1));
//    }
//
//    public static void clearMap(Map<Character, Integer> map){
//        map.put('R', 0);
//        map.put('G', 0);
//        map.put('B', 0);
//    }
//
//    public static char getDifferentChar(char a, char b){
//
//        return (char)(sumRGB - a - b);
//    }
//}


//public class ColouredTriangles {
//    public static char triangle(final String row) {
//
//        StringBuilder stringBuilder = new StringBuilder("");
//        Map<Character, Integer> map = new HashMap<>();
//        String currentLevel = row;
//        int count;
//        int firstIndex;
//        int middleIndex;
//        int lastIndex;
//
//        clearMap(map);
//
//        while(currentLevel.length() >= 3){
//            map.put(currentLevel.charAt(0), 1);
//            map.put(currentLevel.charAt(1), map.get(currentLevel.charAt(1))+1);
//            firstIndex = 0;
//            middleIndex = 1;
//            lastIndex = 2;
//            while(lastIndex < currentLevel.length()) {
//                map.put(currentLevel.charAt(lastIndex), map.get(currentLevel.charAt(lastIndex)) + 1);
//                count = map.get(currentLevel.charAt(middleIndex));
//                if (count == 3) {
//                    stringBuilder.append(currentLevel.charAt(middleIndex));
//                } else if (count == 2) {
//                    if (currentLevel.charAt(firstIndex) != currentLevel.charAt(middleIndex))
//                        stringBuilder.append(currentLevel.charAt(firstIndex));
//                    else
//                        stringBuilder.append(currentLevel.charAt(lastIndex));
//                } else {
//                    if (map.get(currentLevel.charAt(firstIndex)) == 1)
//                        stringBuilder.append(currentLevel.charAt(middleIndex));
//                    else
//                        stringBuilder.append(getDifferentChar(currentLevel.charAt(firstIndex), currentLevel.charAt(middleIndex)));
//                }
//                map.put(currentLevel.charAt(firstIndex), map.get(currentLevel.charAt(firstIndex)) - 1);
//                firstIndex++;
//                middleIndex++;
//                lastIndex++;
//            }
//            currentLevel = stringBuilder.toString();
//            stringBuilder.setLength(0);
//            clearMap(map);
//        }
//        if (currentLevel.length()==1)
//            return currentLevel.charAt(0);
//        else
//            return solveForTwoLetters(currentLevel);
//    }
//
//    private static char solveForTwoLetters(String lastRow) {
//        if (lastRow.charAt(0) == lastRow.charAt(1))
//            return lastRow.charAt(0);
//        else
//            return getDifferentChar(lastRow.charAt(0), lastRow.charAt(1));
//    }
//
//    public static void clearMap(Map<Character, Integer> map){
//        map.put('R', 0);
//        map.put('G', 0);
//        map.put('B', 0);
//    }
//
//    public static char getDifferentChar(char a, char b){
//        if('R' != a && 'R' != b)
//            return  'R';
//        if('G' != a && 'G' != b)
//            return 'G';
//        else
//            return  'B';
//    }
//}
//
