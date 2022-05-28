package com.cyryl.firsttasks;

public class MorseCodeDecoder {

    public static String decodeBits(String bits){
        String trimmedBits = bits.replaceAll("^0+|0+$", "");
        int shortestOnesSequence = Integer.MAX_VALUE;
        int shortestZeroesSequence = Integer.MAX_VALUE;
        int currentOnes = 0;
        int currentZeroes = 0;
        int transmissionRate;

        for(int i=0; i<trimmedBits.length(); i++){
            if (trimmedBits.charAt(i) == '1'){
                currentOnes++;
                if(currentZeroes < shortestZeroesSequence && currentZeroes != 0){
                    shortestZeroesSequence = currentZeroes;
                }
                currentZeroes = 0;
            }else{
                currentZeroes++;
                if(currentOnes < shortestOnesSequence && currentOnes != 0){
                    shortestOnesSequence = currentOnes;
                }
                currentOnes = 0;
            }
        }
        if (shortestZeroesSequence == Integer.MAX_VALUE) {
            return ".";
        }else {

            transmissionRate = shortestOnesSequence < shortestZeroesSequence ? shortestOnesSequence : shortestZeroesSequence;

            trimmedBits = trimmedBits
                    .replaceAll("1{" + (3 * transmissionRate) + "}", "-")
                    .replaceAll("1{" + (transmissionRate) + "}", ".")
                    .replaceAll("0{" + (7 * transmissionRate) + "}", "   ")
                    .replaceAll("0{" + (3 * transmissionRate) + "}", " ")
                    .replaceAll("0{" + (transmissionRate) + "}", "");

            return trimmedBits;
        }
    }

//    public static String decodeBits(String bits) {
//        String trimmedBits = bits.replaceAll("^0+", "").replaceAll("0+$", "");
//
//        int longestZerosSequence = 0;
//        int shortestZeroesSequence = Integer.MAX_VALUE;
//        int currentZerosSequence = 0;
//        int transmissionRate;
//        int currentOnesSequence = 0;
//        int longestOnesSequence = 0;
//        final int PAUSE_BETWEEN_WORDS = 7;
//        final int PAUSE_BETWEEN_LETTERS = 3;
//        StringBuilder solution = new StringBuilder();
//
//        for(int i=0; i<trimmedBits.length(); i++){
//            if(trimmedBits.charAt(i) == '0'){
//                currentZerosSequence++;
//                currentOnesSequence = 0;
//                if (currentZerosSequence > longestZerosSequence){
//                    longestZerosSequence = currentZerosSequence;
//                }
//            }else{
//                currentOnesSequence++;
//                if(currentOnesSequence > longestOnesSequence){
//                    longestOnesSequence = currentOnesSequence;
//                }
//                if (currentZerosSequence < shortestZeroesSequence && currentZerosSequence != 0) {
//                    shortestZeroesSequence = currentZerosSequence;
//                }
//                currentZerosSequence = 0;
//            }
//        }
//
//        if (shortestZeroesSequence == Integer.MAX_VALUE) {
//            return ".";
//        }
//
//        if (shortestZeroesSequence*PAUSE_BETWEEN_WORDS == longestZerosSequence){
//            transmissionRate = longestZerosSequence/PAUSE_BETWEEN_WORDS;
//        }else if(shortestZeroesSequence*PAUSE_BETWEEN_LETTERS == longestZerosSequence){
//            transmissionRate = longestZerosSequence/PAUSE_BETWEEN_LETTERS;
//        }else {
//            if (shortestZeroesSequence < longestZerosSequence) {
//                transmissionRate = longestZerosSequence / PAUSE_BETWEEN_WORDS;
//            }else if(shortestZeroesSequence > longestOnesSequence) {
//                transmissionRate = longestOnesSequence;
//            }else{
//                transmissionRate = shortestZeroesSequence;
//            }
//        }
//
//        for (int i=0; i<trimmedBits.length(); i+=transmissionRate){
//            solution.append(trimmedBits.charAt(i));
//        }
//
//        return solution.toString().replaceAll("1{3}", "-").replaceAll("1", ".")
//                .replaceAll("0{7}", "   ").replaceAll("0{3}", " ").replaceAll("0", "");
//    }

    public static String decode(String morseCode) {
        String letter = "";
        String decodedMessage = "";
        int numberOfSpaces = 0;
        char currentChar = ' ';
        StringBuilder stringBuilderLetter = new StringBuilder(letter);
        StringBuilder stringBuilderMessage = new StringBuilder(decodedMessage);
        boolean messageStarted = false;


        for (int i=0; i<morseCode.length(); i++){
            currentChar = morseCode.charAt(i);
            if (currentChar == ' ' && messageStarted){
                numberOfSpaces++;
                if (numberOfSpaces == 1 ){
//                    stringBuilderMessage.append(MorseCode.get(letter));
                    stringBuilderLetter.setLength(0);
                }
                if (numberOfSpaces == 3){
                    stringBuilderMessage.append(decodedMessage).append(" ");
                    numberOfSpaces = 0;
                }
            }else if (currentChar == '.' || currentChar == '-'){
                messageStarted = true;
                numberOfSpaces = 0;
                letter = stringBuilderLetter.append(currentChar).toString();
            }
        }
        if (currentChar != ' '){
//            decodedMessage = stringBuilderMessage.append(decodedMessage).append(MorseCode.get(letter)).toString();
        }else
            decodedMessage = stringBuilderMessage.toString();
        return decodedMessage;
    }
}
