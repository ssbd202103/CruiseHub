package pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.reservations;

import com.fasterxml.jackson.databind.JavaType;
import lombok.*;
import pl.lodz.p.it.ssbd2021.ssbd03.security.SignableEntity;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RemoveClientReservationDto {
    private String reservationUuid;
    private String clientLogin;
}
