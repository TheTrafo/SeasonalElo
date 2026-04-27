package org.training.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    private Double eloRating = 1000.0;

    @CreationTimestamp
    @Setter(AccessLevel.NONE)
    private LocalDate registeredAt;

    public Player(Long id, String username, String email, Double eloRating, LocalDate registeredAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.eloRating = eloRating;
        this.registeredAt = registeredAt;
    }

    public Player(String username, String email) {
        this.username = username;
        this.email = email;
    }

}
