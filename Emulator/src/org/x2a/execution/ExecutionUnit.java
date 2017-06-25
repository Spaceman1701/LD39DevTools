package org.x2a.execution;

import java.util.Stack;

/**
 * Created by Ethan on 6/25/2017.
 */
public class ExecutionUnit {

    private short[] memory = new short[0xFFFF];
    private short[] registers = new short[0xF];

    private Stack<Object> stackTrace; //for debug

    private long cyclesPerSecond;

    public ExecutionUnit(long cyclesPerSecond) {
        this.cyclesPerSecond = cyclesPerSecond;
    }
}
