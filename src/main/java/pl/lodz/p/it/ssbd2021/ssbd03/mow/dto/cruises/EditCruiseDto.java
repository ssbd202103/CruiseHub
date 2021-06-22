package pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.cruises;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.ssbd2021.ssbd03.security.SignableEntity;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditCruiseDto implements SignableEntity {
    private UUID uuid; //todo change UUID to String and handle parsing exception
    private String startDate;
    private String endDate;
    private Long version;

    @JsonIgnore
    @Override
    public String getSignablePayload() {
        return uuid + "." + version;
    }
}
