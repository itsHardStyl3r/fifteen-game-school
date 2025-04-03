package me.hardstyl3r.algorithms;

import me.hardstyl3r.objects.FifteenGame;
import me.hardstyl3r.enums.Move;

import java.util.*;

import static me.hardstyl3r.algorithms.Utils.getGameTarget;
import static me.hardstyl3r.algorithms.Utils.moveTile;

public class FifteenAStarSolver {
    private final FifteenGame game;
    private final int[] goalState;

    public FifteenAStarSolver(FifteenGame game) {
        this.game = game;
        this.goalState = getGameTarget(game.getX(), game.getY());
    }

    public String solveAStar(boolean useManhattan) {
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(board -> heuristic(board, useManhattan)));
        Set<String> visited = new HashSet<>();
        Map<String, String> paths = new HashMap<>();
        pq.add(game.getBoard());
        paths.put(Arrays.toString(game.getBoard()), "");

        while (!pq.isEmpty()) {
            int[] currentBoard = pq.poll();
            String currentPath = paths.get(Arrays.toString(currentBoard));
            if (Arrays.equals(currentBoard, goalState)) return currentPath;
            visited.add(Arrays.toString(currentBoard));

            for (Move move : Move.values()) {
                int[] newBoard = moveTile(game, currentBoard, move);
                if (newBoard != null && !visited.contains(Arrays.toString(newBoard))) {
                    pq.add(newBoard);
                    paths.put(Arrays.toString(newBoard), currentPath + move.name().charAt(0));
                }
            }
        }
        return "DNF";
    }

    private int heuristic(int[] board, boolean useManhattan) {
        int cost = 0;
        for (int i = 0; i < board.length; i++) {
            if (board[i] == 0) continue;
            int targetX = (board[i] - 1) / game.getY();
            int targetY = (board[i] - 1) % game.getY();
            int x = i / game.getY();
            int y = i % game.getY();

            cost += useManhattan ? Math.abs(targetX - x) + Math.abs(targetY - y) : (board[i] != i + 1 ? 1 : 0);
        }
        return cost;
    }
}
