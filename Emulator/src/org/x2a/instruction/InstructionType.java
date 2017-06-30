package org.x2a.instruction;

/**
 * Created by ethan on 6/26/17.
 */
public enum InstructionType {

    NOP(0x0, false),
    VAL(0x1, true),
    VAL8(0x2, false),
    MOV(0x3, true),
    JMP(0x4, true),
    LOOP(0x5, false),
    LD(0x6, false),
    STR(0x7, false),
    PUSH(0x8, false),
    POP(0x9, false),
    CALL(0xA, false),
    RET(0xB, false),
    ALU(0xC, false),
    INC(0xD, false); //this is probably supposed to be an alu op

    byte opcode;
    boolean conditional;
    InstructionType(int opcode, boolean conditional) {
        this.opcode = (byte) opcode;
        this.conditional = conditional;
    }

    public byte opcode() {
        return opcode;
    }

    public boolean conditional() {
        return conditional;
    }
}
