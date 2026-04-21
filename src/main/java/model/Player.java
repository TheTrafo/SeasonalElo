package model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Player {

    @Id
    private Long id;

    private String username;

    private String email;

    private Integer eloRating = 1000;

    private LocalDate registeredAt;

    public Player(Long id, String username, String email, Integer eloRating, LocalDate registeredAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.eloRating = eloRating;
        this.registeredAt = registeredAt;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Integer getEloRating() {
        return eloRating;
    }

    public LocalDate getRegisteredAt() {
        return registeredAt;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setRegisteredAt(LocalDate registeredAt) {
        this.registeredAt = registeredAt;
    }

}
