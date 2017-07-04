package org.x2a.parse;

import org.x2a.parse.section.DataSection;
import org.x2a.parse.section.InfoSection;
import org.x2a.parse.section.TextSection;

import java.io.Serializable;

/**
 * Created by ethan on 7/3/17.
 */
public class ParsedObject implements Serializable{
    private InfoSection infoSection;
    private DataSection dataSection;
    private TextSection textSection;


    public ParsedObject(InfoSection infoSection, DataSection dataSection, TextSection textSection) {
        this.infoSection = infoSection;
        this.dataSection = dataSection;
        this.textSection = textSection;
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
