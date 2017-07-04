package org.x2a.parse.section;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by ethan on 7/3/17.
 */
public class DataSection implements Section, Serializable{
    private final Map<String, int[]> data;

    public DataSection(Map<String, int[]> data) {
        this.data = data;
    }
}
