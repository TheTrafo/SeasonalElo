package org.training.exception;

public class SamePlayerException extends RuntimeException {
    public SamePlayerException(String message) {
        super(message);
    }
}
