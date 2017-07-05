package org.x2a.assemble;

/**
 * Created by ethan on 7/4/17.
 */
public class Location {
    private final int start;
    private final int end;

    public Location(int start, int size) {
        this.start = start;
        this.end = this.start + size;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }
}
