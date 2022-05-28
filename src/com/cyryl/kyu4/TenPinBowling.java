package com.cyryl.kyu4;

public class TenPinBowling {

    static final int NO_BONUS = 0;
    static final int SPARE = 1;
    static final int STRIKE = 2;
    static final int DOUBLE_STRIKE = 3;
    static int[] bonusTable = {1, 2, 2, 3};
    static int currentBonus;

    public static int bowlingScore(String frames) {

        String scoreSheet[] = frames.split(" ");
        int finalBonus;
        currentBonus = NO_BONUS;
        int finalScore = 0;

        for(int i=0; i<9; i++){
            finalScore += calculateFrameScore(scoreSheet[i]);
        }

        String lastRoundScore = scoreSheet[9];

        if(lastRoundScore.substring(1).startsWith("/")){
            finalScore += calculateFrameScore(lastRoundScore.substring(0,2));
            currentBonus = NO_BONUS;
            finalScore += calculateFrameScore(lastRoundScore.substring(2) + "0");
        }else if(!lastRoundScore.startsWith("X")){
            finalScore += calculateFrameScore(lastRoundScore);
        }else{
            finalBonus = currentBonus;
            finalScore += calculateFrameScore(lastRoundScore.substring(0,1));
            currentBonus = lowerBonus(finalBonus);
            if(!lastRoundScore.substring(1).startsWith("X")){
                finalScore += calculateFrameScore(lastRoundScore.substring(1));
            }else{
                finalScore += calculateFrameScore(lastRoundScore.substring(1,2));
                currentBonus = NO_BONUS;
                finalScore += calculateFrameScore(lastRoundScore.substring(2) + "0");
            }
        }
        return finalScore;
    }

    public static int calculateFrameScore(String frameScore){
        int newBonus = 0;
        int score = 0;
        int firstThrow;
        int secondThrow;
        if (frameScore.startsWith("X")){
            newBonus = STRIKE;
            score += 10 * bonusTable[currentBonus];
        }else {
            firstThrow = Character.getNumericValue(frameScore.charAt(0));
            secondThrow = Character.getNumericValue(frameScore.charAt(1));
            if (frameScore.endsWith("/")) {
                newBonus = SPARE;
                secondThrow = 10 - firstThrow;
            }
            score += firstThrow * bonusTable[currentBonus];
            currentBonus = lowerBonus(currentBonus);
            score += secondThrow * bonusTable[currentBonus];
        }
        currentBonus = lowerBonus(currentBonus);
        currentBonus += newBonus;
        return score;
    }

    public static int lowerBonus(int bonus){
        if(bonus == DOUBLE_STRIKE)
            return bonus-2;
        if(bonus > 0)
            return bonus-1;
        return 0;
    }
}
