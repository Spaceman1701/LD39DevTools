package org.x2a.parse.section;

import java.io.Serializable;

/**
 * Created by ethan on 7/3/17.
 */
public class InfoSection implements Section, Serializable {
    private final int offset;
    private final String start;
    private final boolean autoOffset;


    public InfoSection(int offset, boolean autoOffset, String start) {
        this.offset = offset;
        this.start = start;
        this.autoOffset = autoOffset;
    }

    public String getStart() {
        return start;
    }

    public int getOffset() {
        return offset;
    }

    public boolean getAutoOffset() {
        return autoOffset;
    }
}
