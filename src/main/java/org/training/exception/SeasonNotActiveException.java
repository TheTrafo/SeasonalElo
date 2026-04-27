package org.training.exception;

public class SeasonNotActiveException extends RuntimeException {
    public SeasonNotActiveException(String message) {
        super(message);
    }
}
