package org.acme.util;

public class Range{
    private final int startIndex;
    private final int lastIndex;

    public Range(int startIndex, int lastIndex) {
        this.startIndex = startIndex;
        this.lastIndex = lastIndex;
    }

    public static Range of(int startIndex, int lastIndex) {
        return new Range(startIndex, lastIndex);
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getLastIndex() {
        return lastIndex;
    }
}