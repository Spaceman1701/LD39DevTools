package org.x2a.parse.section;

import org.x2a.instruction.InstructionInfo;
import org.x2a.instruction.ParamInfo;
import org.x2a.instruction.Register;
import org.x2a.parse.ParsedInstruction;
import org.x2a.parse.SyntaxException;
import org.x2a.parse.token.SymbolToken;
import org.x2a.parse.token.Token;
import org.x2a.parse.token.ValueToken;

import java.util.*;

/**
 * Created by ethan on 7/3/17.
 */
public class TextSectionParser implements SectionParser {
    private List<ParsedInstruction> instructions;
    private Map<String, ParsedInstruction> symbols;

    private String hangingSymbol;

    public TextSectionParser() {
        instructions = new ArrayList<>();
        symbols = new HashMap<>();
    }

    @Override
    public void parseLine(int lineNumber, String[] lines) throws SyntaxException {
        String line = lines[lineNumber].replace(", ", ",");
        if (line.length() > 0) {
            if (line.contains(":")) {
                String symbol = line.substring(0, line.indexOf(':'));
                this.hangingSymbol = symbol;
                line = line.substring(line.indexOf(':') + 1, line.length()).trim();
            }
            if (line.length() > 0) {
                String[] tokens = line.split(" ");

                InstructionInfo instructionInfo = InstructionInfo.fromString(tokens[0]);
                int paramCount = instructionInfo.paramCount();
                if (paramCount > 0 && tokens.length < 2) {
                    throw new SyntaxException("expected at least 2 tokens");
                }
                String[] args = null;
                if (paramCount > 0) {
                    args = tokens[1].split(",");
                }
                parseInst(instructionInfo, args);
            }
        }

    }


    private void parseInst(InstructionInfo inst, String[] args) throws SyntaxException {
        if (args == null && inst.paramCount() != 0) {
            throw new SyntaxException(inst.name() + " expected " + inst.paramCount() + " parameters, found: 0");
        }
        if (args != null && inst.paramCount() != args.length) {
            throw new SyntaxException(inst.name() + " expected " + inst.paramCount() + " paramters, found: " + args.length);
        }
        Token left = null;
        Token right = null;
        if (inst.left() == ParamInfo.REGISTER) {
            left = parseRegister(args[0]);
        } else if (inst.left() == ParamInfo.BYTE) {
            left = parseByte(args[0]);
        } else if (inst.left() == ParamInfo.WORD) {
            left = parseWord(args[0]);
        }

        if (inst.right() == ParamInfo.REGISTER) {
            right = parseRegister(args[1]);
        } else if (inst.right() == ParamInfo.BYTE) {
            right = parseByte(args[1]);
        } else if (inst.right() == ParamInfo.WORD) {
            right = parseWord(args[1]);
        }

        ParsedInstruction parsedInstruction = new ParsedInstruction(inst, left, right);
        instructions.add(parsedInstruction);
        if (hangingSymbol != null) {
            symbols.put(hangingSymbol, parsedInstruction);
            hangingSymbol = null;
        }
    }

    private Token parseRegister(String arg) {
        Register register = Register.fromName(arg);
        return new ValueToken(register.code());
    }

    private Token parseByte(String arg) {
        try {
            int val = Integer.parseInt(arg);
            if (val != (val & 0xFF)) {
                throw new IllegalArgumentException(val + " is not a valid byte");
            }
            return new ValueToken(val);
        } catch (NumberFormatException e) { //assume it's a symbol
            return new SymbolToken(arg);
        }
    }

    private Token parseWord(String arg) {
        try {
            int val = Integer.parseInt(arg);
            if (val != (val & 0xFFFF)) {
                throw new IllegalArgumentException(val + " is not a valid byte");
            }
            return new ValueToken(val);
        } catch (NumberFormatException e) { //assume it's a symbol
            return new SymbolToken(arg);
        }
    }

    @Override
    public Section buildSection() {
        return new TextSection(instructions, symbols);
    }
}
