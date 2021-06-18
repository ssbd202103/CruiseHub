package pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.cruises;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.security.EntityIdentitySignerVerifier;
import pl.lodz.p.it.ssbd2021.ssbd03.security.SignableEntity;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class CruiseForCruiseGroupDto implements SignableEntity {
    private UUID uuid;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean active;
    private boolean published;
    private long version;
    private String etag;

    public CruiseForCruiseGroupDto(UUID uuid, LocalDateTime startDate, LocalDateTime endDate,
                                   boolean active, boolean published, long version) throws BaseAppException {
        this.uuid = uuid;
        this.startDate = startDate;
        this.endDate = endDate;
        this.active = active;
        this.published = published;
        this.version = version;
        this.etag = EntityIdentitySignerVerifier.calculateEntitySignature(this);
    }

    @Override
    @JsonIgnore
    public String getSignablePayload() {
        return uuid + "." + version;
    }
}