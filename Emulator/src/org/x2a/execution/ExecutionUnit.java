package org.x2a.execution;

import org.x2a.instruction.Instruction;
import org.x2a.instruction.InstructionMod;
import org.x2a.instruction.InstructionType;

import java.lang.reflect.InvocationTargetException;
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
    private short[] reg = new short[0x10];

    private Stack<Object> stackTrace; //for debug

    private long cyclesPerSecond;

    private InstructionMap opMethods;

    public ExecutionUnit(long cyclesPerSecond) {
        this.cyclesPerSecond = cyclesPerSecond;

        Method[] methods = this.getClass().getDeclaredMethods();

        opMethods = new InstructionMap();

        for (Method method : methods) {
            Operation op = method.getDeclaredAnnotation(Operation.class);
            if (op != null) {
                byte code = (byte) ((op.inst().opcode() << 4) + (op.mod().opcode()));
                opMethods.put(code, method);
            }
        }
        System.out.println("finished init");
    }

    private byte fetch() {
        return memory[reg[IP]++];
    }

    public int fullFetch() {
        int inst = (fetch() << 8) + fetch();
        inst = inst << (WORD_SIZE * 8);
        if (isTwoWords(inst)) {
            inst += (fetch() << 8) + fetch();
        }
        return inst;
    }

    public void decode(int inst) {
        Map<Byte, InstructionType> instMap;
        byte fullcode = (byte) ((inst & DecodeUtils.BYTE_3_MASK) >>> DecodeUtils.BYTE_BIT_SIZE * 3);

        InstructionType type = instMap.get((byte) (DecodeUtils.NIBBLE_1_MASK & fullcode));
        if (type.conditional()) {
            //decode conditional
        } else {
            Method m = opMethods.getFirst(inst);
        }
    }

    public void decodeConditional(int inst, byte fullcode) {
        byte condCode = (byte) (fullcode & DecodeUtils.NIBBLE_0_MASK);
        Method m = opMethods.getFirst(inst);
        if ((condCode == InstructionMod.COND_EQ.opcode()) ||
                (condCode == InstructionMod.COND_LESSEQ.opcode()) ||
                condCode == InstructionMod.COND_GREATER_EQ.opcode() ||
                condCode == InstructionMod.COND_NABOVE.opcode() ||
                condCode == InstructionMod.COND_NBELOW.opcode()) {
            if ((reg[STATUS] & StatusCodes.ZERO) != 0) { // zero
                //
                return;
            }
        }
        if ((condCode == InstructionMod.COND_NEQ.opcode()) || (condCode == InstructionMod.COND_NABOVE.opcode())) {
            if ((reg[STATUS] & StatusCodes.ZERO) == 0) { // not zero
                //
                return;
            }
        }
        if ((condCode == InstructionMod.COND_BELOW.opcode()) || (condCode == InstructionMod.COND_NABOVE.opcode())) {
            if ((reg[STATUS] & StatusCodes.CARRY) != 0) { //carry
                //
                return;
            }
        }
        if ((condCode == InstructionMod.COND_NBELOW.opcode()) || (condCode == InstructionMod.COND_ABOVE.opcode())) {
            if ((reg[STATUS] & StatusCodes.CARRY) == 0) { //not carry
                //
                return;
            }
        }
        if ((condCode == InstructionMod.COND_LESS.opcode()) || (condCode == InstructionMod.COND_LESSEQ.opcode())) {
            if (((reg[STATUS] & StatusCodes.OVERFLOW) == 0) != ((reg[STATUS] & StatusCodes.SIGN) == 0)) { //sign != carry
                return;
            }
        }
        if ((condCode == InstructionMod.COND_GREATER.opcode())) {
            if ((((reg[STATUS] & StatusCodes.OVERFLOW) == 0) == ((reg[STATUS] & StatusCodes.SIGN) == 0))
                    && (reg[STATUS] & StatusCodes.ZERO) == 0) { //!zero && (sign == carry)
                return;
            }
        }
        if (condCode == InstructionMod.COND_GREATER_EQ.opcode()) {
            if (((reg[STATUS] & StatusCodes.OVERFLOW) == 0) == ((reg[STATUS] & StatusCodes.SIGN) == 0)) { //sign == overflow
                return;
            }
        }
        if (condCode == InstructionMod.COND_BIT.opcode()) {
            if ((reg[STATUS] & StatusCodes.BIT) != 0) { //bit
                return;
            }
        }
        if (condCode == InstructionMod.COND_NBIT.opcode()) {
            if ((reg[STATUS] & StatusCodes.BIT) == 0) {
                return;
            }
        }
    }

    public boolean isTwoWords(int inst) {
        return false;
    }

    public void setStatus(short newDest) {
        short status = 0;
        if (newDest == 0) {
            status |= StatusCodes.ZERO;
        }
        if (newDest < 0) {
            status |= StatusCodes.SIGN;
        }
        reg[STATUS] = status;
    }

    public void mathSetStatus(short oldDest, short newDest, short src) {
        setStatus(newDest);
        if ((src < 0 == oldDest < 0) && (newDest < 0) != (oldDest < 0)) {
            reg[STATUS] |= StatusCodes.OVERFLOW;
        }
    }

    //ALY
    @Operation(inst = InstructionType.ALU, mod = InstructionMod.ALU_ADD)
    public void add(int inst) {
        byte dest = DecodeUtils.IType.destReg(inst);
        byte src = DecodeUtils.IType.srcReg(inst);
        short oldVal = reg[dest];
        reg[dest] = (short) (reg[dest] + reg[src]);

        mathSetStatus(oldVal, reg[dest], reg[src]);

        System.out.println("Add");
    }

    @Operation(inst = InstructionType.ALU, mod = InstructionMod.ALU_ADDC)
    public void addc(int inst) {
        byte dest = DecodeUtils.IType.destReg(inst);
        byte src = DecodeUtils.IType.srcReg(inst);

        short oldValue = reg[dest];
        char unsigned = (char) oldValue;
        char unsignedRes = (char) (reg[dest] + reg[src]);
        reg[dest] = (short)(unsignedRes);
        mathSetStatus(oldValue, reg[dest], reg[src]);

        if (unsignedRes < unsigned) { //must have been a carry
            reg[STATUS] |= StatusCodes.CARRY;
        }
    }

    @Operation(inst = InstructionType.ALU, mod = InstructionMod.ALU_SUB)
    public void sub(int inst) {
        byte dest = DecodeUtils.IType.destReg(inst);
        byte src = DecodeUtils.IType.srcReg(inst);

        short old = reg[dest];
        reg[dest] = (short)(reg[dest] - reg[src]);
        mathSetStatus(old, reg[dest], reg[src]);

        System.out.println("Sub");
    }

    @Operation(inst = InstructionType.ALU, mod = InstructionMod.ALU_SUBB)
    public void subb(int inst) {
        byte dest = DecodeUtils.IType.destReg(inst);
        byte src = DecodeUtils.IType.srcReg(inst);

        short old = reg[dest];
        char unsigned = (char)old;
        char unsignedRes = (char)(reg[dest] - reg[src]);

        reg[dest] = (short)(unsignedRes);
        mathSetStatus(old, reg[dest], reg[src]);

        if (unsignedRes > unsigned) { //must be borrow
            reg[STATUS] |= StatusCodes.CARRY;
        }
    }

    @Operation(inst = InstructionType.ALU, mod = InstructionMod.ALU_AND)
    public void and(int inst) {
        byte dest = DecodeUtils.IType.destReg(inst);
        byte src = DecodeUtils.IType.srcReg(inst);

        reg[dest] &= src;
        setStatus(reg[dest]);
    }

    @Operation(inst = InstructionType.ALU, mod = InstructionMod.ALU_OR)
    public void or(int inst) {
        byte dest = DecodeUtils.IType.destReg(inst);
        byte src = DecodeUtils.IType.srcReg(inst);

        reg[dest] |= src;
        setStatus(reg[dest]);
    }

    @Operation(inst = InstructionType.ALU, mod = InstructionMod.ALU_XOR)
    public void xor(int inst) {
        byte dest = DecodeUtils.IType.destReg(inst);
        byte src = DecodeUtils.IType.srcReg(inst);

        reg[dest] ^= src;

        setStatus(reg[dest]);
    }

    @Operation(inst = InstructionType.ALU, mod = InstructionMod.ALU_NOT)
    public void not(int inst) {
        byte dest = DecodeUtils.IType.destReg(inst);

        reg[dest] = (short)~reg[dest];
        setStatus(reg[dest]);
    }

    @Operation(inst = InstructionType.ALU, mod = InstructionMod.ALU_BIT)
    public void bit(int inst) {
        byte dest = DecodeUtils.IType.destReg(inst);
        byte src = DecodeUtils.IType.srcReg(inst);

        if (((dest >>> src) & 0x00001) != 0) {
            reg[STATUS] = StatusCodes.BIT;
        } else {
            reg[STATUS] = 0;
        }
    }
    //END ALU

    @Operation(inst = InstructionType.NOP)
    public void nop(int inst) {

    }

    @Operation(inst = InstructionType.VAL, mod = InstructionMod.COND_NOP)
    public void val(int inst) {
        byte dest = DecodeUtils.AType.destReg(inst);
        short val = DecodeUtils.AType.value(inst);

        reg[dest] = val;
    }

    @Operation(inst = InstructionType.VAL, mod = InstructionMod.COND_EQ)
    public void valEqual(int inst) {
        if ((reg[STATUS] & StatusCodes.ZERO) != 0) {
            val(inst);
        }
    }

    @Operation(inst = InstructionType.VAL, mod = InstructionMod.COND_NEQ)
    public void valNotEqual(int inst) {
        if ((reg[STATUS] & StatusCodes.ZERO) == 0) {
            val(inst);
        }
    }

    @Operation(inst = InstructionType.VAL, mod = InstructionMod.COND_BELOW)
    public void valBelow(int inst) {
        if ((reg[STATUS] & StatusCodes.CARRY) != 0) {
            val(inst);
        }
    }

    @Operation(inst = InstructionType.VAL, mod = InstructionMod.COND_NBELOW)
    public void valNotBelow(int inst) {
        if ((reg[STATUS] & StatusCodes.CARRY) == 0) {
            val(inst);
        }
    }

    @Operation(inst = InstructionType.VAL, mod = InstructionMod.COND_ABOVE)
    public void valAbove(int inst) {
        if ((reg[STATUS] & StatusCodes.CARRY) != 0 || (reg[STATUS] & StatusCodes.ZERO) != 0) {
            val(inst);
        }
    }

    @Operation(inst = InstructionType.VAL, mod = InstructionMod)

    @Operation(inst = InstructionType.VAL8)
    public void val8(int inst) {
        byte dest = DecodeUtils.BType.destReg(inst);
        byte val = DecodeUtils.BType.value(inst);

        reg[dest] = val;
    }

    @Operation(inst = InstructionType.MOV, mod = InstructionMod.COND_NOP)
    public void mov(int inst) {
        byte dest = DecodeUtils.CType.destReg(inst);
        byte src = DecodeUtils.CType.srcReg(inst);

        reg[dest] = reg[src];

        System.out.println("Mov");
    }
}
