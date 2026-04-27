package org.training.dto.player;

import lombok.Data;

@Data
public class PlayerUpdateRequest {

    private String username;

    private String email;

    private Double eloRating;

}
