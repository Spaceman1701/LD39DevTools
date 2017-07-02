package org.x2a.instruction;

import static org.x2a.execution.DecodeUtils.*;

/**
 * Created by ethan on 7/1/17.
 */
public class EncodeUtils {

    private static int setOpCode(byte op) {
        return (op << NIBBLE_BIT_SIZE * 7);
    }

    private static int setOpAndMod(byte op, byte mod) {
        return (op << NIBBLE_BIT_SIZE * 7) + (mod << NIBBLE_BIT_SIZE * 6);
    }

    public static final class AType {
        private AType() {}

        public static int encode(byte op, byte destReg, short data) {
            int inst = setOpCode(op);
            inst += (destReg << NIBBLE_BIT_SIZE * 5);
            inst += data;
            return inst;
        }
    }

    public static final class BType {
        private BType() {}

        public static int encode(byte op, byte destReg, byte data) {
            int inst = setOpCode(op);

            byte upper = (byte) ((data & NIBBLE_1_MASK) >>> NIBBLE_BIT_SIZE);
            byte lower = (byte) (data & NIBBLE_0_MASK);
            inst += (upper << NIBBLE_BIT_SIZE * 6);
            inst += (destReg << NIBBLE_BIT_SIZE * 5);
            inst += (lower << NIBBLE_BIT_SIZE * 4);

            return inst;
        }
    }

    public static final class CType {
        private CType() {}

        public static int encode(byte op, byte cond, byte dest, byte src) {
            int inst = setOpAndMod(op, cond);
            inst += dest << NIBBLE_BIT_SIZE * 5;
            inst += src << NIBBLE_BIT_SIZE * 4;

            return inst;
        }
    }

    public static final class DType {
        private DType() {}

        public static int encode(byte op, byte cond, byte val) {
            int inst = setOpAndMod(op, cond);
            inst += val << NIBBLE_BIT_SIZE * 4;

            return inst;
        }
    }

    public static final class EType {
        private EType() {}

        public static int encode(byte op, byte ptrReg, byte reg) {
            int inst = setOpCode(op);
            inst += reg << NIBBLE_BIT_SIZE * 5;
            inst += ptrReg << NIBBLE_BIT_SIZE * 4;

            return inst;
        }
    }

    public static final class FType {
        private FType() {}

        public static int encode(byte op, byte reg) {
            int inst = setOpCode(op);
            inst += reg << NIBBLE_BIT_SIZE * 5;

            return inst;
        }
    }

    public static final class GType {
        private GType() {}

        public static int encode(byte op, short value) {
            int inst = setOpCode(op);
            inst += value;

            return inst;
        }
    }

    public static final class HType {
        private HType() {}

        public static int encode(byte op) {
            return setOpCode(op);
        }
    }
}
