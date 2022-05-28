package com.cyryl.firsttasks;

public class XO {
    public static boolean getXO (String str) {
        int equality = 0;
        for(int i=0; i<str.length(); i++){
            if(str.charAt(i) == 'X' || str.charAt(i) == 'x') {
                equality--;
            }else if(str.charAt(i) == 'o' || str.charAt(i) == 'O'){
                equality++;
            }
        }
        return (equality == 0);
    }
}
