package com.cyryl.firsttasks;

public class PigLatin {
    public static String pigIt(String str) {
        String result = "";
        for(String word: str.split(" ")) {
            if (word.matches(".*[a-zA-Z]+.*")) {
                if (word.length() > 1 && (word.toUpperCase().charAt(0) >= 'A' && word.toUpperCase().charAt(0) <= 'Z')) {
                    result += word.substring(1) + word.charAt(0) + "ay ";
                } else {
                    result += word + "ay ";
                }
            }else{
                result += word + " ";
            }
        }
        return result.trim();
    }
}
