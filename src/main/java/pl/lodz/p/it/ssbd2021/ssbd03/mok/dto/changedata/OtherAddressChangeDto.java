package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.*;

import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OtherAddressChangeDto {
    @Positive
    private Long newHouseNumber;
    @Street
    private String newStreet;
    @PostCode
    private String newPostalCode;
    @City
    private String newCity;
    @Country
    private String newCountry;
    @Name
    private String alteredBy; //todo needs to be deleted
}