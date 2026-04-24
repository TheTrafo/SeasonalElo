package org.training.exception;

public class PlayerNotPartOfTeam extends RuntimeException {
    public PlayerNotPartOfTeam(String message) {
        super(message);
    }
}
