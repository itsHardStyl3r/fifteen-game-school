package me.hardstyl3r;

import me.hardstyl3r.managers.FifteenManager;
import me.hardstyl3r.objects.FifteenGame;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        FifteenManager manager = new FifteenManager();
        FifteenGame game = manager.loadFromFile(new File("games/4x4_01_00001.txt"));
        if (game != null) System.out.println(manager.displayGame(game));
        else System.out.println("Failed to display the game.");
    }
}