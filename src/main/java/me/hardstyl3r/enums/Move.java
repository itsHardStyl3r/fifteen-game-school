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
        return switch (c) {
            case 'L' -> Move.LEFT;
            case 'R' -> Move.RIGHT;
            case 'U' -> Move.UP;
            case 'D' -> Move.DOWN;
            default -> throw new IllegalArgumentException("Invalid move");
        };
    }
}
