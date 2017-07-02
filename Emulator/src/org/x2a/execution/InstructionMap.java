package org.x2a.execution;

import java.lang.reflect.Method;
import java.util.TreeMap;

/**
 * Created by ethan on 7/1/17.
 */
public class InstructionMap {
    private TreeMap<Integer, Method> map;

    public InstructionMap() {
        map = new TreeMap<>(Integer::compareUnsigned);
    }

    public void put(int code, Method m) {
        map.put(code, m);
    }

    public Method get(int inst) {
        return map.floorEntry(inst).getValue();
    }
}
