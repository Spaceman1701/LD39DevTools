package org.x2a.instruction;

import static org.x2a.instruction.ParamInfo.*;
/**
 * Created by ethan on 7/2/17.
 */
public enum InstructionInfo {
    //contains parameter lists for every instruction
    NOP(0x0, -0x1, NONE, NONE), //nop

    VAL(0x1, 0x0, REGISTER, WORD), //val unconditional
    VEQ(0x1, 0x1, REGISTER, WORD), //val equal
    VNE(0x1, 0x2, REGISTER, WORD), //not equal
    VBL(0x1, 0x3, REGISTER, WORD), //below
    VNB(0x1, 0x4, REGISTER, WORD), //not below
    VNA(0x1, 0x5, REGISTER, WORD), //above
    VAB(0x1, 0x6, REGISTER, WORD), //not above
    VLT(0x1, 0x7, REGISTER, WORD),
    VLE(0x1, 0x8, REGISTER, WORD),
    VGT(0x1, 0x9, REGISTER, WORD),
    VGE(0x1, 0xA, REGISTER, WORD),
    VBT(0x1, 0xB, REGISTER, WORD),
    VNBT(0x1, 0xC, REGISTER, WORD), //end val

    SVL(0x2, -0x1, REGISTER, BYTE), //val8

    MOV(0x3, 0x0, REGISTER, REGISTER), //mov
    MEQ(0x3, 0x1, REGISTER, REGISTER), //mov
    MNE(0x3, 0x2, REGISTER, REGISTER), //mov
    MBL(0x3, 0x3, REGISTER, REGISTER), //mov
    MNB(0x3, 0x4, REGISTER, REGISTER), //mov
    MNA(0x3, 0x5, REGISTER, REGISTER), //mov
    MAB(0x3, 0x6, REGISTER, REGISTER), //mov
    MLT(0x3, 0x7, REGISTER, REGISTER), //mov
    MLE(0x3, 0x8, REGISTER, REGISTER), //mov
    MGT(0x3, 0x9, REGISTER, REGISTER), //mov
    MGE(0x3, 0xA, REGISTER, REGISTER), //mov
    MBT(0x3, 0xB, REGISTER, REGISTER), //mov
    MNBT(0x3, 0xC, REGISTER, REGISTER), //mov

    JMP(0x4, 0x0, BYTE, NONE),
    JEQ(0x4, 0x1, BYTE, NONE),
    JNE(0x4, 0x2, BYTE, NONE),
    JBL(0x4, 0x3, BYTE, NONE),
    JNB(0x4, 0x4, BYTE, NONE),
    JNA(0x4, 0x5, BYTE, NONE),
    JAB(0x4, 0x6, BYTE, NONE),
    JLT(0x4, 0x7, BYTE, NONE),
    JLE(0x4, 0x8, BYTE, NONE),
    JGT(0x4, 0x9, BYTE, NONE),
    JGE(0x4, 0xA, BYTE, NONE),
    JBT(0x4, 0xB, BYTE, NONE),
    JNBT(0x4, 0xC, BYTE, NONE),

    LOOP(0x5, -0x1, REGISTER, BYTE),

    LD(0x6, -0x1, REGISTER, REGISTER),
    STR(0x7, -0x1, REGISTER, REGISTER),

    PUSH(0x8, -0x1, REGISTER, NONE),
    POP(0x9, -0x1, REGISTER, NONE),

    CALL(0xA, -0x1, WORD, NONE),
    RET(0xB, -0x1, NONE, NONE),

    ADD(0xC, 0x0, REGISTER, REGISTER),
    ADDC(0xC, 0x1, REGISTER, REGISTER),

    SUB(0xC, 0x2, REGISTER, REGISTER),
    SUBB(0xC, 0x3, REGISTER, REGISTER),

    AND(0xC, 0x4, REGISTER, REGISTER),
    OR(0xC, 0x5, REGISTER, REGISTER),
    XOR(0xC, 0x6, REGISTER, REGISTER),
    NOT(0xC, 0x7, REGISTER, REGISTER),
    BIT(0xC, 0x8, REGISTER, REGISTER),

    INCR(0xD, -0x1, BYTE, NONE);

    int op;
    int mod;
    ParamInfo left;
    ParamInfo right;
    boolean modifier;

    InstructionInfo(int op, int mod, ParamInfo left, ParamInfo right) {
        this.op = op;
        this.mod = mod;
        this.left = left;
        this.right = right;

        modifier = (mod != -0x1);
    }

    public int op() {
        return op;
    }

    public int mod() {
        return mod;
    }

    public ParamInfo left() {
        return left;
    }

    public ParamInfo right() {
        return right;
    }

    public boolean hasModifier() {
        return modifier;
    }
}
