package org.example.objects;

public class FifteenGame {
    private final int x;
    private final int y;
    private final int[][] board;

    public FifteenGame(int x, int y) {
        this.x = x;
        this.y = y;
        this.board = new int[x][y];
    }

    public void setBoard(int x, int y, int i) {
        board[x][y] = i;
    }

    public int getOnBoard(int x, int y) {
        return this.board[x][y];
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
}
