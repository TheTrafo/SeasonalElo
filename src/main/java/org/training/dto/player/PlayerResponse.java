package org.training.dto.player;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PlayerResponse {

    private Long id;

    private String username;

    private String email;

    private Double eloRating;

    private LocalDate registeredAt;

}
