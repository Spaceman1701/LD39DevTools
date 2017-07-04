package org.x2a.parse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ethan on 7/3/17.
 */
public class ParseErrors {
    public static class Error {
        private final int level;
        private final String message;
        private final int lineNumber;

        public Error(int level, String message, int lineNumber) {
            this.level = level;
            this.message = message;
            this.lineNumber = lineNumber;
        }

        public String getMessage() {
            return message;
        }

        public int getLineNumber() {
            return lineNumber;
        }
    }

    private List<Error> errors;

    public ParseErrors() {
        errors = new ArrayList<>();
    }

    public void add(Error error) {
        this.errors.add(error);
    }

    public List<Error> getErrors() {
        return errors;
    }

    public int size() {
        return errors.size();
    }

}
