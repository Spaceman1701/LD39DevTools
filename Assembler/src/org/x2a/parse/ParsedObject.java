package org.x2a.parse;

import org.x2a.instruction.InstructionInfo;
import org.x2a.parse.section.DataSection;
import org.x2a.parse.section.InfoSection;
import org.x2a.parse.section.TextSection;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ethan on 7/3/17.
 */
public class ParsedObject implements Serializable{
    private InfoSection infoSection;
    private DataSection dataSection;
    private TextSection textSection;

    private Map<String, ParsedInstruction> exportedSymbols;
    private Map<String, ParsedInstruction> localSymbols;

    private Map<String, int[]> exportedDataSymbols;
    private Map<String, int[]> localDataSymbols;

    public ParsedObject(InfoSection infoSection, DataSection dataSection, TextSection textSection) throws SyntaxException {
        this.infoSection = infoSection;
        this.dataSection = dataSection;
        this.textSection = textSection;
        exportedSymbols = new HashMap<>();
        localSymbols = new HashMap<>();
        exportedDataSymbols = new HashMap<>();
        localDataSymbols = new HashMap<>();
        constructSymbolTables();
    }

    private void constructSymbolTables() throws SyntaxException { //TODO: might not catch multiple definitions correctly
        List<String> exports = infoSection.getExports();
        Map<String, ParsedInstruction> textTable = textSection.getSymbols();
        for (String sym : textTable.keySet()) {
            if (exports.contains(sym)) {
                if (exportedSymbols.containsKey(sym)) {
                    throw new SyntaxException("Multiple definitions for exported symbol " + sym);
                }
                exportedSymbols.put(sym, textTable.get(sym));
                exports.remove(sym);
            } else {
                if (localSymbols.containsKey(sym)) {
                    throw new SyntaxException("Multiple definitions for local symbol " + sym);
                }
                localSymbols.put(sym, textTable.get(sym));
            }
        }
        Map<String, int[]> data = dataSection.getData();
        for (String sym : data.keySet()) {
            if (exports.contains(sym)) {
                if (exportedDataSymbols.containsKey(sym)) {
                    throw new SyntaxException("Multiple definitions for exported symbol: " + sym);
                }
                exportedDataSymbols.put(sym, data.get(sym));
                exports.remove(sym);
            } else {
                if (localDataSymbols.containsKey(sym)) {
                    throw new SyntaxException("Multiple definitions for local symbol: " + sym);
                }
                localDataSymbols.put(sym, data.get(sym));
            }
        }
        if (exports.size() > 0) {
            throw new SyntaxException("Cannot find definition for " + exports.get(0) + " and " + (exports.size() - 1) + " symbols");
        }
    }

    public int calculuateSize() {
        int size = 0;
        for (ParsedInstruction instruction : textSection.getInstructions()) {
            size += instruction.getSize();
        }
        return size;
    }

    public InfoSection getInfoSection() {
        return infoSection;
    }

    public DataSection getDataSection() {
        return dataSection;
    }

    public TextSection getTextSection() {
        return textSection;
    }
}
