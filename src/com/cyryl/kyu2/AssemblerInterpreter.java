package com.cyryl.kyu2;

import java.util.*;
import java.util.function.BiConsumer;

public class AssemblerInterpreter {

    public static String interpret(final String input) {
        String[] inputs = input.split("\n");
        Assembler assembler = new Assembler();
        assembler.runProgram(inputs);
        return assembler.endMessage();
    }

    private static class Assembler {

        private final Register register;
        private final Map<String, Integer> labels;
        private int currentLine;
        private final Deque<Integer> returnLine;
        private int lastComparisonResult;
        private int status;
        private String returnMessage = "";

        private static final int EQUAL = 0;
        private static final int ENDED = 0;
        private static final int RUNNING = 1;
        private static final int GREATER = 1;
        private static final int LESS = -1;

        public Assembler() {
            register = new Register();
            labels = new HashMap<>();
            returnLine = new ArrayDeque<>();
            lastComparisonResult = EQUAL;
            status = RUNNING;
        }

        public void runProgram(String[] inputs) {
            setupLabels(inputs);
            run(inputs);
        }

        private void setupLabels(String[] inputs) {
            int line = 0;
            for (String inputLine : inputs) {
                if (isLabel(getLineIgnoringComments(inputLine))) {
                    setLabel(inputLine.substring(0, inputLine.indexOf(":")).trim(), line);
                }
                line++;
            }
        }

        private void run(String[] inputs) {
            currentLine = 0;
            while (status == RUNNING && currentLine < inputs.length) {
                processNewCommand(inputs[currentLine].trim());
            }
        }

        private void setLabel(String label, int line) {
            labels.put(label, line);
        }

        private boolean isLabel(String inputLine) {
            return inputLine.matches("^\\s*\\w+:\\s*");
        }

        private void processNewCommand(String command) {
            if (!command.isBlank()) {
                processCommand(command);
            } else {
                currentLine++;
            }
        }

        public String endMessage() {
            return status == ENDED ? returnMessage : null;
        }

        private void processCommand(String command) {
            String cmdName;
            String cmdData;
            int cmdIdx = command.indexOf(" ");
            if (cmdIdx > 0) {
                cmdName = command.substring(0, cmdIdx);
                cmdData = getLineIgnoringComments(command.substring(cmdIdx + 1));
            } else {
                cmdName = command;
                cmdData = "";
            }
            switch (cmdName) {
                case "mov" -> readParametersAndAccept(this::mov, cmdData);
                case "add" -> readParametersAndAccept(this::add, cmdData);
                case "mul" -> readParametersAndAccept(this::mul, cmdData);
                case "sub" -> readParametersAndAccept(this::sub, cmdData);
                case "div" -> readParametersAndAccept(this::div, cmdData);
                case "cmp" -> readParametersAndAccept(this::cmp, cmdData);
                case "inc" -> inc(cmdData);
                case "dec" -> dec(cmdData);
                case "jne" -> jumpNotEqual(cmdData);
                case "je" -> jumpEqual(cmdData);
                case "jge" -> jumpGreaterEqual(cmdData);
                case "jg" -> jumpGreater(cmdData);
                case "jle" -> jumpLessEqual(cmdData);
                case "jl" -> jumpLess(cmdData);
                case "call" -> call(cmdData);
                case "ret" -> ret();
                case "msg" -> message(cmdData);
                case ";" -> ignore();
                case "end" -> end();
                case "jmp" -> jumpLabel(cmdData);
                default -> {
                    if (isLabel(cmdName)) {
                        ignore();
                    } else {
                        throw new IllegalArgumentException("Invalid command name: " + command);
                    }
                }
            }
            currentLine++;
        }

        private String getLineIgnoringComments(String data) {
            int commentBeginIdx = data.indexOf(";");
            if (commentBeginIdx > 0) {
                data = data.substring(0, commentBeginIdx);
            }
            return data.trim();
        }

        private void readParametersAndAccept(BiConsumer<Parameter, Parameter> cmd, String data) {
            Parameter[] parameters = convertCmdToParameters(data);
            Parameter x = parameters[0];
            Parameter y = parameters[1];
            cmd.accept(x, y);
        }

        private Parameter[] convertCmdToParameters(String data) {
            String[] values = data.split(",");
            if (values.length < 2) throw new IllegalArgumentException();
            return new Parameter[]{new Parameter(values[0].trim(), register), new Parameter(values[1].trim(), register)};
        }

        private void end() {
            status = ENDED;
        }

        private void ignore() {
            // ignore this line
        }

        private void message(String message) {
            StringBuilder sb = new StringBuilder(returnMessage);
            List<String> messageData = splitMessage(message);
            for (String data : messageData) {
                String clearData = data.trim();
                if (clearData.startsWith("'")) {
                    sb.append(clearOutsideApostrophes(clearData));
                } else {
                    sb.append(register.get(clearData));
                }
            }
            returnMessage = sb.toString();
        }

        private List<String> splitMessage(String message) {
            boolean inQuotes = false;
            List<String> parts = new ArrayList<>();
            int start = 0;
            for (int curr = 0; curr < message.length(); curr++) {
                if (message.charAt(curr) == '\'') {
                    inQuotes = !inQuotes;
                } else if (message.charAt(curr) == ',' && !inQuotes) {
                    parts.add(message.substring(start, curr));
                    start = curr + 1;
                }
            }
            parts.add(message.substring(start));
            return parts;
        }

        private String clearOutsideApostrophes(String data) {
            return data.substring(1, data.length() - 1);
        }

        private void ret() {
            currentLine = returnLine.removeFirst();
        }

        private void call(String label) {
            returnLine.offerFirst(currentLine);
            jumpLabel(label);
        }

        private void jumpLess(String label) {
            if (lastComparisonResult == LESS) {
                jumpLabel(label);
            }
        }

        private void jumpLessEqual(String label) {
            if (lastComparisonResult != GREATER) {
                jumpLabel(label);
            }
        }

        private void jumpGreater(String label) {
            if (lastComparisonResult == GREATER) {
                jumpLabel(label);
            }
        }

        private void jumpGreaterEqual(String label) {
            if (lastComparisonResult != LESS) {
                jumpLabel(label);
            }
        }

        private void jumpEqual(String label) {
            if (lastComparisonResult == EQUAL) {
                jumpLabel(label);
            }
        }

        private void jumpNotEqual(String label) {
            if (lastComparisonResult != EQUAL) {
                jumpLabel(label);
            }
        }

        private void cmp(Parameter x, Parameter y) {
            lastComparisonResult = Integer.compare(x.getValue(), y.getValue());
        }

        private void jumpLabel(String label) {
            currentLine = labels.get(label);
        }

        private void inc(String varName) {
            register.increase(varName);
        }

        private void dec(String varName) {
            register.decrease(varName);
        }

        private void mov(Parameter x, Parameter y) {
            x.setValue(y.getValue());
        }

        private void add(Parameter x, Parameter y) {
            x.setValue(x.getValue() + y.getValue());
        }

        private void sub(Parameter x, Parameter y) {
            x.setValue(x.getValue() - y.getValue());
        }

        private void div(Parameter x, Parameter y) {
            x.setValue(x.getValue() / y.getValue());
        }

        private void mul(Parameter x, Parameter y) {
            x.setValue(x.getValue() * y.getValue());
        }


    }

    private static class Register {
        private final Map<String, Integer> register;

        private Register() {
            register = new HashMap<>();
        }

        public int get(String key) {
            return register.get(key);
        }

        public void set(String key, Integer val) {
            register.put(key, val);
        }

        public void increase(String key) {
            int value = register.get(key);
            register.put(key, value + 1);
        }

        public void decrease(String key) {
            int value = register.get(key);
            register.put(key, value - 1);
        }

    }

    private static class Parameter {
        private final String name;
        private Integer value;
        private boolean isValue;
        private final Register register;

        public Parameter(String name, Register register) {
            this.register = register;
            this.name = name;
            try {
                value = Integer.parseInt(name);
                isValue = true;
            } catch (NumberFormatException e) {
                isValue = false;
            }
        }

        public int getValue() {
            if (isValue) {
                return value;
            } else {
                return register.get(name);
            }
        }

        public void setValue(Integer newValue) {
            if (isValue) {
                value = newValue;
            } else {
                register.set(name, newValue);
            }
        }
    }
}
