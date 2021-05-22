package pl.lodz.p.it.ssbd2021.ssbd03.mow.dto;


import lombok.*;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.CruiseGroup;

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
    private CruiseGroupDto cruiseGroupDto;
}
