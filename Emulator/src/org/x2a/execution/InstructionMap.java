package org.x2a.execution;

import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by ethan on 6/27/17.
 */
public class InstructionMap {
    private TreeMap<Integer, Method> map;

    public InstructionMap() {
        map = new TreeMap<>(Integer::compareUnsigned);
    }

    public void put(int key, Method value) {
        map.put(key, value);
    }

    public Method getFirst(int key) {
        Map.Entry<Integer, Method> entry = map.floorEntry(key);
        return entry.getValue();
    }
}
