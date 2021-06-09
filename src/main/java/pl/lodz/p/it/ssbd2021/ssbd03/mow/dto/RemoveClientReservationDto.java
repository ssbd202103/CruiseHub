package pl.lodz.p.it.ssbd2021.ssbd03.mow.dto;

import com.fasterxml.jackson.databind.JavaType;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RemoveClientReservationDto {
    private long reservationVersion;
    private String reservationUuid;
    private String clientLogin;
}
