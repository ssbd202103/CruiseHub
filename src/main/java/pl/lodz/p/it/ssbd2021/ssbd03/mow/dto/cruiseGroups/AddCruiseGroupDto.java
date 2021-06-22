package pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.cruiseGroups;

import lombok.*;
import pl.lodz.p.it.ssbd2021.ssbd03.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.cruises.CruiseAddressDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.cruises.CruisePictureDto;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.CompanyName;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_NOT_EMPTY;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_POSITIVE;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddCruiseGroupDto {
    @CompanyName
    private String companyName;

    @NotEmpty(message = CONSTRAINT_NOT_EMPTY)
    private String name;

    @Positive(message = CONSTRAINT_POSITIVE)
    private long numberOfSeats;

    @Positive(message = CONSTRAINT_POSITIVE)
    private double price;

    @Valid
    private CruiseAddressDto cruiseAddress;

    @NotEmpty(message = CONSTRAINT_NOT_EMPTY)
    private List<CruisePictureDto> cruisePictures;

    @NotEmpty(message = CONSTRAINT_NOT_EMPTY)
    private String description;


}
