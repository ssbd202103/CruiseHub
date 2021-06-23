package pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.cruiseGroups;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.CruisePicture;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.cruises.CruiseAddressDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.cruises.CruisePictureDto;
import pl.lodz.p.it.ssbd2021.ssbd03.security.SignableEntity;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.CruiseGroupName;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Name;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.UUID;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_POSITIVE;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_POSITIVE_OR_ZERO;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class ChangeCruiseGroupDto implements SignableEntity {

    private UUID uuid; //todo change UUID to String and handle parse exception

    @CruiseGroupName
    private String name;

    @Positive
    private long numberOfSeats;

    @Positive
    private Double price;

    @Valid
    private CruiseAddressDto cruiseAddress;

    @Valid
    private CruisePictureDto picture;

    @Name
    private String description;
    @PositiveOrZero(message = CONSTRAINT_POSITIVE_OR_ZERO)
    private long version;

    @JsonIgnore
    @Override
    public String getSignablePayload() { return uuid + "." + version; }
}
