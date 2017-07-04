package org.x2a.parse;

import org.x2a.parse.section.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ethan on 7/2/17.
 */
public class ObjectParser {
    private final String[] objectCode;

    public ObjectParser(String objectCode) {
        this.objectCode = processLines(objectCode);
    }

    private String[] processLines(String data) {
        String[] lines = data.split("\n");
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            if (line.contains("//")) {
                lines[i] = line.trim().substring(line.indexOf("//") + 1, line.length() - 1);
            } else {
                lines[i] = line.trim();
            }

        }
        return lines;
    }


    public ParsedObject produceObject(ParseErrors errors) { //TODO: There is no reason for this to be so shitty -Ethan
        List<Section> sections = new ArrayList<>();
        SectionParser ctxParser = null;
        for (int i = 0; i < objectCode.length; i++) {
            String line = objectCode[i];
            if (line.startsWith(".")) {
                if (ctxParser != null) {
                    sections.add(ctxParser.buildSection());
                }
                ctxParser = createParser(line);
            } else {
                try {
                    ctxParser.parseLine(i, objectCode);
                } catch (Exception e) {
                    e.printStackTrace();
                    errors.add(new ParseErrors.Error(0, e.getMessage(), i));
                }
            }
        }
        sections.add(ctxParser.buildSection());

        InfoSection infoSection = null;
        DataSection dataSection = null;
        TextSection textSection = null;

        for (Section s : sections) {
            if (s instanceof InfoSection) {
                infoSection = (InfoSection) s;
            } else if (s instanceof DataSection) {
                dataSection = (DataSection) s;
            } else if (s instanceof TextSection) {
                textSection = (TextSection) s;
            }
        }
        if (infoSection == null | dataSection == null | textSection == null || sections.size() != 3) {
            errors.add(new ParseErrors.Error(0, "Incorrect number of sections!", 0));
        }

        return new ParsedObject(infoSection, dataSection, textSection);
    }

    private SectionParser createParser(String line) {
        switch (line) {
            case ".info":
                return new InfoSectionParser();
            case ".data":
                return new DataSectionParser();
            case ".text":
                return new TextSectionParser();
            default:
                throw new IllegalArgumentException(line + " does not define a section");
        }
    }


}
