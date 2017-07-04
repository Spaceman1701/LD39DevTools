package org.x2a.instruction;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ethan on 7/3/17.
 */
public enum Register {
    R0(0),
    R1(1),
    R2(2),
    R3(3),
    R4(4),
    R5(5),
    R6(6),
    R7(7),
    R8(8),
    R9(9),
    R10(10, "bp"),
    R11(11),
    R12(12),
    R13(13, "sp"),
    R14(14),
    R15(15);


    Set<String> names;
    int code;
    Register(int code, String... names) {
        this.code = code;
        this.names = new HashSet<>(Arrays.asList(names));
        this.names.add(toString().toLowerCase());
    }


    public static Register fromName(String n) {
        for (Register r : values()) {
            if (r.names.contains(n.toLowerCase())) {
                return r;
            }
        }
        throw new IllegalArgumentException(n + " is not a register");
    }

    public int code() {
        return code;
    }
}
