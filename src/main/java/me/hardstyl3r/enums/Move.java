package me.hardstyl3r.enums;

public enum Move {
    LEFT(0, -1), RIGHT(0, 1), UP(-1, 0), DOWN(1, 0);

    public final int dx;
    public final int dy;

    Move(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public static Move getMove(char c) {
        switch (c) {
            case 'L':
                return Move.LEFT;
            case 'R':
                return Move.RIGHT;
            case 'U':
                return Move.UP;
            case 'D':
                return Move.DOWN;
            default:
                throw new IllegalArgumentException("Invalid move");
        }
    }
}
