package org.x2a.assemble;

import org.x2a.parse.ParsedObject;

import java.util.List;

/**
 * Created by ethan on 7/4/17.
 */
public class Linker {
    private final List<ParsedObject> objects;
    private byte[] output = new byte[Integer.MAX_VALUE & 0xFFFF];

    public Linker(List<ParsedObject> objects) {
        this.objects = objects;
    }
}