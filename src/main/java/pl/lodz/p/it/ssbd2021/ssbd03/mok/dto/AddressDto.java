package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.City;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Country;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.PostCode;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Street;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {
    @Positive
    private Long houseNumber;
    @NotNull
    @NotEmpty
    @Street
    private String street;

    @NotNull
    @NotEmpty
    @PostCode
    private String postalCode;

    @NotNull
    @NotEmpty
    @City
    private String city;

    @NotNull
    @NotEmpty
    @Country
    private String country;
}
