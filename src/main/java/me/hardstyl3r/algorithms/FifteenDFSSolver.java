package me.hardstyl3r.algorithms;

import me.hardstyl3r.objects.FifteenGame;
import me.hardstyl3r.enums.Move;
import me.hardstyl3r.objects.GameStat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import static me.hardstyl3r.algorithms.Utils.getGameTarget;
import static me.hardstyl3r.algorithms.Utils.moveTile;
import static me.hardstyl3r.enums.Move.getMove;

public class FifteenDFSSolver {
    private final FifteenGame game;
    private final int[] goalState;
    private final int MAX_DEPTH = 20;
    private GameStat gameStat;

    public FifteenDFSSolver(FifteenGame game) {
        this.game = game;
        this.goalState = getGameTarget(game.getX(), game.getY());
    }

    public String solveDFS(String order) {
        long current = System.currentTimeMillis();
        boolean didFinish = false;
        String currentPath = "";
        int maxDepth = 0;

        Stack<int[]> stack = new Stack<>();
        Map<String, String> visited = new HashMap<>();
        Map<String, String> paths = new HashMap<>();
        stack.push(game.getBoard());
        visited.put(Arrays.toString(game.getBoard()), "");
        paths.put(Arrays.toString(game.getBoard()), "");

        while (!stack.isEmpty()) {
            int[] currentBoard = stack.pop();
            currentPath = paths.get(Arrays.toString(currentBoard));
            if (currentPath.length() > maxDepth) {
                maxDepth = currentPath.length();
            }
            if (Arrays.equals(currentBoard, goalState)) {
                didFinish = true;
                break;
            }
            if (currentPath.length() >= MAX_DEPTH) {
                continue;
            }

            for (char dir : order.toCharArray()) {
                Move move = getMove(dir);
                int[] newBoard = moveTile(game, currentBoard, move);
                String boardKey = Arrays.toString(newBoard);

                if (newBoard != null && !visited.containsKey(boardKey)) {
                    stack.push(newBoard);
                    visited.put(boardKey, currentPath + dir);
                    paths.put(boardKey, currentPath + dir);
                }
            }
        }

        this.gameStat = new GameStat(-1, visited.size(), paths.size(), maxDepth,
                System.currentTimeMillis() - current);
        if (didFinish) {
            this.gameStat.setSolutionLength(currentPath.length());
            return currentPath;
        }
        return "DNF";
    }

    public GameStat returnLatestStat() {
        return this.gameStat;
    }
}