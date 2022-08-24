package com.cyryl.kyu4;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SimpleAssembler {

    private static Map<String, Integer> state;

    public static Map<String, Integer> interpret(String[] program){

        state = new HashMap<>();

        Arrays.stream(program).forEach(System.out::println);

        int i=0;

        while (i< program.length){
            i = readInstruction(program[i], i);
        }

        return state;
    }

    public static int readInstruction(String instruction, int i){
        if(instruction.startsWith("mov"))
            performMov(instruction);
        else if(instruction.startsWith("inc"))
            performInc(instruction);
        else if(instruction.startsWith("dec"))
            performDec(instruction);
        else if(instruction.startsWith("jnz"))
            return (i + performJnz(instruction));
        return i+1;
    }

    private static void performMov(String instruction){
        String[] cmds = instruction.split(" ");

        int value;
        String sValue = cmds[2];

        if(state.containsKey(sValue))
            value = state.get(sValue);
        else
            value = Integer.parseInt(sValue);

        state.put(cmds[1], value);
    }

    private static void performInc(String instruction){
        String[] cmds = instruction.split(" ");

        state.put(cmds[1], state.get(cmds[1])+1);
    }

    private static void performDec(String instruction){
        String[] cmds = instruction.split(" ");

        state.put(cmds[1], state.get(cmds[1])-1);
    }

    private static int performJnz(String instruction){
        String[] cmds = instruction.split(" ");
        int value;

        if (state.containsKey(cmds[1]))
            value = state.get(cmds[1]);
        else
            value = Integer.parseInt(cmds[1]);

        if(value != 0)
            return Integer.parseInt(cmds[2]);
        else
            return 1;
    }
}
