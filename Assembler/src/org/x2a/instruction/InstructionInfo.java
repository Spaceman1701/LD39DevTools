package org.x2a.instruction;

import static org.x2a.instruction.ParamInfo.*;
/**
 * Created by ethan on 7/2/17.
 */
public enum InstructionInfo {
    //contains parameter lists for every instruction
    NOP(0x0, -0x1, 2, NONE, NONE), //nop

    VAL(0x1, 0x0, 4, REGISTER, WORD), //val unconditional
    VEQ(0x1, 0x1, 4, REGISTER, WORD), //val equal
    VNE(0x1, 0x2, 4, REGISTER, WORD), //not equal
    VBL(0x1, 0x3, 4, REGISTER, WORD), //below
    VNB(0x1, 0x4, 4, REGISTER, WORD), //not below
    VNA(0x1, 0x5, 4, REGISTER, WORD), //above
    VAB(0x1, 0x6, 4, REGISTER, WORD), //not above
    VLT(0x1, 0x7, 4, REGISTER, WORD),
    VLE(0x1, 0x8, 4, REGISTER, WORD),
    VGT(0x1, 0x9, 4, REGISTER, WORD),
    VGE(0x1, 0xA, 4, REGISTER, WORD),
    VBT(0x1, 0xB, 4, REGISTER, WORD),
    VNBT(0x1, 0xC, 4, REGISTER, WORD), //end val

    SVL(0x2, -0x1, 2, REGISTER, BYTE), //val8

    MOV(0x3, 0x0, 2, REGISTER, REGISTER), //mov
    MEQ(0x3, 0x1, 2, REGISTER, REGISTER), //mov
    MNE(0x3, 0x2, 2, REGISTER, REGISTER), //mov
    MBL(0x3, 0x3, 2, REGISTER, REGISTER), //mov
    MNB(0x3, 0x4, 2, REGISTER, REGISTER), //mov
    MNA(0x3, 0x5, 2, REGISTER, REGISTER), //mov
    MAB(0x3, 0x6, 2, REGISTER, REGISTER), //mov
    MLT(0x3, 0x7, 2, REGISTER, REGISTER), //mov
    MLE(0x3, 0x8, 2, REGISTER, REGISTER), //mov
    MGT(0x3, 0x9, 2, REGISTER, REGISTER), //mov
    MGE(0x3, 0xA, 2, REGISTER, REGISTER), //mov
    MBT(0x3, 0xB, 2, REGISTER, REGISTER), //mov
    MNBT(0x3, 0xC, 2, REGISTER, REGISTER), //mov

    JMP(0x4, 0x0, 2, BYTE, NONE),
    JEQ(0x4, 0x1, 2, BYTE, NONE),
    JNE(0x4, 0x2, 2, BYTE, NONE),
    JBL(0x4, 0x3, 2, BYTE, NONE),
    JNB(0x4, 0x4, 2, BYTE, NONE),
    JNA(0x4, 0x5, 2, BYTE, NONE),
    JAB(0x4, 0x6, 2, BYTE, NONE),
    JLT(0x4, 0x7, 2, BYTE, NONE),
    JLE(0x4, 0x8, 2, BYTE, NONE),
    JGT(0x4, 0x9, 2, BYTE, NONE),
    JGE(0x4, 0xA, 2, BYTE, NONE),
    JBT(0x4, 0xB, 2, BYTE, NONE),
    JNBT(0x4, 0xC, 2, BYTE, NONE),

    LOOP(0x5, -0x1, 2, REGISTER, BYTE),

    LD(0x6, -0x1, 2, REGISTER, REGISTER),
    STR(0x7, -0x1, 2, REGISTER, REGISTER),

    PUSH(0x8, -0x1, 2, REGISTER, NONE),
    POP(0x9, -0x1, 2, REGISTER, NONE),

    CALL(0xA, -0x1, 4, WORD, NONE),
    RET(0xB, -0x1, 2, NONE, NONE),

    ADD(0xC, 0x0, 2, REGISTER, REGISTER),
    ADDC(0xC, 0x1, 2, REGISTER, REGISTER),

    SUB(0xC, 0x2, 2, REGISTER, REGISTER),
    SUBB(0xC, 0x3, 2, REGISTER, REGISTER),

    AND(0xC, 0x4, 2, REGISTER, REGISTER),
    OR(0xC, 0x5, 2, REGISTER, REGISTER),
    XOR(0xC, 0x6, 2, REGISTER, REGISTER),
    NOT(0xC, 0x7, 2, REGISTER, REGISTER),
    BIT(0xC, 0x8, 2, REGISTER, REGISTER),

    CMP(0xC, 0x9, 2, REGISTER, REGISTER),
    TST(0xC, 0xA, 2, REGISTER, REGISTER),

    INCR(0xD, -0x1, 2, REGISTER, BYTE);

    int op;
    int mod;
    int size;
    ParamInfo left;
    ParamInfo right;
    boolean modifier;

    InstructionInfo(int op, int mod, int size, ParamInfo left, ParamInfo right) {
        this.op = op;
        this.mod = mod;
        this.left = left;
        this.right = right;
        this.size = size;

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

    public int size() {
        return size;
    }

    public int paramCount() {
        int count = 0;
        if (left != NONE) {
            count++;
        }
        if (right != NONE) {
            count++;
        }
        return count;
    }

    public static InstructionInfo fromString(String s) {
        for (InstructionInfo inst : values()) {
            if (inst.toString().toLowerCase().equals(s.toLowerCase())) {
                return inst;
            }
        }
        throw new IllegalArgumentException(s + " is not an instruction");
    }
}
