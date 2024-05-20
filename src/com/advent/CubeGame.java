package com.advent;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CubeGame {
    private static final String games = "Game 1: 7 blue, 9 red, 1 green; 8 green; 10 green, 5 blue, 3 red; 11 blue, 5 red, 1 green\n" +
            "Game 2: 7 green, 3 blue; 20 blue, 4 green; 6 red, 13 blue, 2 green\n" +
            "Game 3: 11 blue, 3 red, 1 green; 15 red, 9 blue, 3 green; 11 blue, 4 red, 4 green; 1 red, 2 green, 14 blue; 18 blue, 4 green, 10 red\n" +
            "Game 4: 3 red, 7 blue; 3 blue, 2 red, 2 green; 2 green, 1 red, 1 blue; 3 green, 5 blue, 5 red; 7 blue, 1 green, 1 red; 2 green, 7 blue\n" +
            "Game 5: 1 blue, 2 red, 1 green; 6 blue, 3 green, 2 red; 2 blue\n" +
            "Game 6: 5 green, 5 red, 5 blue; 9 blue, 6 green, 8 red; 7 green, 3 red, 15 blue; 9 blue, 9 green; 10 red, 12 blue, 4 green; 9 blue, 1 red, 9 green\n" +
            "Game 7: 8 blue, 9 green, 16 red; 9 green, 9 blue; 10 red, 5 blue, 8 green; 9 green, 17 red, 2 blue; 1 blue, 18 red, 8 green; 3 green, 8 blue, 14 red\n" +
            "Game 8: 6 green, 8 blue, 16 red; 10 green, 1 blue, 4 red; 2 blue, 15 red, 10 green; 7 green, 9 red, 2 blue; 17 red, 4 green, 7 blue\n" +
            "Game 9: 5 blue, 1 green, 4 red; 2 green, 6 red, 12 blue; 2 green, 7 blue, 1 red; 12 blue, 2 green, 1 red\n" +
            "Game 10: 1 red, 16 blue, 18 green; 14 green, 13 blue; 4 green, 7 blue; 5 red, 16 blue, 11 green; 14 green, 2 red, 5 blue; 10 blue, 3 red, 6 green\n" +
            "Game 11: 4 green, 2 blue, 17 red; 1 green, 2 red, 1 blue; 5 blue, 14 red\n" +
            "Game 12: 7 red, 7 green; 7 blue, 7 green, 8 red; 14 red, 7 blue, 5 green\n" +
            "Game 13: 1 red, 7 green; 7 green, 5 blue; 4 blue, 1 red; 14 green, 5 blue, 2 red; 3 red, 5 green; 10 green, 2 blue, 3 red\n" +
            "Game 14: 4 blue, 7 red; 10 red, 6 blue; 1 green, 3 red; 1 green, 12 blue, 8 red; 1 red, 1 green, 6 blue; 1 green, 6 red\n" +
            "Game 15: 4 green, 6 blue, 12 red; 7 blue, 6 red, 4 green; 1 green; 16 blue, 5 red, 5 green; 11 blue, 7 red, 2 green\n" +
            "Game 16: 6 blue, 2 red, 4 green; 7 green, 2 blue; 2 red, 4 green; 3 green, 4 blue, 1 red\n" +
            "Game 17: 9 green, 9 red, 3 blue; 9 green, 4 blue, 5 red; 10 green, 2 blue, 3 red\n" +
            "Game 18: 2 red, 1 green; 3 red, 9 blue, 1 green; 4 red, 10 blue; 6 blue, 2 green, 5 red; 12 blue, 5 red, 2 green\n" +
            "Game 19: 2 blue, 15 green, 9 red; 9 blue, 15 red, 4 green; 9 green, 4 blue, 4 red; 2 blue, 12 green, 16 red\n" +
            "Game 20: 5 blue, 2 green, 9 red; 10 blue, 2 green, 6 red; 1 red, 13 green, 6 blue; 15 green, 13 blue, 12 red; 13 blue, 5 green\n" +
            "Game 21: 3 red, 1 green, 10 blue; 8 green, 10 blue, 5 red; 12 blue, 5 red, 2 green\n" +
            "Game 22: 4 blue, 6 red, 2 green; 5 blue, 16 red; 13 red; 19 red, 1 green, 6 blue; 11 red, 2 green, 5 blue\n" +
            "Game 23: 5 red, 4 green, 2 blue; 7 blue, 3 green; 5 blue, 4 red, 1 green; 2 blue, 3 red, 2 green\n" +
            "Game 24: 1 green; 4 red, 6 green, 2 blue; 6 green, 4 red; 8 blue, 3 red, 2 green; 1 blue, 2 red\n" +
            "Game 25: 4 green, 2 blue, 12 red; 10 blue, 1 red, 2 green; 3 green, 12 blue; 7 green, 12 red, 3 blue; 2 green, 6 blue, 13 red; 3 green, 14 red, 9 blue\n" +
            "Game 26: 8 red, 8 blue, 4 green; 5 red, 14 blue, 11 green; 3 green, 4 blue; 3 red, 10 green, 8 blue\n" +
            "Game 27: 14 red, 5 blue, 6 green; 1 red, 1 green; 3 red, 1 blue, 4 green\n" +
            "Game 28: 11 green, 9 blue; 3 green, 6 blue, 7 red; 9 blue, 5 red, 10 green; 8 red, 5 blue, 10 green; 10 green, 9 red, 2 blue\n" +
            "Game 29: 12 red, 1 green, 7 blue; 4 red, 4 blue; 12 red, 1 blue\n" +
            "Game 30: 3 red, 9 blue; 7 blue, 3 green, 2 red; 1 green, 3 blue, 8 red; 15 blue, 8 red, 1 green; 1 red, 2 green, 6 blue; 2 blue, 3 green, 15 red\n" +
            "Game 31: 10 red, 14 green, 9 blue; 6 blue, 7 red; 16 red, 3 blue, 5 green; 11 red, 7 blue, 1 green; 10 green, 8 red, 3 blue; 14 green, 6 red, 8 blue\n" +
            "Game 32: 1 red, 7 blue, 4 green; 5 green, 6 blue; 4 blue, 2 green; 2 blue, 3 green\n" +
            "Game 33: 2 red, 4 green; 1 green, 12 blue; 1 red, 4 green, 13 blue; 3 red, 11 blue; 8 blue, 3 red, 4 green; 4 green, 2 blue, 3 red\n" +
            "Game 34: 9 green, 3 red, 10 blue; 2 red, 5 green, 7 blue; 8 green, 3 red\n" +
            "Game 35: 3 blue, 1 red; 1 red, 1 green, 3 blue; 13 red, 1 blue; 3 blue, 3 green, 14 red; 1 blue; 3 blue, 2 green, 3 red\n" +
            "Game 36: 5 red, 10 blue; 10 green, 4 red, 8 blue; 6 blue, 9 green, 9 red\n" +
            "Game 37: 1 red, 3 green, 1 blue; 7 blue, 4 red; 11 red, 6 blue, 2 green; 1 green, 10 red, 3 blue; 2 blue, 1 green, 10 red; 10 red, 4 blue\n" +
            "Game 38: 13 red, 6 blue, 1 green; 8 red, 4 green, 8 blue; 13 green, 7 red, 3 blue; 6 red, 12 green, 2 blue; 7 blue, 15 green, 5 red; 13 green, 2 blue, 11 red\n" +
            "Game 39: 1 blue, 5 green, 6 red; 1 green, 8 red, 4 blue; 8 red, 10 green, 6 blue; 2 blue, 1 red, 4 green; 3 blue, 2 red, 7 green; 8 red, 6 green, 2 blue\n" +
            "Game 40: 6 blue, 20 green, 12 red; 7 blue, 10 red, 7 green; 5 red, 2 green, 8 blue; 2 blue, 1 red, 7 green; 11 green, 3 red; 9 red, 9 blue, 6 green\n" +
            "Game 41: 15 red, 5 green, 7 blue; 4 red, 7 blue; 12 green, 7 blue; 12 red, 15 green, 8 blue\n" +
            "Game 42: 2 green, 12 blue, 4 red; 2 blue, 2 red, 8 green; 10 blue, 2 red, 11 green; 1 green, 1 red, 5 blue\n" +
            "Game 43: 14 blue, 2 green, 11 red; 10 red, 8 blue; 15 blue; 1 green, 16 blue, 6 red; 3 red, 17 blue; 3 blue, 1 green\n" +
            "Game 44: 3 blue, 4 green, 9 red; 7 green, 15 red, 2 blue; 8 green, 8 red; 3 green, 10 blue, 6 red\n" +
            "Game 45: 2 green, 14 red; 1 blue, 16 red, 5 green; 3 green, 5 red; 1 blue, 5 green, 2 red\n" +
            "Game 46: 2 red, 13 blue, 6 green; 8 green, 1 blue; 8 blue, 6 green, 2 red; 6 green, 3 blue; 2 green, 7 blue\n" +
            "Game 47: 1 green, 11 blue, 6 red; 3 green, 4 blue, 4 red; 6 red, 13 blue; 6 blue, 5 green, 6 red\n" +
            "Game 48: 1 red, 1 green; 6 red, 3 blue, 2 green; 3 green, 6 red\n" +
            "Game 49: 10 blue, 15 green, 5 red; 5 green, 10 red; 4 green, 12 red, 5 blue; 7 red, 9 green, 7 blue; 17 green, 3 blue, 4 red\n" +
            "Game 50: 7 red, 8 green; 11 red, 1 green, 2 blue; 12 red, 4 green; 15 red, 2 green; 5 red, 2 blue, 6 green; 1 green, 3 red\n" +
            "Game 51: 7 red, 4 blue, 1 green; 10 red, 7 blue; 11 blue, 8 red\n" +
            "Game 52: 3 green, 2 blue, 1 red; 1 red, 1 blue, 2 green; 3 green, 12 blue; 9 blue, 3 red; 6 blue, 2 red, 2 green; 1 green, 1 red, 14 blue\n" +
            "Game 53: 7 red, 1 green, 4 blue; 5 blue, 5 red; 7 red, 2 blue\n" +
            "Game 54: 3 red, 8 green, 12 blue; 15 red, 4 green, 16 blue; 1 blue, 4 green, 5 red; 5 green, 8 red, 10 blue; 14 red, 7 blue\n" +
            "Game 55: 8 green, 18 blue, 2 red; 4 red, 15 green, 19 blue; 10 blue, 8 red, 1 green\n" +
            "Game 56: 13 blue, 2 red, 5 green; 1 blue, 13 green, 5 red; 3 red, 1 blue, 10 green; 5 red, 14 blue, 1 green; 11 green, 6 blue, 6 red; 11 green, 7 blue, 8 red\n" +
            "Game 57: 1 green; 1 blue; 1 blue, 6 red, 1 green; 1 green, 3 red; 1 green, 6 red\n" +
            "Game 58: 14 blue, 7 red; 4 green, 10 red; 5 blue, 7 green, 6 red; 3 green, 6 red, 19 blue\n" +
            "Game 59: 3 green, 5 red, 3 blue; 1 blue, 5 green, 3 red; 3 blue, 7 red, 4 green\n" +
            "Game 60: 6 blue; 11 blue, 2 red, 6 green; 1 red, 3 blue; 2 green, 1 blue, 2 red\n" +
            "Game 61: 5 red, 6 green, 8 blue; 8 blue, 5 green, 7 red; 6 green, 3 red, 7 blue; 8 green, 7 blue\n" +
            "Game 62: 9 green; 4 red, 5 green; 3 green, 14 blue; 4 green, 3 red, 6 blue\n" +
            "Game 63: 6 green, 12 blue; 1 red, 12 blue; 1 green, 13 blue; 3 blue, 8 green; 7 blue, 2 green\n" +
            "Game 64: 2 green, 11 red, 1 blue; 2 red; 3 green; 2 green, 6 red; 1 blue, 6 red\n" +
            "Game 65: 9 green, 1 blue; 5 green, 14 red, 1 blue; 11 green, 6 blue, 2 red; 8 red, 1 green; 9 green, 11 red, 5 blue; 18 green, 11 red, 1 blue\n" +
            "Game 66: 5 green, 17 red; 1 blue, 4 green, 2 red; 3 green, 2 blue, 13 red; 4 red, 1 green; 2 green, 18 red; 18 red, 1 green, 2 blue\n" +
            "Game 67: 7 green; 2 blue, 1 green; 1 blue, 6 green, 1 red; 3 green, 3 blue\n" +
            "Game 68: 7 blue, 18 red, 16 green; 7 blue, 6 red, 3 green; 5 blue, 4 red; 12 red, 20 green, 7 blue; 5 green, 4 blue\n" +
            "Game 69: 5 red, 19 green, 2 blue; 12 green, 7 red; 7 red, 10 green; 2 blue, 1 red, 1 green\n" +
            "Game 70: 8 red, 2 green, 14 blue; 1 green, 12 red, 3 blue; 2 green, 1 blue, 18 red; 10 red, 15 blue, 1 green; 2 green, 1 red, 14 blue; 1 green, 12 blue, 8 red\n" +
            "Game 71: 11 green, 9 red; 17 red, 1 blue, 9 green; 14 green, 1 red, 1 blue; 6 green, 11 red; 3 red, 14 green; 1 blue, 12 green\n" +
            "Game 72: 4 red, 3 blue, 16 green; 12 green, 5 red, 4 blue; 7 red, 4 blue, 12 green\n" +
            "Game 73: 1 blue; 1 green, 10 blue, 1 red; 1 blue, 1 red, 1 green; 11 blue, 1 green, 1 red; 10 blue\n" +
            "Game 74: 12 red, 3 green; 11 red, 1 blue; 19 red, 1 blue; 3 green, 1 blue; 5 red, 1 blue; 5 red, 1 blue, 2 green\n" +
            "Game 75: 9 blue, 4 green; 1 green, 1 blue, 5 red; 6 blue, 8 red, 3 green; 10 blue, 2 green, 6 red; 3 green, 3 red, 3 blue; 4 green, 7 red, 8 blue\n" +
            "Game 76: 1 green, 13 blue, 2 red; 1 green, 15 blue; 8 red, 1 green, 10 blue; 3 blue, 6 red\n" +
            "Game 77: 9 red, 2 green, 11 blue; 6 red, 5 blue, 2 green; 6 blue, 9 red, 1 green; 4 blue, 5 red; 13 blue\n" +
            "Game 78: 13 blue, 4 red, 2 green; 7 red, 2 green, 8 blue; 6 red, 20 blue, 4 green; 7 red, 3 green, 14 blue; 15 blue, 2 red, 3 green\n" +
            "Game 79: 2 red, 10 blue, 6 green; 4 blue, 3 red, 3 green; 3 red, 5 blue, 3 green; 1 blue, 4 green, 1 red; 1 red, 3 green, 1 blue; 1 blue, 6 green\n" +
            "Game 80: 2 green, 1 blue, 3 red; 2 green, 2 red; 1 green, 4 red, 1 blue; 4 red, 3 green\n" +
            "Game 81: 1 red, 3 green; 11 green; 4 green, 6 red; 1 red, 1 blue, 13 green; 11 green, 1 blue\n" +
            "Game 82: 15 green, 3 red, 9 blue; 3 blue, 7 green, 3 red; 3 blue, 11 green; 9 blue, 3 red, 9 green; 5 blue, 1 green, 1 red; 4 blue, 9 green, 1 red\n" +
            "Game 83: 5 red, 1 blue; 17 red, 1 green, 6 blue; 3 blue, 11 red; 7 blue, 4 red; 1 blue, 12 red, 1 green; 1 red, 1 green, 1 blue\n" +
            "Game 84: 6 red, 7 green, 3 blue; 2 blue, 7 red, 15 green; 1 blue, 5 red, 3 green; 10 red, 1 blue, 4 green; 4 green, 2 blue, 4 red; 9 red, 11 green\n" +
            "Game 85: 1 green, 10 red; 10 red, 2 blue, 3 green; 2 blue, 6 red; 1 blue, 16 red; 8 red, 2 green; 13 red, 4 green\n" +
            "Game 86: 3 blue, 2 red, 9 green; 2 green, 6 red, 8 blue; 2 red, 7 blue, 8 green\n" +
            "Game 87: 14 red, 1 green, 2 blue; 9 blue, 11 green, 7 red; 13 green, 5 blue, 1 red; 12 red, 7 blue, 3 green; 6 red, 8 blue, 3 green\n" +
            "Game 88: 7 blue, 2 green, 14 red; 7 red, 4 green, 16 blue; 6 green, 6 blue, 2 red; 1 red, 7 green, 2 blue\n" +
            "Game 89: 3 red, 5 blue, 3 green; 4 blue, 2 green, 14 red; 17 red, 1 blue\n" +
            "Game 90: 9 red, 1 blue; 7 red; 12 red, 1 green, 1 blue\n" +
            "Game 91: 12 green, 16 blue, 5 red; 18 green, 11 blue, 3 red; 5 green, 6 blue, 2 red; 13 blue, 10 green; 3 red, 2 blue\n" +
            "Game 92: 7 red, 10 green, 13 blue; 4 green, 9 blue, 1 red; 3 green, 9 red, 13 blue\n" +
            "Game 93: 2 blue, 2 red, 6 green; 3 red, 14 green; 13 green, 3 red, 3 blue; 3 red, 8 green; 13 green; 13 green, 1 red, 2 blue\n" +
            "Game 94: 12 red, 5 green, 2 blue; 5 blue, 12 red; 5 blue, 2 red, 9 green; 10 red, 8 green, 8 blue; 7 red, 8 green; 3 blue, 6 green, 5 red\n" +
            "Game 95: 7 green, 5 red, 3 blue; 14 green, 3 red, 5 blue; 6 green, 1 blue; 10 green, 2 red, 5 blue; 4 blue, 14 green, 4 red\n" +
            "Game 96: 2 green, 2 blue, 2 red; 5 blue, 2 red; 2 blue, 1 green; 1 green, 1 red\n" +
            "Game 97: 5 green, 6 red; 6 red, 5 green; 4 red, 4 blue; 1 blue, 4 green, 3 red; 1 green, 8 red; 2 red, 9 green, 5 blue\n" +
            "Game 98: 1 red, 3 blue; 3 green; 1 red, 4 green; 1 red, 4 blue, 3 green; 2 blue, 4 green, 1 red\n" +
            "Game 99: 8 blue, 3 green; 1 green, 3 red; 2 green, 5 blue, 7 red; 5 red, 9 blue, 1 green; 3 green, 6 red, 7 blue; 3 green, 6 blue, 9 red\n" +
            "Game 100: 13 green, 9 blue, 4 red; 2 green, 2 red, 15 blue; 1 red, 1 green; 9 green, 1 red\n";

    private static final Map<String, Integer> limits = Map.of("red", 12, "green", 13, "blue", 14);

    public static void main(String[] args) {
        part2();
    }

    private static void part2() {
        String[] lines = games.split("\\n");
        long sum = 0;
        for (String line : lines) {
            String[] game = line.split(":");
            String[] rounds = game[1].split(";");
            sum += calculatePower(rounds);
        }
        System.out.println(sum);
    }

    private static long calculatePower(String[] rounds) {
        Map<String, Integer> powers = new HashMap<>();
        powers.put("red" , 0);
        powers.put("green" , 0);
        powers.put("blue" , 0);
        for (String round : rounds) {
            String[] pulls = round.split(",");
            Map<String, Integer> roundMin = new HashMap<>();
            for (String pull : pulls) {
                String[] cubeData = pull.trim().split("\\s");
                roundMin.put(cubeData[1], roundMin.getOrDefault(cubeData[1], 0) + Integer.parseInt(cubeData[0]));
            }
            roundMin.forEach((key, value) -> powers.put(key, Math.max(powers.get(key), value)));
        }
        return powers.values().stream().mapToLong(value -> (long) value).reduce((a, b) -> a * b).orElse(0);
    }

    private static void part1() {
        String[] lines = games.split("\\n");
        int sum = 0;
        for (String line : lines) {
            String[] game = line.split(":");
            String[] rounds = game[1].split(";");
            if (Arrays.stream(rounds).allMatch(CubeGame::isRoundPossible)) {
                int gameId = Integer.parseInt(game[0].replaceAll("Game ", ""));
                sum += gameId;
            }
        }
        System.out.println(sum);
    }

    private static boolean isRoundPossible(String round) {
        String[] pulls = round.split(",");
        Map<String, Integer> result = new HashMap<>();
        for (String pull : pulls) {
            String[] cubeData = pull.trim().split("\\s");
            result.put(cubeData[1], result.getOrDefault(cubeData[1], 0) + Integer.parseInt(cubeData[0]));
        }
        return result.entrySet().stream().allMatch(entry -> entry.getValue() <= limits.get(entry.getKey()));
    }
}
