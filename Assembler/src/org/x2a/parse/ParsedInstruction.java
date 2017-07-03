package org.x2a.parse;

import org.x2a.instruction.InstructionInfo;

/**
 * Created by ethan on 7/2/17.
 */
public class ParsedInstruction {
    private final InstructionInfo info;
    private final int left;
    private final int right;

    public ParsedInstruction(InstructionInfo info, int left, int right) {
        this.info = info;
        this.left = left;
        this.right = right;
    }

    public int getRight() {
        return right;
    }

    public int getLeft() {
        return left;
    }

    public InstructionInfo getInfo() {
        return info;
    }
}
