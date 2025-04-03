package me.hardstyl3r.managers;

import me.hardstyl3r.objects.FifteenGame;

import java.io.File;
import java.nio.file.Files;
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
        x = Integer.parseInt(text.getFirst().split(" ")[0]);
        y = Integer.parseInt(text.getFirst().split(" ")[1]);
        text.removeFirst();
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
            sb.append(game.getOnBoard(i)).append(" ");
            if ((i + 1) % game.getY() == 0) sb.append("\n");
        }
        return sb.toString();
    }
}
