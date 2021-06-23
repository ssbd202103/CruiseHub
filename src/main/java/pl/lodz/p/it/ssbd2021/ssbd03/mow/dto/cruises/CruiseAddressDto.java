package pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.cruises;

import lombok.*;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.*;

import javax.validation.constraints.Positive;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CruiseAddressDto {
    @Street
    private String street;

    @StreetNumber
    private String streetNumber;

    @HarborName
    private String harborName;

    @City
    private String cityName;

    @Country
    private String countryName;
}
