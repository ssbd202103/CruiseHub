package pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.cruiseGroups;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.ssbd2021.ssbd03.security.SignableEntity;

import javax.validation.constraints.PositiveOrZero;

import java.util.UUID;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_POSITIVE_OR_ZERO;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeactivateCruiseGroupDto implements SignableEntity{

    private UUID uuid; //todo change UUID to String and handle parsing exception

    @PositiveOrZero(message = CONSTRAINT_POSITIVE_OR_ZERO)
    private long version;

    @JsonIgnore
    @Override
    public String getSignablePayload() {
        return uuid + "." + version; }

}





