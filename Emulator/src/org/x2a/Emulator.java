package org.x2a;

import org.x2a.execution.ExecutionUnit;
import org.x2a.instruction.InstructionProperties;

import java.io.IOException;
import java.util.List;

/**
 * Created by Ethan on 6/24/2017.
 */
public class Emulator {


    public static void main(String[] args) throws IOException {
        ExecutionUnit eu = new ExecutionUnit(50000000);

        System.out.println(InstructionProperties.getOpCode("main.nop"));
    }
}
