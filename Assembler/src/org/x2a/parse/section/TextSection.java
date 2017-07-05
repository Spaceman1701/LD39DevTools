package org.x2a.parse.section;

import org.x2a.parse.ParsedInstruction;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by ethan on 7/3/17.
 */
public class TextSection implements Serializable, Section{
    private List<ParsedInstruction> instructions;
    private final Map<String, ParsedInstruction> symbols;

    public TextSection(List<ParsedInstruction> instructions, Map<String, ParsedInstruction> symbols) {
        this.instructions = instructions;
        this.symbols = symbols;
    }

    public List<ParsedInstruction> getInstructions() {
        return instructions;
    }

    public Map<String, ParsedInstruction> getSymbols() {
        return symbols;
    }
}
