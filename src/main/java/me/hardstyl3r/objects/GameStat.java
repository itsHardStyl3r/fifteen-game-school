package me.hardstyl3r.objects;

public class GameStat {
    private int solutionLength;
    private final int statesVisited;
    private final int statesProcessed;
    private final int maxRecursionDepth;
    private final long timeElapsed;

    public GameStat(int solutionLength, int statesVisited, int statesProcessed, int maxRecursionDepth, long timeElapsed) {
        this.solutionLength = solutionLength;
        this.statesVisited = statesVisited;
        this.statesProcessed = statesProcessed;
        this.maxRecursionDepth = maxRecursionDepth;
        this.timeElapsed = timeElapsed;
    }

    @Override
    public String toString() {
        return "GameStat{" +
                "solutionLength=" + solutionLength +
                ", statesVisited=" + statesVisited +
                ", statesProcessed=" + statesProcessed +
                ", maxRecursionDepth=" + maxRecursionDepth +
                ", timeElapsed=" + timeElapsed +
                '}';
    }

    public void setSolutionLength(int solutionLength) {
        this.solutionLength = solutionLength;
    }
}
