package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto;

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
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {
    @Positive(message = CONSTRAINT_POSITIVE_ERROR)
    private Long houseNumber;

    @Street
    private String street;

    @PostCode
    private String postalCode;

    @City
    private String city;

    @Country
    private String country;
}
