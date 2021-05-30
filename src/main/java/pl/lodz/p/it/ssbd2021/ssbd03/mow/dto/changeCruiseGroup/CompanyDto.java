package pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.changeCruiseGroup;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDto {
    private String name;
    private String phoneNumber;
    private long NIP;
    private String houseNumber;
    private String street;
    private String postalCode;
    private String city;
    private String country;
}
