package org.x2a.parse;

import org.x2a.instruction.InstructionInfo;
import org.x2a.parse.token.Token;

import java.io.Serializable;

/**
 * Created by ethan on 7/2/17.
 */
public class ParsedInstruction implements Serializable{
    private final InstructionInfo info;
    private final Token left;
    private final Token right;

    public ParsedInstruction(InstructionInfo info, Token left, Token right) {
        this.info = info;
        this.left = left;
        this.right = right;
    }

    public Token getRight() {
        return right;
    }

    public Token getLeft() {
        return left;
    }

    public InstructionInfo getInfo() {
        return info;
    }

    public int getSize() {
        return info.size();
    }

    @Override
    public String toString() {
        String leftstr = "";
        String rightstr = "";
        if (left != null) {
            leftstr = left.toString();
        }
        if (right != null) {
            rightstr = right.toString();
        }
        return info.toString() + " " + leftstr + " " + rightstr;
    }
}
