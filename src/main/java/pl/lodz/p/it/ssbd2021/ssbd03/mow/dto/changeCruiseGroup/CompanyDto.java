package pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.changeCruiseGroup;

import lombok.*;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.City;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Country;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.PostCode;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Street;

import javax.persistence.Column;
import javax.validation.constraints.Positive;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_POSITIVE_ERROR;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDto {
    private String name;
    private String phoneNumber;
    private long NIP;
    private long houseNumber;
    private String street;
    private String postalCode;
    private String city;
    private String country;
}
