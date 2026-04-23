package org.training.dto.team;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class TeamResponse {

    private Long id;

    private String name;

    private String abbreviation;

    private LocalDate foundedAt;

}
