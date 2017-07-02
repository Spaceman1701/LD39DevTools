package org.x2a.instruction;

/**
 * Created by ethan on 7/2/17.
 */
public class InstConstants {

    public static final int NIBBLE_7_MASK = 0xF0000000;
    public static final int NIBBLE_6_MASK = 0x0F000000;

    public static final int NIBBLE_5_MASK = 0x00F00000;
    public static final int NIBBLE_4_MASK = 0x000F0000;

    public static final int NIBBLE_3_MASK = 0x0000F000;
    public static final int NIBBLE_2_MASK = 0x00000F00;

    public static final int NIBBLE_1_MASK = 0x000000F0;
    public static final int NIBBLE_0_MASK = 0x0000000F;


    public static final int BYTE_0_MASK = NIBBLE_0_MASK | NIBBLE_1_MASK;
    public static final int BYTE_1_MASK = NIBBLE_2_MASK | NIBBLE_3_MASK;

    public static final int BYTE_2_MASK = NIBBLE_4_MASK | NIBBLE_5_MASK;
    public static final int BYTE_3_MASK = NIBBLE_6_MASK | NIBBLE_7_MASK;


    public static final int WORD_0_MASK = BYTE_0_MASK | BYTE_1_MASK;
    public static final int WORD_1_MASK = BYTE_2_MASK | BYTE_3_MASK;


    public static final int NIBBLE_BIT_SIZE = 4;
    public static final int BYTE_BIT_SIZE = 8;
    public static final int WORD_BIT_SIZE = 16;


    public static final int WORD_SIZE = 2;


    public static final int OPCODE = NIBBLE_7_MASK;
    public static final int MODCODE = NIBBLE_6_MASK;

}
