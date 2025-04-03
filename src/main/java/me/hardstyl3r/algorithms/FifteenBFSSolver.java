package me.hardstyl3r.algorithms;

import me.hardstyl3r.objects.FifteenGame;
import me.hardstyl3r.enums.Move;

import java.util.*;

import static me.hardstyl3r.algorithms.Utils.getGameTarget;
import static me.hardstyl3r.algorithms.Utils.moveTile;
import static me.hardstyl3r.enums.Move.getMove;

public class FifteenBFSSolver {
    private final FifteenGame game;
    private final int[] goalState;

    public FifteenBFSSolver(FifteenGame game) {
        this.game = game;
        this.goalState = getGameTarget(game.getX(), game.getY());
    }

    public String solveBFS(String order) {
        Queue<int[]> queue = new LinkedList<>();
        Map<String, String> visited = new HashMap<>();
        Map<String, String> paths = new HashMap<>();
        queue.add(game.getBoard());
        visited.put(Arrays.toString(game.getBoard()), "");
        paths.put(Arrays.toString(game.getBoard()), "");

        while (!queue.isEmpty()) {
            int[] currentBoard = queue.poll();
            String currentPath = paths.get(Arrays.toString(currentBoard));
            if (Arrays.equals(currentBoard, goalState)) return currentPath;

            for (char dir : order.toCharArray()) {
                Move move = getMove(dir);
                int[] newBoard = moveTile(game, currentBoard, move);
                if (newBoard != null && !visited.containsKey(Arrays.toString(newBoard))) {
                    queue.add(newBoard);
                    visited.put(Arrays.toString(newBoard), currentPath + dir);
                    paths.put(Arrays.toString(newBoard), currentPath + dir);
                }
            }
        }
        return "DNF";
    }
}
