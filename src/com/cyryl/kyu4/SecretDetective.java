package com.cyryl.kyu4;

import java.util.HashMap;
import java.util.Map;

public class SecretDetective {

    public Map<Character, String> significantLetters = new HashMap<>();

    public String recoverSecret(char[][] triplets) {

        StringBuilder stringBuilder = new StringBuilder("");
        String nextLetter;

        for(char[] triplet: triplets){
            setTriplet(triplet);
        }

        while(!significantLetters.isEmpty()){
            nextLetter = String.valueOf(getNextLetter());
            removeLetter(nextLetter);
            stringBuilder.append(nextLetter);
        }
        return stringBuilder.toString();
    }

    public void setTriplet(char[] triplet){
        for(int i=0; i<triplet.length; i++){
            if(!significantLetters.containsKey(triplet[i])){
                significantLetters.put(triplet[i],  (i>=2 ? "" + triplet[1] : "") + (i>=1 ? "" + triplet[0] : "")) ;
            }else{
                significantLetters.put(triplet[i], significantLetters.get(triplet[i]) + (i>=2 ? "" + triplet[1] : "") + (i>=1 ? "" + triplet[0] : ""));
            }
        }
    }

    public Character getNextLetter(){
        for (Character key: significantLetters.keySet()){
            if(significantLetters.get(key).isBlank()){
                significantLetters.remove(key);
                return key;
            }
        }
        return null;
    }

    public void removeLetter(String letter){
        significantLetters.replaceAll((k, v) -> significantLetters.get(k).replaceAll(letter, ""));
    }
}
