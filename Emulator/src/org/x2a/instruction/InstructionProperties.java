package org.x2a.instruction;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by ethan on 6/26/17.
 */
public class InstructionProperties {
    private static InstructionProperties INSTANCE;

    private Properties properties;

    private InstructionProperties() {
        properties = new Properties();
        try {
            properties.load(this.getClass().getResourceAsStream("/res/opcodes.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte getOpCodeProperty(String key) {
        return Byte.decode(properties.getProperty(key));
    }

    public static byte getOpCode(String name) {
        if (INSTANCE == null) {
            INSTANCE = new InstructionProperties();
        }
        return INSTANCE.getOpCodeProperty(name.toLowerCase());
    }
}
