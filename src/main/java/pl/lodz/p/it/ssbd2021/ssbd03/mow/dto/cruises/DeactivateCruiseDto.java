package pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.cruises;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.ssbd2021.ssbd03.security.SignableEntity;

import javax.validation.constraints.PositiveOrZero;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DeactivateCruiseDto implements SignableEntity {

    private UUID uuid;
    @PositiveOrZero
    private Long version;


    @JsonIgnore
    @Override
    public String getSignablePayload() {
        return uuid + "." + version;
    }
}
