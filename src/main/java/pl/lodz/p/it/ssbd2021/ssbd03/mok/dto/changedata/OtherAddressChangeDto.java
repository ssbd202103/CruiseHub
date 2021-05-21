package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.City;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Country;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.PostCode;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Street;

import javax.validation.constraints.Positive;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_POSITIVE_ERROR;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OtherAddressChangeDto {
    @Positive(message = CONSTRAINT_POSITIVE_ERROR)
    private long newHouseNumber;
    @Street
    private String newStreet;
    @PostCode
    private String newPostalCode;
    @City
    private String newCity;
    @Country
    private String newCountry;
}