package org.x2a.parse.section;

import org.x2a.parse.*;

/**
 * Created by ethan on 7/3/17.
 */
public class InfoSectionParser implements SectionParser {
    private boolean autoOffset;
    private int offset = Integer.MAX_VALUE;
    private String start;


    @Override
    public void parseLine(int lineNumber, String[] lines) throws SyntaxException{
        String line = lines[lineNumber];
        if (line.startsWith("offset ")) {
            parseOffset(line);
        } else if (line.startsWith("start ")) {
            parseStart(line);
        }
    }

    private void parseStart(String line) throws SyntaxException {
        if (start != null) {
            throw new SyntaxException("start is already set");
        }
        String[] tokens = line.split(" ");
        if (tokens.length != 2) {
            throw new SyntaxException("expected 2 tokens, found:" + tokens.length);
        }
        this.start = tokens[1];
    }

    private void parseOffset(String line) throws SyntaxException { //TODO: THis is confusing to read
        if (offset != Integer.MAX_VALUE) {
            throw new SyntaxException("offset is already set");
        }
        String[] tokens = line.split(" ");
        if (tokens.length != 2) {
            throw new SyntaxException("expected 2 tokens, found:" + tokens.length);
        }

        if (tokens[1].equals("auto")) {
            this.offset = 0;
            this.autoOffset = true;
        } else {
            try {
                int offset = Integer.parseInt(tokens[1]);
                this.offset = offset;
                this.autoOffset = false;
            } catch (NumberFormatException e) {
                throw new SyntaxException("expected number, found: " + tokens[2]);
            }
        }
    }

    @Override
    public
    Section buildSection() {
        return new InfoSection(offset, autoOffset, start);
    }
}
