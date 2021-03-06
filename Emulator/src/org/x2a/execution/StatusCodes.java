package org.x2a.execution;

import java.util.Random;

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


    public static boolean isCode(short status, short code) {
        return (status & code) != 0;
    }

    public static boolean isCarry(short status) {
        return isCode(status, CARRY);
    }

    public static boolean isZero(short status) {
        return isCode(status, ZERO);
    }

    public static boolean isSign(short status) {
        return isCode(status, SIGN);
    }

    public static boolean isOverflow(short status) {
        return isCode(status, OVERFLOW);
    }

    public static boolean isBit(short status) {
        return isCode(status, BIT);
    }

    public static boolean isReadNull(short status) {
        return isCode(status, READ_NULL);
    }

    public static boolean isWriteNull(short status) {
        return isCode(status, WRITE_NULL);
    }

    public static boolean isStackOverflow(short status) {
        return isCode(status, STACK_OVERFLOW);
    }
}
