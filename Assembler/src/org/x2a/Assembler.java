package org.x2a;

import org.apache.commons.io.FileUtils;
import org.x2a.parse.ObjectParser;
import org.x2a.parse.ParseErrors;
import org.x2a.parse.ParsedInstruction;
import org.x2a.parse.ParsedObject;

import java.io.*;

/**
 * Created by ethan on 7/2/17.
 */
public class Assembler {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String asm = FileUtils.readFileToString(new File("example.asm"));
        ObjectParser parser = new ObjectParser(asm);
        ParseErrors errors = new ParseErrors();
        ParsedObject obj = parser.produceObject(errors);

        for (ParseErrors.Error e : errors.getErrors()) {
            System.err.println(e.getLineNumber() + ": " + e.getMessage());
        }

        File file = new File("example.object");
        file.createNewFile();
        try (FileOutputStream fos = new FileOutputStream(file)) {
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(obj);
        }
        ParsedObject loaded = null;
        try (FileInputStream fis = new FileInputStream(file)) {
            ObjectInputStream ois = new ObjectInputStream(fis);
            loaded = (ParsedObject) ois.readObject();
        }

        for (ParsedInstruction inst : loaded.getTextSection().getInstructions()) {
            System.out.println(inst.toString());
        }
    }
}
