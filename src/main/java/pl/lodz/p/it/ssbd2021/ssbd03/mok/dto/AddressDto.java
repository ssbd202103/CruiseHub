package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {
    @HouseNumber
    private String houseNumber;

    @Street
    private String street;

    @PostCode
    private String postalCode;

    @City
    private String city;

    @Country
    private String country;
}
