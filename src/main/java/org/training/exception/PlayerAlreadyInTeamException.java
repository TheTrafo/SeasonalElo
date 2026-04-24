package org.training.exception;

public class PlayerAlreadyInTeamException extends RuntimeException {

    public PlayerAlreadyInTeamException(String message) {
        super(message);
    }

}
