package com.cyryl.firsttasks;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrderString {
    public static String order(String words) {

        String[] sortedString;
        String regex = "\\d";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher;

        if(words.isEmpty())
            return words;

        sortedString = new String[words.split(" ").length];

        for(String word : words.split(" ")){
            matcher = pattern.matcher(word);
            matcher.find();
            sortedString[Character.getNumericValue(word.charAt(matcher.start()))-1] = word;
        }

        return String.join(" ", sortedString);
    }
}
