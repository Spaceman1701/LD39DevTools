package org.x2a.parse.section;

import org.x2a.parse.ParseErrors;
import org.x2a.parse.SyntaxException;

import java.nio.channels.IllegalSelectorException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ethan on 7/3/17.
 */
public class DataSectionParser implements SectionParser{
    private static final Pattern strPattern = Pattern.compile("\"([^\"]*)\""); //what a regex

    private Map<String, int[]> vars;

    public DataSectionParser() {
        vars = new HashMap<>();
    }

    @Override
    public void parseLine(int lineNumber, String[] lines) throws SyntaxException {
        String line = lines[lineNumber];
        if (line.contains("\"")) {
            parseString(line);
        } else if (line.contains("[")) {
            parseEmpty(line);
        } else if (line.length() > 0){
            parseArray(line);
        }
    }

    private void parseArray(String line) throws SyntaxException {
        String[] tokens = line.split(" ");
        if (tokens.length < 2) {
            throw new SyntaxException("expected at least 2 tokens");
        }
        String name = tokens[0];
        if (vars.containsKey(name)) {
            throw new SyntaxException("variable: " + name + " already defined");
        }
        int[] values = new int[tokens.length - 1];
        for (int i = 1; i < tokens.length; i++) {
            values[i - 1] = Integer.parseInt(tokens[i]);
        }
        vars.put(name, values);
    }

    private int[] stringVals(String s) {
        int[] ints = new int[s.length() + 1];
        for (int i = 0; i < s.length(); i++) {
            ints[i] = s.charAt(i);
        }
        ints[s.length()] = 0;
        return ints;
    }

    private void parseEmpty(String line) throws SyntaxException {
        String[] tokens = line.split(" ");
        if (tokens.length != 2) {
            throw new SyntaxException("expected 2 tokens, found: " + tokens.length);
        }
        String val = tokens[1];
        String sizeString = tokens[1].substring(val.indexOf("[") + 1, val.indexOf("]"));
        int size = Integer.parseInt(sizeString);
        if (vars.containsKey(tokens[0])) {
            throw new SyntaxException("variable: " + tokens[0] + " already defined");
        }
        vars.put(tokens[0], new int[size]);
    }

    private void parseString(String line) throws SyntaxException{
        Matcher matcher = strPattern.matcher(line);
        matcher.find();
        String match = matcher.group();
        if (match.length() == 0) {
            throw new SyntaxException("cannot allocate space for an empty string");
        }
        String name = line.substring(0, line.indexOf(' '));
        int[] vals = stringVals(match);
        if (vars.containsKey(name)) {
            throw new SyntaxException("variable: " + name + " already defined");
        }
        vars.put(name, vals);
    }

    @Override
    public Section buildSection() {
        return new DataSection(vars);
    }
}
