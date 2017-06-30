package org.x2a.instruction;

/**
 * Created by ethan on 6/26/17.
 */
public enum InstructionType {

    NOP(0x0),
    VAL(0x1),
    VAL8(0x2),
    MOV(0x3),
    JMP(0x4),
    LOOP(0x5),
    LD(0x6),
    STR(0x7),
    PUSH(0x8),
    POP(0x9),
    CALL(0xA),
    RET(0xB),
    ALU(0xC),
    INC(0xD); //this is probably supposed to be an alu op

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
