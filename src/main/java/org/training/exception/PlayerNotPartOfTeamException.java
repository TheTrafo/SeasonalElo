package org.training.exception;

public class PlayerNotPartOfTeamException extends RuntimeException {
    public PlayerNotPartOfTeamException(String message) {
        super(message);
    }
}
