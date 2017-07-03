package org.x2a;

import org.x2a.execution.DecodeUtils;
import org.x2a.execution.ExecutionUnit;
import org.x2a.instruction.EncodeUtils;
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
        Random random = new Random();
        random.nextInt(16);

        ExecutionUnit eu = new ExecutionUnit(50000000);

        int mov = EncodeUtils.CType.encode(InstructionType.MOV.opcode(), InstructionMod.COND_NOP.opcode(), (byte)0, (byte)4);
        int jmp = EncodeUtils.CType.encode(InstructionType.MOV.opcode(), InstructionMod.COND_NOP.opcode(), ExecutionUnit.IP, (byte)0);

        insert(mov, eu, 0);
        insert(jmp, eu, 2);


        long start = System.currentTimeMillis();
        for (int i = 0; i < 40000000; i++) {
            eu.step();
        }
        long time = System.currentTimeMillis() - start;
        System.out.println("time: " + time);
    }

    private static void insert(int inst, ExecutionUnit eu, int index) {
        byte byte0 = (byte) ((inst >>> 24 ) & DecodeUtils.BYTE_0_MASK);
        byte byte1 = (byte) ((inst >>> 16) & DecodeUtils.BYTE_0_MASK);

        eu.memory[index + 0] = byte0;
        eu.memory[index + 1] = byte1;
    }
}
