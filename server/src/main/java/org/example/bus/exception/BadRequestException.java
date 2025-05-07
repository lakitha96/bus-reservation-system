package org.example.bus.exception;

import java.util.Collections;
import java.util.List;

/**
 * @author lakithaprabudh
 */
public class BadRequestException extends RuntimeException {
    private final List<String> errors;

    public BadRequestException(String message) {
        super(message);
        this.errors = Collections.singletonList(message);
    }

    public BadRequestException(List<String> errors) {
        super("Validation failed");
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}