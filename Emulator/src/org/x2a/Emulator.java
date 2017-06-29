package org.x2a;

import org.x2a.execution.ExecutionUnit;
import org.x2a.instruction.InstructionMod;
import org.x2a.instruction.InstructionType;

import java.io.IOException;
import java.util.Random;

/**
 * Created by Ethan on 6/24/2017.
 */
public class Emulator {


    public static void main(String[] args) throws IOException {
        char a = 0x00FF;
        char b = 0x0FFF;
        char c = (char) (a + b);
        System.out.println(c < a);
    }
}
