package org.x2a.execution;

/**
 * Created by ethan on 6/27/17.
 */
public final class StatusCodes {
    private StatusCodes() {}

    public static final short CARRY = 0x0001;
    public static final short ZERO = 0x0002;
    public static final short SIGN = 0x0010;
    public static final short OVERFLOW = 0x0020;
    public static final short BIT = 0x0100;
    public static final short READ_NULL = 0x0200;
    public static final short WRITE_NULL = 0x1000;
    public static final short STACK_OVERFLOW = 0x2000;
}
