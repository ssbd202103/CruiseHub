package pl.lodz.p.it.ssbd2021.ssbd03.mow.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.index.qual.Positive;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.CruiseGroup;
import pl.lodz.p.it.ssbd2021.ssbd03.security.SignableEntity;

import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
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
