package org.x2a;

import org.x2a.execution.ExecutionUnit;
import org.x2a.instruction.InstructionMod;
import org.x2a.instruction.InstructionType;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

/**
 * Created by Ethan on 6/24/2017.
 */
public class Emulator {


    public static void main(String[] args) throws IOException, InvocationTargetException, IllegalAccessException {
        int inst = (InstructionType.MOV.opcode() << 28);
        inst += (InstructionMod.COND_EQ.opcode() << 24);
        System.out.println(inst);

        ExecutionUnit eu = new ExecutionUnit(50000000);
        eu.decode(inst);
    }
}
