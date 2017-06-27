package org.x2a.instruction;

/**
 * Created by ethan on 6/26/17.
 */
public enum InstructionMod {

    ALU_ADD(0x0),
    ALU_ADDC(0x1),
    ALU_SUB(0x2),
    ALU_SUBB(0x3),
    ALU_AND(0x4),
    ALU_OR(0x5),
    ALU_XOR(0x8),
    ALU_NOT(0x7),
    ALU_BIT(0x8),

    COND_NOP(0x0),
    COND_EQ(0x1),
    COND_NEQ(0x2),
    COND_BELOW(0x3),
    COND_NBELOW(0x4),
    COND_ABOVE(0x5),
    COND_NABOVE(0x6),
    COND_LESS(0x7),
    COND_LESSEQ(0x8),
    COND_GREATER(0x9),
    COND_GREATER_EQ(0xA),
    COND_BIT(0xB),
    COND_NBIT(0xC);

    byte value;
    InstructionMod(int value) {
        this.value = (byte) value;
    }

    public byte opcode() {
        return value;
    }
}
