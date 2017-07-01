package org.x2a.instruction;

/**
 * Created by ethan on 6/26/17.
 */
public enum InstructionType {

    NOP(0x0, false, 2),
    VAL(0x1, true, 4),
    VAL8(0x2, false, 2),
    MOV(0x3, true, 2),
    JMP(0x4, true, 2),
    LOOP(0x5, false, 2),
    LD(0x6, false, 2),
    STR(0x7, false, 2),
    PUSH(0x8, false, 2),
    POP(0x9, false, 2),
    CALL(0xA, false, 4),
    RET(0xB, false, 2),
    ALU(0xC, false, 2),
    INC(0xD, false, 2); //this is probably supposed to be an alu op

    byte opcode;
    boolean conditional;
    int size;
    InstructionType(int opcode, boolean conditional, int size) {
        this.opcode = (byte) opcode;
        this.conditional = conditional;
        this.size = size;
    }

    public byte opcode() {
        return opcode;
    }

    public boolean conditional() {
        return conditional;
    }

    public int size() {
        return size;
    }
}
