package org.x2a.parse.section;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ethan on 7/3/17.
 */
public class InfoSection implements Section, Serializable {
    private final int offset;
    private final String start;
    private final boolean autoOffset;
    private final List<String> exports;


    public InfoSection(int offset, boolean autoOffset, String start, List<String> exports) {
        this.offset = offset;
        this.start = start;
        this.autoOffset = autoOffset;
        this.exports = exports;
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

    public List<String> getExports() {
        return exports;
    }
}
