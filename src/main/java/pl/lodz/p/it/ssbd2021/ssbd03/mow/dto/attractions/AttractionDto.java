package pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.attractions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.security.EntityIdentitySignerVerifier;
import pl.lodz.p.it.ssbd2021.ssbd03.security.SignableEntity;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.*;

@Data
@Getter
@Setter
@NoArgsConstructor
public class AttractionDto implements SignableEntity {
    @NotEmpty(message = CONSTRAINT_NOT_EMPTY)
    private String uuid;

    @NotEmpty(message = CONSTRAINT_NOT_EMPTY)
    private String name;

    @NotEmpty(message = CONSTRAINT_NOT_EMPTY)
    private String description;

    @Positive(message = CONSTRAINT_POSITIVE)
    private double price;

    @Positive(message = CONSTRAINT_POSITIVE)
    private double numberOfSeats;

    @PositiveOrZero(message = CONSTRAINT_POSITIVE_OR_ZERO)
    private long version;

    @NotEmpty(message = CONSTRAINT_NOT_EMPTY)
    private String etag;

    public AttractionDto(String uuid, String name, String description, double price, double numberOfSeats, long version) throws BaseAppException {
        this.uuid = uuid;
        this.name = name;
        this.description = description;
        this.price = price;
        this.numberOfSeats = numberOfSeats;
        this.version = version;
        this.etag = EntityIdentitySignerVerifier.calculateEntitySignature(this);
    }

    @Override
    @JsonIgnore
    public String getSignablePayload() {
        return uuid + "." + version;
    }
}
