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
    private Map<String, ParsedInstruction> symbols;

    public TextSection(List<ParsedInstruction> instructions) {
        this.instructions = instructions;
    }

    public List<ParsedInstruction> getInstructions() {
        return instructions;
    }
}
