package pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.reservations;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RemoveClientReservationDto {
    private long reservationVersion;
    private UUID reservationUuid;
    private String clientLogin;
}
