package org.x2a.parse.section;

import org.x2a.parse.ParseErrors;
import org.x2a.parse.SyntaxException;

/**
 * Created by ethan on 7/3/17.
 */
public interface SectionParser {

    void parseLine(int lineNumber, String[] lines) throws SyntaxException;

    Section buildSection();
}
