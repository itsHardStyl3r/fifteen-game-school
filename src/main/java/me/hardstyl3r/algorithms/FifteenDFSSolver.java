package me.hardstyl3r.algorithms;

import me.hardstyl3r.objects.FifteenGame;
import me.hardstyl3r.enums.Move;

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

    public FifteenDFSSolver(FifteenGame game) {
        this.game = game;
        this.goalState = getGameTarget(game.getX(), game.getY());
    }

    public String solveDFS(String order) {
        Stack<int[]> stack = new Stack<>();
        Map<String, String> visited = new HashMap<>();
        Map<String, String> paths = new HashMap<>();

        stack.push(game.getBoard());
        visited.put(Arrays.toString(game.getBoard()), "");
        paths.put(Arrays.toString(game.getBoard()), "");

        while (!stack.isEmpty()) {
            int[] currentBoard = stack.pop();
            String currentPath = paths.get(Arrays.toString(currentBoard));
            if (Arrays.equals(currentBoard, goalState)) {
                return currentPath;
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
        return "DNF";
    }
}