package me.hardstyl3r.algorithms;

import me.hardstyl3r.objects.FifteenGame;
import me.hardstyl3r.enums.Move;

import java.util.*;

import static me.hardstyl3r.algorithms.Utils.getGameTarget;
import static me.hardstyl3r.algorithms.Utils.moveTile;
import static me.hardstyl3r.enums.Move.getMove;

public class FifteenDFSSolver {
    private static final int MAX_DEPTH = 20;
    private final FifteenGame game;
    private final int[] goalState;

    public FifteenDFSSolver(FifteenGame game) {
        this.game = game;
        this.goalState = getGameTarget(game.getX(), game.getY());
    }

    public String solveDFS(String order) {
        Set<String> visited = new HashSet<>();
        return dfs(game.getBoard(), "", visited, order, 0);
    }

    private String dfs(int[] currentBoard, String path, Set<String> visited, String order, int depth) {
        if (Arrays.equals(currentBoard, goalState)) return path;
        if (depth >= MAX_DEPTH) return "DEPTH";
        String currentState = Arrays.toString(currentBoard);
        if (visited.contains(currentState)) return null;
        visited.add(currentState);

        for (char dir : order.toCharArray()) {
            Move move = getMove(dir);
            int[] newBoard = moveTile(game, currentBoard, move);
            if (newBoard != null) {
                String result = dfs(newBoard, path + dir, visited, order, depth + 1);
                if (result != null) return result;
            }
        }
        return null;
    }
}
