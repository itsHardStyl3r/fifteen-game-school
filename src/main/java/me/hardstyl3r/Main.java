package me.hardstyl3r;

import me.hardstyl3r.algorithms.FifteenAStarSolver;
import me.hardstyl3r.algorithms.FifteenBFSSolver;
import me.hardstyl3r.managers.FifteenManager;
import me.hardstyl3r.objects.FifteenGame;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        FifteenManager manager = new FifteenManager();
        FifteenGame game = manager.loadFromFile(new File("games/4x4_03_00003.txt"));
        if (game == null) {
            System.out.println("Failed to load the game.");
            return;
        }
        System.out.println(manager.displayGame(game));
        FifteenBFSSolver bfs = new FifteenBFSSolver(game);
        String[] orders = {"RDUL", "RDLU", "DRUL", "DRLU", "LUDR", "LURD", "ULDR", "ULRD"};
        for (String s : orders) {
            System.out.println("BFS " + s + ": " + bfs.solveBFS(s));
        }
        FifteenAStarSolver astar = new FifteenAStarSolver(game);
        System.out.println("A* Manhattan: " + astar.solveAStar(true));
        System.out.println("A* Hamming: " + astar.solveAStar(false));
    }
}