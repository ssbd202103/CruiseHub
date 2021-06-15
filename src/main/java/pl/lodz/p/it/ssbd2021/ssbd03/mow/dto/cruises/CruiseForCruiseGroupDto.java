package pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.cruises;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CruiseForCruiseGroupDto {

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean active;
}