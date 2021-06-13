package pl.lodz.p.it.ssbd2021.ssbd03.mow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.ssbd2021.ssbd03.security.SignableEntity;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublishCruiseDto implements SignableEntity {
    private long cruiseVersion;
    private UUID cruiseUuid;

    @Override
    public String getSignablePayload() {
        return cruiseUuid+"."+cruiseVersion;
    }
}
