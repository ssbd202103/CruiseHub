package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OtherAddressChangeDto {
    @HouseNumber
    private String newHouseNumber;
    @Street
    private String newStreet;
    @PostCode
    private String newPostalCode;
    @City
    private String newCity;
    @Country
    private String newCountry;
}