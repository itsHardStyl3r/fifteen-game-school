package me.hardstyl3r.objects;

import static me.hardstyl3r.Main.df;

public class GameStat {
    private int solutionLength;
    private final int statesVisited;
    private final int statesProcessed;
    private final int maxRecursionDepth;
    private final long timeElapsedInNano;

    public GameStat(int solutionLength, int statesVisited, int statesProcessed, int maxRecursionDepth, long timeElapsedInNano) {
        this.solutionLength = solutionLength;
        this.statesVisited = statesVisited;
        this.statesProcessed = statesProcessed;
        this.maxRecursionDepth = maxRecursionDepth;
        this.timeElapsedInNano = timeElapsedInNano;
    }

    @Override
    public String toString() {
        return "GameStat{" +
                "solutionLength=" + solutionLength +
                ", statesVisited=" + statesVisited +
                ", statesProcessed=" + statesProcessed +
                ", maxRecursionDepth=" + maxRecursionDepth +
                ", timeElapsed=" + getTimeInMillis() +
                '}';
    }

    public String toFileDetails() {
        return solutionLength + "\n" +
                statesVisited + "\n" +
                statesProcessed + "\n" +
                maxRecursionDepth + "\n" +
                getTimeInMillis() + "\n";
    }

    public void setSolutionLength(int solutionLength) {
        this.solutionLength = solutionLength;
    }

    public String getTimeInMillis() {
        return df.format(timeElapsedInNano / 1000000f);
    }
}
