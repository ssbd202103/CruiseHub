package pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.cruises;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditCruiseDto {
    private UUID uuid;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String description;
    private Long version;
}
