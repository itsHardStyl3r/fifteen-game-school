package me.hardstyl3r;

import me.hardstyl3r.algorithms.FifteenAStarSolver;
import me.hardstyl3r.algorithms.FifteenBFSSolver;
import me.hardstyl3r.algorithms.FifteenDFSSolver;
import me.hardstyl3r.managers.FifteenManager;
import me.hardstyl3r.objects.FifteenGame;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger("The Fifteen Game");
    public static DecimalFormat df = new DecimalFormat("#.###");

    public static void main(String[] args) {
        String[] orders = {"RDUL", "RDLU", "DRUL", "DRLU", "LUDR", "LURD", "ULDR", "ULRD"};
        String[] arguments = {"manh", "hamm"};

        if (args.length == 2 && args[0].equalsIgnoreCase("generate")) {
            long current = System.currentTimeMillis();
            File path = new File(args[1]);
            File[] files = path.listFiles();
            if (files == null || !path.isDirectory()) {
                logger.severe("Invalid directory: " + args[1]);
                return;
            }

            FifteenManager manager = new FifteenManager();
            logger.info("Solving all files in " + path.getAbsolutePath() + "...");
            for (File f : files) {
                if (f.getName().length() != 16) continue;
                FifteenGame game = manager.loadFromFile(f);
                if (game == null) {
                    logger.info("Skipping " + f.getName() + ".");
                    continue;
                }
                System.out.println("Solving and saving " + f.getName() + "...");

                FifteenBFSSolver bfsSolver = new FifteenBFSSolver(game);
                for (String order : orders) {
                    String solution = bfsSolver.solveBFS(order);
                    String stats = bfsSolver.returnLatestStat().toFileDetails();
                    manager.saveToFiles(path, f.getName(), "bfs", order.toLowerCase(), solution, stats);
                }
                FifteenDFSSolver dfsSolver = new FifteenDFSSolver(game);
                for (String order : orders) {
                    String solution = dfsSolver.solveDFS(order);
                    String stats = dfsSolver.returnLatestStat().toFileDetails();
                    manager.saveToFiles(path, f.getName(), "dfs", order.toLowerCase(), solution, stats);
                }
                FifteenAStarSolver aStarSolver = new FifteenAStarSolver(game);
                for (String argument : arguments) {
                    boolean useManhattan = argument.equalsIgnoreCase("manh");
                    String solution = aStarSolver.solveAStar(useManhattan);
                    String stats = aStarSolver.returnLatestStat().toFileDetails();
                    manager.saveToFiles(path, f.getName(),  "astr", useManhattan ? "manh" : "hamm", solution, stats);
                }
            }
            logger.info("Finished. (took " + (System.currentTimeMillis() - current) + "ms)");
            return;
        }
        if (args.length != 5 && args.length != 3) {
            logger.log(Level.INFO, "Usage: java -jar <jar_file> <algorithm> <argument> <input_file> [solution_file] [stats_file]");
            return;
        }
        String[] algorithms = {"bfs", "dfs", "astr"};
        String algorithm = args[0];
        String argument = args[1];
        String inputFile = args[2];

        FifteenManager manager = new FifteenManager();
        FifteenGame game = manager.loadFromFile(new File(inputFile));
        if (game == null) {
            logger.severe("Failed to load the game " + inputFile + ".");
            return;
        }
        if (!Arrays.asList(algorithms).contains(algorithm.toLowerCase())) {
            logger.severe("Unknown " + algorithm + " algorithm.");
            return;
        } else {
            algorithm = algorithm.toLowerCase();
        }
        if (!Arrays.asList(arguments).contains(argument) && algorithm.equals("astr")) {
            logger.severe("Unknown " + argument + " argument.");
            return;
        }
        if (!Arrays.asList(orders).contains(argument) && !algorithm.equals("astr")) {
            logger.severe("Unknown order " + argument + ".");
            return;
        }
        String solution = "";
        String stats = "";
        File solutionFile = new File(inputFile.split(".txt")[0] + "_" + algorithm + "_" + argument + "_sol.txt");
        File statsFile = new File(inputFile.split(".txt")[0] + "_" + algorithm + "_" + argument + "_stats.txt");
        if (args.length == 5) {
            solutionFile = new File(args[3]);
            statsFile = new File(args[4]);
        }
        switch (algorithm) {
            case "bfs":
                FifteenBFSSolver bfsSolver = new FifteenBFSSolver(game);
                solution = bfsSolver.solveBFS(argument);
                stats = bfsSolver.returnLatestStat().toFileDetails();
                break;
            case "dfs":
                FifteenDFSSolver dfsSolver = new FifteenDFSSolver(game);
                solution = dfsSolver.solveDFS(argument);
                stats = dfsSolver.returnLatestStat().toFileDetails();
                break;
            case "astr":
                FifteenAStarSolver aStarSolver = new FifteenAStarSolver(game);
                solution = aStarSolver.solveAStar(argument.equalsIgnoreCase("manh"));
                stats = aStarSolver.returnLatestStat().toFileDetails();
                break;
            default:
                logger.info("Unknown algorithm.");
                break;
        }
        try {
            String output = solution.equals("DNF") ? "-1" : solution.length() + "\n" + solution;
            Files.write(solutionFile.toPath(), output.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            logger.severe("Failed to write solution to file: " + e.getMessage());
        }
        try {
            Files.write(statsFile.toPath(), stats.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            logger.severe("Failed to write stats to file: " + e.getMessage());
        }
    }
}