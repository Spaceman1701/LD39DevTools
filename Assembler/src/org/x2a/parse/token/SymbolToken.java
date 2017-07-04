package org.x2a.parse.token;

/**
 * Created by ethan on 7/3/17.
 */
public class SymbolToken implements Token{
    private static final Type TYPE = Type.SYMBOL;

    private final String symbol;

    public SymbolToken(String symbol) {
        this.symbol = symbol;
    }


    @Override
    public String getValue() {
        return symbol;
    }

    @Override
    public Type getType() {
        return TYPE;
    }

    @Override
    public String toString() {
        return "SYM:" + symbol;
    }
}
