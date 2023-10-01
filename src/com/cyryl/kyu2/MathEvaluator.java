package com.cyryl.kyu2;

import java.util.ArrayDeque;
import java.util.Map;
import java.util.function.BiFunction;

public class MathEvaluator {

    private static final BiFunction<Double, Double, Double> multiply = (a, b) -> a * b;
    private static final BiFunction<Double, Double, Double> divide = (a, b) -> a / b;
    private static final BiFunction<Double, Double, Double> subtract = (a, b) -> a - b;
    private static final BiFunction<Double, Double, Double> add = (a, b) -> a + b;

    private static final Map<Character, BiFunction<Double, Double, Double>> operationMap = Map.of(
            '*', multiply,
            '/', divide,
            '-', subtract,
            '+', add);

    private static final char CLOSING_BRACKET = ')';
    private static final char OPENING_BRACKET = '(';

    private ArrayDeque<BiFunction<Double, Double, Double>> operationsStack;
    private ArrayDeque<Double> numbersStack;

    public double calculate(String expression) {
        operationsStack = new ArrayDeque<>();
        numbersStack = new ArrayDeque<>();
        evaluate(expression, 0, 0, 0);
        if (numbersStack.isEmpty()) {
            return 0d;
        }
        return numbersStack.pop();
    }

    private int evaluate(String expression, int currPos, int startingNumCount, int startingOpCount) {
        String trimmedExpression = expression.trim();
        while (currPos < trimmedExpression.length()) {
            char currChar = expression.charAt(currPos);
            if (currChar == CLOSING_BRACKET) {
                finalize(startingNumCount, startingOpCount);
                return currPos + 1;
            } else if (currChar == OPENING_BRACKET) {
                currPos = evaluate(expression, currPos + 1, numbersStack.size(), operationsStack.size());
                doPriorityOperations(startingNumCount, startingOpCount);
            } else if (operationMap.containsKey(currChar)) {
                doOperation(currChar);
                currPos++;
            } else if (isNumber(currChar)) {
                currPos = readNumber(expression, currPos, startingNumCount, startingOpCount);
            } else {
                currPos++;
            }
        }
        finalize(startingNumCount, startingOpCount);
        return currPos + 1;
    }

    private void finalize(int numCount, int opCount) {
        while (canPerformOperation(numCount, opCount)) {
            performOperation();
        }
    }

    private boolean isNumber(char c) {
        return c >= '0' && c <= '9';
    }


    private void doOperation(char operation) {
        if (operation == '-') {
            if (numbersStack.size() > operationsStack.size()) {
                operationsStack.push(add);
            }
            numbersStack.push(-1d);
            operationsStack.push(multiply);
        } else {
            operationsStack.push(operationMap.get(operation));
        }
    }

    private int readNumber(String expression, int startPos, int startingNumCount, int startingOpCount) {
        int newPos = startPos;
        while (newPos < expression.length() && (isNumber(expression.charAt(newPos)) || expression.charAt(newPos) == '.')) {
            newPos++;
        }
        numbersStack.push(Double.parseDouble(expression.substring(startPos, newPos)));
        doPriorityOperations(startingNumCount, startingOpCount);
        return newPos;
    }

    private void doPriorityOperations(int startingNumCount, int startingOpCount) {
        while (canPerformOperation(startingNumCount, startingOpCount) && isPriorityOperationAvailable()) {
            performOperation();
        }
    }

    private boolean isPriorityOperationAvailable() {
        BiFunction<Double, Double, Double> op = operationsStack.peek();
        return op == multiply || op == divide;
    }

    private boolean canPerformOperation(int startingNumCount, int startingOpCount) {
        return numbersStack.size() - startingNumCount >= 2 && operationsStack.size() - startingOpCount >= 1;
    }

    private void performOperation() {
        double b = numbersStack.pop();
        double a = numbersStack.pop();
        numbersStack.push(operationsStack.pop().apply(a, b));
    }
}
