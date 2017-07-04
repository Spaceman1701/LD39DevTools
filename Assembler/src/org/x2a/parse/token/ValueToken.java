package org.x2a.parse.token;

/**
 * Created by ethan on 7/3/17.
 */
public class ValueToken implements Token{
    private static final Type TYPE = Type.VALUE;

    private final int value;

    public ValueToken(int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public Type getType() {
        return TYPE;
    }

    @Override
    public String toString() {
        return "VAL:" + Integer.toHexString(value);
    }
}
