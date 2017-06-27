package org.x2a.execution;

import org.x2a.instruction.InstructionMod;
import org.x2a.instruction.InstructionType;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
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

    private Map<Byte, Method> opMethods;

    public ExecutionUnit(long cyclesPerSecond) {
        this.cyclesPerSecond = cyclesPerSecond;

        Method[] methods = this.getClass().getDeclaredMethods();

        opMethods = new HashMap<>();

        for (Method method : methods) {
            Operation op = method.getDeclaredAnnotation(Operation.class);
            if (op != null) {
                byte code = (byte) ((op.inst().opcode() << 4) + (op.mod().opcode()));
                opMethods.put(op.inst().opcode(), method);
            }
        }
        System.out.println("finished init");
    }

    private byte fetch() {
        return memory[reg[IP]++];
    }

    public int fullFetch() {
        int inst = (fetch() << 8) + fetch();
        if (isTwoWords(inst)) {
            inst = inst << (WORD_SIZE * 8);
            inst += (fetch() << 8) + fetch();
        }
        return inst;
    }

    public void decode(int inst) {

    }

    public boolean isTwoWords(int inst) {
        return false;
    }

    @Operation(inst = InstructionType.ALU, mod = InstructionMod.ALU_ADD)
    public void add(int inst) {
        short dest = (short)((inst & 0x00F0) >>> 20);
        short src = (short)((inst & 0x000F) >>> 16);

        reg[dest] = (short) (reg[dest] + reg[src]);
    }
}
