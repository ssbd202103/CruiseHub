package pl.lodz.p.it.ssbd2021.ssbd03.mow.dto;

import lombok.*;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.City;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Country;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Name;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Street;

import javax.validation.constraints.Positive;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CruiseAddressDto {
    @Street
    private String street;
    @Positive
    private Integer streetNumber;
    @Name
    private String harborName;
    @City
    private String cityName;
    @Country
    private String countryName;

}
