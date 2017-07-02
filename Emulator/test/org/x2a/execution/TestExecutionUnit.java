package org.x2a.execution;

import org.junit.Assert;
import org.junit.Test;
import org.x2a.instruction.EncodeUtils;
import org.x2a.instruction.Instruction;
import org.x2a.instruction.InstructionMod;
import org.x2a.instruction.InstructionType;
import sun.jvm.hotspot.debugger.remote.x86.RemoteX86Thread;

/**
 * Created by ethan on 7/1/17.
 */
public class TestExecutionUnit {

    @Test
    public void testAdd() {
        int inst = EncodeUtils.CType.encode(InstructionType.ALU.opcode(),
                InstructionMod.ALU_ADD.opcode(), ExecutionUnit.R2, ExecutionUnit.R1);

        ExecutionUnit eu = new ExecutionUnit(0);
        eu.reg[ExecutionUnit.R2] = 22;
        eu.reg[ExecutionUnit.R1] = 20;
        eu.add(inst);

        Assert.assertEquals(42, eu.reg[ExecutionUnit.R2]);
    }

    @Test
    public void testAddc() {
        int inst = EncodeUtils.CType.encode(InstructionType.ALU.opcode(),
                InstructionMod.ALU_ADDC.opcode(), ExecutionUnit.R2, ExecutionUnit.R1);
        int add = EncodeUtils.CType.encode(InstructionType.ALU.opcode(),
                InstructionMod.ALU_ADD.opcode(), ExecutionUnit.R3, ExecutionUnit.R4);


        ExecutionUnit eu = new ExecutionUnit(0);
        eu.reg[ExecutionUnit.R3] = (short)0xFFFF;
        eu.reg[ExecutionUnit.R4] = (short)0xFFFF;
        eu.add(add);

        Assert.assertTrue(StatusCodes.isCarry(eu.reg[ExecutionUnit.STATUS]));

        eu.reg[ExecutionUnit.R2] = 22;
        eu.reg[ExecutionUnit.R1] = 20;
        eu.addc(inst);

        Assert.assertEquals(43, eu.reg[ExecutionUnit.R2]);
        Assert.assertTrue((eu.reg[ExecutionUnit.STATUS] & StatusCodes.CARRY) == 0);
    }

    @Test
    public void testSub() {
        int inst = EncodeUtils.CType.encode(InstructionType.ALU.opcode(),
                InstructionMod.ALU_SUB.opcode(), ExecutionUnit.R3, ExecutionUnit.R6);

        ExecutionUnit eu = new ExecutionUnit(0);
        eu.reg[ExecutionUnit.R3] = 22;
        eu.reg[ExecutionUnit.R6] = 20;
        eu.sub(inst);

        Assert.assertEquals(2, eu.reg[ExecutionUnit.R3]);
    }

    @Test
    public void testSubb() {
        int inst = EncodeUtils.CType.encode(InstructionType.ALU.opcode(),
                InstructionMod.ALU_SUB.opcode(), ExecutionUnit.R3, ExecutionUnit.R6);

        ExecutionUnit eu = new ExecutionUnit(0);
        eu.reg[ExecutionUnit.R3] = 22;
        eu.reg[ExecutionUnit.R6] = 23;
        eu.sub(inst);

        Assert.assertTrue(StatusCodes.isCarry(eu.reg[ExecutionUnit.STATUS]));

        int subb = EncodeUtils.CType.encode(InstructionType.ALU.opcode(),
                InstructionMod.ALU_SUB.opcode(), ExecutionUnit.R3, ExecutionUnit.R6);
        eu.reg[ExecutionUnit.R6] = 0;
        eu.subb(subb);

        Assert.assertEquals(-2, eu.reg[ExecutionUnit.R3]);
    }

    @Test
    public void testAnd() {
        int and = EncodeUtils.CType.encode(InstructionType.ALU.opcode(),
                InstructionMod.ALU_AND.opcode(), ExecutionUnit.R0, ExecutionUnit.R1);

        ExecutionUnit eu = new ExecutionUnit(0);
        eu.reg[ExecutionUnit.R0] = 987;
        eu.reg[ExecutionUnit.R1] = 42;

        eu.and(and);

        Assert.assertEquals(987 & 42, eu.reg[ExecutionUnit.R0]);
    }

    @Test
    public void testOr() {
        int or = EncodeUtils.CType.encode(InstructionType.ALU.opcode(),
                InstructionMod.ALU_OR.opcode(), ExecutionUnit.R0, ExecutionUnit.R1);

        ExecutionUnit eu = new ExecutionUnit(0);
        eu.reg[ExecutionUnit.R0] = 987;
        eu.reg[ExecutionUnit.R1] = 42;

        eu.or(or);

        Assert.assertEquals(987 | 42, eu.reg[ExecutionUnit.R0]);
    }

    @Test
    public void testXOr() {
        int or = EncodeUtils.CType.encode(InstructionType.ALU.opcode(),
                InstructionMod.ALU_XOR.opcode(), ExecutionUnit.R0, ExecutionUnit.R1);

        ExecutionUnit eu = new ExecutionUnit(0);
        eu.reg[ExecutionUnit.R0] = 213;
        eu.reg[ExecutionUnit.R1] = -6534;

        eu.xor(or);

        Assert.assertEquals(213 ^ -6534, eu.reg[ExecutionUnit.R0]);
    }

    @Test
    public void testNot() {
        int not = EncodeUtils.CType.encode(InstructionType.ALU.opcode(),
                InstructionMod.ALU_NOT.opcode(), ExecutionUnit.R0, (byte)6);
        ExecutionUnit eu = new ExecutionUnit(0);
        eu.reg[ExecutionUnit.R0] = 213;

        eu.not(not);

        Assert.assertEquals(~213, eu.reg[ExecutionUnit.R0]);
    }

    @Test
    public void testBit() {
        int bit = EncodeUtils.CType.encode(InstructionType.ALU.opcode(),
                InstructionMod.ALU_BIT.opcode(), ExecutionUnit.R0, ExecutionUnit.R1);

        ExecutionUnit eu = new ExecutionUnit(0);
        eu.reg[ExecutionUnit.R0] = 2;
        eu.reg[ExecutionUnit.R1] = 1;

        eu.bit(bit);

        Assert.assertTrue(StatusCodes.isBit(eu.reg[ExecutionUnit.STATUS]));
    }

    @Test
    public void testMov() {
        int inst = EncodeUtils.CType.encode(InstructionType.MOV.opcode(), InstructionMod.COND_NOP.opcode(), (byte)0, (byte)4);

        ExecutionUnit eu = new ExecutionUnit(0);
        eu.reg[4] = 42;
        eu.mov(inst);

        Assert.assertEquals(42, eu.reg[0]);
    }
}
