package com.cyryl.firsttasks;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Scramblies {
    public static boolean scramble(String str1, String str2) {
        Map<Character, Integer> map = new HashMap<>();
        for(int i = 0; i<str2.length(); i++){
            if (map.containsKey(str2.charAt(i))){
                map.put(str2.charAt(i), map.get(str2.charAt(i))+1);
            }else{
                map.put(str2.charAt(i), 1);
            }
        }
        for(int i=0; i<str1.length(); i++){
            if (map.containsKey(str1.charAt(i)))
                map.put(str1.charAt(i), map.get(str1.charAt(i))-1);
        }
        return Collections.max(map.values()) <= 0;
    }
}
