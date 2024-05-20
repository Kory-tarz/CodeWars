package com.cyryl.kyu1.threepass;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Compiler {

    private static final String ARG_END_BRACKET = "]";
    private static final Set<String> OPERATORS = Set.of("*", "/", "+", "-");

    private Map<String, Integer> argMap;

    public List<String> compile(String prog) {
        return pass3(pass2(pass1(prog)));
    }

    /**
     * Returns an un-optimized AST
     */
    public Ast pass1(String prog) {
        Deque<String> tokens = tokenize(prog);
        return null;
    }

    private List<String> readArgList(Deque<String> tokens) {
        tokens.pop(); // remove '['
        String currElement = tokens.pop();
        List<String> args = new ArrayList<>();
        while (!currElement.equals(ARG_END_BRACKET)) {
            args.add(currElement);
        }
        return args;
    }

    private Ast createPass1Ast(Deque<String> tokens) {
        Deque<BinOp> operations = new LinkedList<>();
        Deque<BinOp> priorityOperations = new LinkedList<>();
        BinOp root = null;

        while (!tokens.isEmpty() && !tokens.peek().equals(")")) {
            String currToken = tokens.pop();
            BinOp lastBinOp = operations.peekLast();
            if (isArgument(currToken)) {
                if (lastBinOp != null && !lastBinOp.isFull()) {
                    lastBinOp.setRightArg(createUnOp(currToken));
                } else {
                    operations.push(new BinOp(createUnOp(currToken)));
                }
            } else if (isOperator(currToken)) {

            }

        }
        return root;
    }

    private UnOp createUnOp(String token) {
        return null;
    }

    private boolean isArgument(String token) {
        return token.matches("\\d+") || argMap.containsKey(token);
    }

    private boolean isPriorityOperator(String token) {
        return token.equals("*") || token.equals("/");
    }

    private boolean isOperator(String token) {
        return OPERATORS.contains(token);
    }

    private Optional<Integer> isNumber(String token) {
        try {
            return Optional.of(Integer.parseInt(token));
        } catch (NumberFormatException exception) {
            return Optional.empty();
        }
    }

    /**
     * Returns an AST with constant expressions reduced
     */
    public Ast pass2(Ast ast) {
        return null;
    }

    /**
     * Returns assembly instructions
     */
    public List<String> pass3(Ast ast) {
        return null;
    }

    private static Deque<String> tokenize(String prog) {
        Deque<String> tokens = new LinkedList<>();
        Pattern pattern = Pattern.compile("[-+*/()\\[\\]]|[a-zA-Z]+|\\d+");
        Matcher m = pattern.matcher(prog);
        while (m.find()) {
            tokens.add(m.group());
        }
        tokens.add("$"); // end-of-stream
        return tokens;
    }
}
