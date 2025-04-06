package me.hardstyl3r.algorithms;

import me.hardstyl3r.objects.FifteenGame;
import me.hardstyl3r.enums.Move;
import me.hardstyl3r.objects.GameStat;

import java.util.*;

import static me.hardstyl3r.algorithms.Utils.getGameTarget;
import static me.hardstyl3r.algorithms.Utils.moveTile;
import static me.hardstyl3r.enums.Move.getMove;

public class FifteenBFSSolver {
    private final FifteenGame game;
    private final int[] goalState;
    private GameStat gameStat;

    public FifteenBFSSolver(FifteenGame game) {
        this.game = game;
        this.goalState = getGameTarget(game.getX(), game.getY());
    }

    public String solveBFS(String order) {
        long current = System.currentTimeMillis();
        boolean didFinish = false;
        String currentPath = "";
        int i = 0;

        Queue<int[]> queue = new LinkedList<>();
        Map<String, String> paths = new HashMap<>();
        queue.add(game.getBoard());
        paths.put(Arrays.toString(game.getBoard()), "");

        while (!queue.isEmpty()) {
            int[] currentBoard = queue.poll();
            currentPath = paths.get(Arrays.toString(currentBoard));
            if (Arrays.equals(currentBoard, goalState)) {
                didFinish = true;
                break;
            }
            i++;

            for (char dir : order.toCharArray()) {
                Move move = getMove(dir);
                int[] newBoard = moveTile(game, currentBoard, move);
                if (newBoard != null && !paths.containsKey(Arrays.toString(newBoard))) {
                    queue.add(newBoard);
                    paths.put(Arrays.toString(newBoard), currentPath + dir);
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

    public GameStat returnLatestStat() {
        return this.gameStat;
    }
}
