package pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.cruises;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShortCruiseDto {

    private UUID uuid;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
