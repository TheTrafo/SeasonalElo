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
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    private String name;

    @Column(length = 5)
    private String abbreviation;

    @CreationTimestamp
    @Setter(AccessLevel.NONE)
    private LocalDate foundedAt;

    public Team(Long id, String name, String abbreviation, LocalDate foundedAt) {
        this.id = id;
        this.name = name;
        this.abbreviation = abbreviation;
        this.foundedAt = foundedAt;
    }

    public Team(String name, String abbreviation) {
        this.name = name;
        this.abbreviation = abbreviation;
    }

}
