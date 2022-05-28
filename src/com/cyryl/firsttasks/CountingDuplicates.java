package com.cyryl.firsttasks;

import java.util.HashMap;
import java.util.Map;

public class CountingDuplicates {
    public static int duplicateCount(String text) {
        String textUp = text.toUpperCase();
        int repeatingCharacters = 0;
        int count;
        Map<Character, Integer> map = new HashMap<>();

        for(int i=0; i<textUp.length(); i++){
            if (map.containsKey(textUp.charAt(i))){
                count = map.get(textUp.charAt(i));
                if(count == 1) {
                    repeatingCharacters++;
                }
                map.put(textUp.charAt(i), ++count);
            }else{
                map.put(textUp.charAt(i), 1);
            }
        }
        return repeatingCharacters;
    }
}
