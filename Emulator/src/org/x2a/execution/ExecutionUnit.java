package org.x2a.execution;

import java.util.Stack;

/**
 * Created by Ethan on 6/25/2017.
 */
public class ExecutionUnit {
    public static final int WORD_SIZE = 2;

    /** register constants **/
    public static final byte R0 = 0x0;
    public static final byte R1 = 0x1;
    public static final byte R2 = 0x2;
    public static final byte R3 = 0x3;
    public static final byte R4 = 0x4;
    public static final byte R5 = 0x5;
    public static final byte R6 = 0x6;
    public static final byte R7 = 0x7;
    public static final byte R8 = 0x8;
    public static final byte R9 = 0x9;
    public static final byte BP = 0xA;
    public static final byte SL = 0xB;
    public static final byte IP = 0xC;
    public static final byte SP = 0xD;
    public static final byte STATUS = 0xE;
    public static final byte INTERRUPT = 0xF;

    public static final short NULL = 0x0;

    private byte[] memory = new byte[0xFFFF];
    private short[] reg = new short[0xF];

    private Stack<Object> stackTrace; //for debug

    private long cyclesPerSecond;

    public ExecutionUnit(long cyclesPerSecond) {
        this.cyclesPerSecond = cyclesPerSecond;
    }

    private byte fetch() {
        return memory[reg[IP]++];
    }

    public int decode() {
        int inst = (fetch() << 8) + fetch();
        if (isTwoWords(inst)) {
            inst = inst << (WORD_SIZE * 8);
            inst += (fetch() << 8) + fetch();
        }
        return inst;
    }

    public boolean isTwoWords(int inst) {
        return false;
    }
}
