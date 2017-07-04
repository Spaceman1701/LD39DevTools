package org.x2a.parse.token;

import java.io.Serializable;

/**
 * Created by ethan on 7/3/17.
 */
public interface Token extends Serializable{
    enum Type {
        VALUE, SYMBOL
    }

    Object getValue();

    public Type getType();
}
