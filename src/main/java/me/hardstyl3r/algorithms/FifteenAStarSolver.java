package me.hardstyl3r.algorithms;

import me.hardstyl3r.objects.FifteenGame;
import me.hardstyl3r.enums.Move;
import me.hardstyl3r.objects.GameStat;

import java.util.*;

import static me.hardstyl3r.algorithms.Utils.getGameTarget;
import static me.hardstyl3r.algorithms.Utils.moveTile;

public class FifteenAStarSolver {
    private final FifteenGame game;
    private final int[] goalState;
    private GameStat gameStat;

    public FifteenAStarSolver(FifteenGame game) {
        this.game = game;
        this.goalState = getGameTarget(game.getX(), game.getY());
    }

    public String solveAStar(boolean useManhattan) {
        long current = System.currentTimeMillis();
        boolean didFinish = false;
        String currentPath = "";

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(board -> heuristic(board, useManhattan)));
        Set<String> visited = new HashSet<>();
        Map<String, String> paths = new HashMap<>();
        pq.add(game.getBoard());
        paths.put(Arrays.toString(game.getBoard()), "");

        int i = 0;
        while (!pq.isEmpty()) {
            int[] currentBoard = pq.poll();
            currentPath = paths.get(Arrays.toString(currentBoard));
            if (Arrays.equals(currentBoard, goalState)) {
                didFinish = true;
                break;
            }
            visited.add(Arrays.toString(currentBoard));
            i++;

            for (Move move : Move.values()) {
                int[] newBoard = moveTile(game, currentBoard, move);
                if (newBoard != null && !visited.contains(Arrays.toString(newBoard))) {
                    pq.add(newBoard);
                    paths.put(Arrays.toString(newBoard), currentPath + move.name().charAt(0));
                }
            }
        }

        this.gameStat = new GameStat(-1, paths.size(), i, currentPath.length(),
                System.currentTimeMillis() - current);
        if (didFinish) {
            this.gameStat.setSolutionLength(currentPath.length());
            return currentPath;
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

    public GameStat returnLatestStat() {
        return this.gameStat;
    }
}
