package pl.lodz.p.it.ssbd2021.ssbd03.mow.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RelatedCruiseDto {
    UUID uuid;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean active;
}
