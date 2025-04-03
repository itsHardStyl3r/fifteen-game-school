package me.hardstyl3r.algorithms;

import me.hardstyl3r.enums.Move;
import me.hardstyl3r.objects.FifteenGame;

public class Utils {
    public static int[] getGameTarget(int x, int y) {
        int[] goal = new int[x * y];
        for (int i = 0; i < goal.length - 1; i++) goal[i] = i + 1;
        return goal;
    }

    public static int[] moveTile(FifteenGame game, int[] board, Move move) {
        int zeroIndex = 0;
        for (int i = 0; i < board.length; i++) {
            if (board[i] == 0) {
                zeroIndex = i;
                break;
            }
        }

        int x = zeroIndex / game.getY(), y = zeroIndex % game.getY();
        int newX = x + move.dx, newY = y + move.dy;
        if (newX < 0 || newX >= game.getX() || newY < 0 || newY >= game.getY()) return null;

        int[] newBoard = board.clone();
        int newZeroIndex = newX * game.getY() + newY;
        newBoard[zeroIndex] = newBoard[newZeroIndex];
        newBoard[newZeroIndex] = 0;
        return newBoard;
    }
}
