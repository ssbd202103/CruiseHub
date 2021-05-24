package pl.lodz.p.it.ssbd2021.ssbd03.mow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateReservationDto {
    private long reservationVersion;
    private UUID cruiseUuid;
    private long numberOfSeats;
    private String clientLogin;
}