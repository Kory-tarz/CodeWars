package com.cyryl.kyu4;

import java.util.Arrays;
import java.util.List;

public class StripComments {

    public static String stripComments(String text, String[] commentSymbols) {

        boolean commentedText = false;
        String result = "";
        String lineResult = "";
        List<String> symbolsList = Arrays.asList(commentSymbols);

        for(String line: text.stripTrailing().split("\n")){
            lineResult = line;
            for(int i = 0; i < line.length(); i++){
                if(!commentedText && symbolsList.contains(Character.toString(line.charAt(i)))){
                    commentedText = true;
                    lineResult = line.substring(0, i).stripTrailing() + line.charAt(i);
                }
            }
            result = result + lineResult.stripTrailing() + "\n";
            commentedText = false;
        }
        result = result.stripTrailing();
        for(String symbol : symbolsList){
            result = result.replaceAll(symbol, "");
            result = result.replaceAll("\\" + symbol, "");
        }
        return result;
    }
}

//    boolean commentedText = false;
//    String result = "";
//    String lineResult = "";
//
//        for(String line: text.stripTrailing().split("\n")){
//                for(String word : line.split(" ")){
//                if (!commentedText && !word.isEmpty() && Arrays.asList(commentSymbols).contains(word.substring(0,1))) {
//                commentedText = true;
//                }
//                if (!commentedText)
//                lineResult = lineResult + word + " ";
//                }
//                result = result + lineResult.stripTrailing() + "\n";
//                lineResult = "";
//                commentedText = false;
//                }
//                return result.trim();
