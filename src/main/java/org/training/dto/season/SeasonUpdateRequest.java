package org.training.dto.season;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SeasonUpdateRequest {

    private String name;

    private LocalDate startDate;

    private LocalDate endDate;

}
