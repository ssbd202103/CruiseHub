package pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.cruises;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublishCruiseDto {
    private long cruiseVersion;
    private UUID cruiseUuid;
}