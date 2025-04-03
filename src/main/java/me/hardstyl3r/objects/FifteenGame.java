package me.hardstyl3r.objects;

public class FifteenGame {
    private final int x;
    private final int y;
    private final int[] board;

    public FifteenGame(int x, int y) {
        this.x = x;
        this.y = y;
        this.board = new int[x * y];
    }

    public void setBoard(int position, int value) {
        board[position] = value;
    }

    public int getOnBoard(int position) {
        return this.board[position];
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
}
