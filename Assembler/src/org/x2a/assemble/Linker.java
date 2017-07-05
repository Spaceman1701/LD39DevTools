package org.x2a.assemble;

import org.x2a.parse.ParsedInstruction;
import org.x2a.parse.ParsedObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ethan on 7/4/17.
 */
public class Linker {
    private final List<ParsedObject> objects;

    public Linker(List<ParsedObject> objects) {
        this.objects = objects;
    }

    public byte[] assemble() throws LinkException {
        Map<ParsedObject, Location> locationMap = locateObjects();
    }


    private void resolveLocalSymbols(ParsedObject object, Location location) {

    }

    private Map<String, Integer> resolveGlobalSymbols(Map<ParsedObject, Location> locationMap) {
        Map<String, Integer> symbolTable = new HashMap<>();
        for (ParsedObject object : objects) {
            Map<String, ParsedInstruction> exports = object.getExportedSymbols();
            for (String sym : exports.keySet()) {
                int location = object.getRelativeLocation(exports.get(sym)) + locationMap.get(object).getStart();
                symbolTable.put(sym, location);
            }
        }
        return symbolTable;
    }

    private Map<ParsedObject, Location> locateObjects() throws LinkException{
        Map<ParsedObject, Location> locationMap = new HashMap<>();
        ParsedObject startObj = null;
        for (ParsedObject object : objects) {
            if (!object.getInfoSection().getAutoOffset() && object.getInfoSection().getOffset() == 0) {
                startObj = object;
            }
        }
        if (startObj == null) {
            throw new LinkException("no starting obj found");
        }

        locationMap.put(startObj, new Location(0, startObj.calculuateSize()));
        int rollingLocation = startObj.calculuateSize();
        for (ParsedObject object : objects) {
            if (!object.equals(startObj)) {
                Location location = new Location(rollingLocation, object.calculuateSize());
                rollingLocation = location.getEnd();
                locationMap.put(object, location);
            }
        }
    }
}
