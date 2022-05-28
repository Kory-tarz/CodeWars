package com.cyryl.kyu3;

public class RailFenceCipher {
    static String decode(String s, int n) {
        char[] originalMessage = new char[s.length()];
        int codeIndex = 0;
        int railIndex = 0;

        for (int rail = n; rail > 0; rail--) {
            for (int originalIndex = railIndex; originalIndex < s.length(); originalIndex += 2 * (n - 1)) {
                originalMessage[originalIndex] = s.charAt(codeIndex);
                codeIndex++;
                if (!(rail == 1 || rail == n) && (originalIndex + 2 * (rail - 1) < s.length())){
                    originalMessage[originalIndex + 2 * (rail - 1)] = s.charAt(codeIndex);
                    codeIndex++;
                }
            }
            railIndex++;
        }
        return String.valueOf(originalMessage);
    }

    static String encode(String s, int n) {
        StringBuilder stringBuilder = new StringBuilder();
        int railIndex = 0;

        for (int rail = n; rail > 0; rail--) {
            for (int i = railIndex; i < s.length(); i = i + 2 * (n - 1)) {
                stringBuilder.append(s.charAt(i));
                if (!(rail == 1 || rail == n)) {
                    if (i + 2 * (rail - 1) < s.length())
                        stringBuilder.append(s.charAt(i + 2 * (rail - 1)));
                }
            }
            railIndex++;
        }
        return stringBuilder.toString();
    }
}

