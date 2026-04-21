package org.training.dto.player;

public class UpdatePlayerDTO {

    private String username;

    private String email;

    private Integer eloRating;

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Integer getEloRating() {
        return eloRating;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEloRating(Integer eloRating) {
        this.eloRating = eloRating;
    }
}
