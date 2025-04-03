package me.hardstyl3r.managers;

import me.hardstyl3r.objects.FifteenGame;

import java.io.File;
import java.util.Scanner;
import java.util.logging.Logger;

public class FifteenManager {

    Logger logger = Logger.getLogger("FifteenManager");

    public FifteenGame loadFromFile(File f) {
        int x, y;
        Scanner s;
        try {
            s = new Scanner(f);
        } catch (Exception e) {
            logger.severe("Error: " + e.getMessage() + ".");
            return null;
        }
        if (!s.hasNextLine()) {
            logger.severe("File " + f.getAbsolutePath() + " appears to be empty");
            return null;
        }
        String[] line = s.nextLine().split(" ");
        x = Integer.parseInt(line[0]);
        y = Integer.parseInt(line[1]);
        FifteenGame game = new FifteenGame(x, y);
        for (int i = 0; i < x; i++) {
            line = s.nextLine().split(" ");
            for (int j = 0; j < y; j++) game.setBoard(i, j, Integer.parseInt(line[j]));
        }
        return game;
    }

    public String displayGame(FifteenGame game) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < game.getX(); i++) {
            for (int j = 0; j < game.getY(); j++) sb.append(game.getOnBoard(i, j)).append(" ");
            sb.append("\n");
        }
        return sb.toString();
    }
}
