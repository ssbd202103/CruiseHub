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

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class changeCruiseGroupDto implements SignableEntity {

    private UUID uuid;

    @CruiseGroupName
    private String name;

    @Positive
    private long numberOfSeats;

    @Positive
    private Double price;

    @Valid
    private CruiseAddressDto cruiseAddress;

    private CruisePictureDto picture;
    @Name
    private String description;
    @PositiveOrZero
    private long version;

    @JsonIgnore
    @Override
    public String getSignablePayload() { return uuid + "." + version; }
}
