package pl.lodz.p.it.ssbd2021.ssbd03.mow.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewCruiseDto {

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean active;
    private String description;
    private Boolean available;
    private String cruiseName;
}
