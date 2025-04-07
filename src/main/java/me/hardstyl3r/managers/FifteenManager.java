package me.hardstyl3r.managers;

import me.hardstyl3r.objects.FifteenGame;

import java.io.BufferedWriter;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.logging.Logger;

public class FifteenManager {

    private final Logger logger = Logger.getLogger("FifteenManager");

    public FifteenGame loadFromFile(File f) {
        int x, y;
        List<String> text;
        try {
            text = Files.readAllLines(f.toPath());
        } catch (Exception e) {
            logger.severe("Failed to read file " + f.getAbsolutePath() + ": " + e.getMessage());
            return null;
        }
        try {
            x = Integer.parseInt(text.get(0).split(" ")[0]);
            y = Integer.parseInt(text.get(0).split(" ")[1]);
        } catch (Exception e) {
            logger.severe("File " + f.getAbsolutePath() + " most likely is not the right format.");
            return null;
        }
        text.remove(0);
        FifteenGame game = new FifteenGame(x, y);
        try {
            int index = 0;
            for (String line : text) {
                String[] number = line.split(" ");
                for (String num : number) game.setBoard(index++, Integer.parseInt(num));
            }
        } catch (Exception e) {
            logger.severe("Failed to parse integers from game data: " + e.getMessage());
            return null;
        }
        return game;
    }

    public String displayGame(FifteenGame game) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < game.getX() * game.getY(); i++) {
            sb.append(game.getOnBoard(i)).append("\t");
            if ((i + 1) % game.getY() == 0) sb.append("\n");
        }
        return sb.toString();
    }

    public void saveToFiles(File directory, String fileName, String algorithm, String strategy, String solution, String stats) {
        String baseName = fileName.split(".txt")[0];
        String solutionContent = solution.equals("DNF") ? "-1" : solution.length() + "\n" + solution;
        String solFileName = String.format("%s_%s_%s_sol.txt", baseName, algorithm, strategy);
        String statsFileName = String.format("%s_%s_%s_stats.txt", baseName, algorithm, strategy);

        try {
            Path solPath = Paths.get(directory.getAbsolutePath(), solFileName);
            try (BufferedWriter writer = Files.newBufferedWriter(solPath,
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
                writer.write(solutionContent);
            }
            Path statsPath = Paths.get(directory.getAbsolutePath(), statsFileName);
            try (BufferedWriter writer = Files.newBufferedWriter(statsPath,
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
                writer.write(stats);
            }
        } catch (Exception e) {
            logger.severe(String.format("Failed to write files for %s: %s", baseName, e.getMessage()));
        }
    }
}
