package pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.reservations;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.ssbd2021.ssbd03.security.SignableEntity;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateReservationDto {
    private long cruiseVersion;
    private UUID cruiseUuid;
    private long numberOfSeats;
    private List<String> attractionsUUID;
}