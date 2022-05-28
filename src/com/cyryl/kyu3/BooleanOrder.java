package com.cyryl.kyu3;

import java.math.BigInteger;

public class BooleanOrder {

    BigInteger[][][] memory;

    String operands;
    String operators;

    public BooleanOrder(final String operands, final String operators) {
        this.operands = operands;
        this.operators = operators;
        memory = new BigInteger[2][operands.length()][operands.length()];
        setMemoryToNegativeOne();
    }

    public BigInteger mySolve(int leftIndex, int rightIndex, boolean expected){
        BigInteger result = BigInteger.ZERO;
        BigInteger tempResult;

        if(leftIndex == rightIndex) {
            tempResult = getLogicalValue(leftIndex) == expected ? BigInteger.ONE : BigInteger.ZERO;
            memory[expected ? 1 : 0][leftIndex][rightIndex] = tempResult;
            return tempResult;
        }

        for(int splitIndex=leftIndex; splitIndex<rightIndex; splitIndex++){
            switch(operators.charAt(splitIndex)) {
                case '&':
                    if (expected) {
                        tempResult = solveBothSidesOfOperator(leftIndex, rightIndex, splitIndex, true, true);
                    } else {
                        tempResult = solveBothSidesOfOperator(leftIndex, rightIndex, splitIndex, true, false);
                        tempResult = tempResult.add(solveBothSidesOfOperator(leftIndex, rightIndex, splitIndex, false, false));
                        tempResult = tempResult.add(solveBothSidesOfOperator(leftIndex, rightIndex, splitIndex, false, true));
                    }
                    break;
                case '^':
                    if (expected) {
                        tempResult = solveBothSidesOfOperator(leftIndex, rightIndex, splitIndex, true, false);
                        tempResult = tempResult.add(solveBothSidesOfOperator(leftIndex, rightIndex, splitIndex, false, true));
                    } else {
                        tempResult = solveBothSidesOfOperator(leftIndex, rightIndex, splitIndex, true, true);
                        tempResult = tempResult.add(solveBothSidesOfOperator(leftIndex, rightIndex, splitIndex, false, false));
                    }
                    break;
                case '|':
                    if (expected) {
                        tempResult = solveBothSidesOfOperator(leftIndex, rightIndex, splitIndex, true, true);
                        tempResult = tempResult.add(solveBothSidesOfOperator(leftIndex, rightIndex, splitIndex, true, false));
                        tempResult = tempResult.add(solveBothSidesOfOperator(leftIndex, rightIndex, splitIndex, false, true));
                    } else {
                        tempResult = solveBothSidesOfOperator(leftIndex, rightIndex, splitIndex, false, false);
                    }
                    break;
                default:
                    tempResult = BigInteger.valueOf(-1);
                    break;
            }
            result = result.add(tempResult);
            memory[expected ? 1 : 0][leftIndex][rightIndex] = result;

        }
        return result;
    }

    public BigInteger solveBothSidesOfOperator(int leftIndex, int rightIndex, int splitIndex, boolean leftExpected, boolean rightExpected){
        BigInteger leftResult, rightResult;
        if(wasThisCalculated(leftIndex, splitIndex, leftExpected)) {
            leftResult = memory[boolToInt(leftExpected)][leftIndex][splitIndex];
        }
        else {
            leftResult = mySolve(leftIndex, splitIndex, leftExpected);
        }
        if(wasThisCalculated(splitIndex+1, rightIndex, rightExpected)) {
            rightResult = memory[boolToInt(rightExpected)][splitIndex + 1][rightIndex];
        }
        else {
            rightResult = mySolve(splitIndex + 1, rightIndex, rightExpected);
        }
        return leftResult.multiply(rightResult);
    }


    public BigInteger solve() {
        return mySolve(0, operands.length()-1, true);
    }

    private boolean getLogicalValue(int index){
        return operands.charAt(index) == 't';
    }

    private int boolToInt(boolean value){
        return value ? 1 : 0;
    }

    public boolean wasThisCalculated(int leftIndex, int rightIndex, boolean value){
        return !(memory[value ? 1 : 0][leftIndex][rightIndex].equals(BigInteger.valueOf(-1)));
    }

    private void setMemoryToNegativeOne(){
        for(int i=0; i<memory.length; i++)
            for(int j=0; j<memory[0].length; j++)
                for(int k=0; k<memory[0][0].length; k++)
                memory[i][j][k] = BigInteger.valueOf(-1);
    }
}

//        if(wasThisCalculated(leftIndex, rightIndex, expected)){
//            return memory[expected ? 1 : 0][leftIndex][rightIndex];
//        }
