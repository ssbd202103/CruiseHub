package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.PhoneNumber;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OtherClientChangeDataDto extends OtherAccountChangeDataDto {
    @NotNull
    private OtherAddressChangeDto newAddress;
    @PhoneNumber
    private String newPhoneNumber;

    public OtherClientChangeDataDto(String login, Long version, String newFirstName, String newSecondName, String newPhoneNumber, OtherAddressChangeDto newAddress, String alteredBy) {
        super(login, version, newFirstName, newSecondName, alteredBy);
        this.newPhoneNumber = newPhoneNumber;
        this.newAddress = newAddress;
    }
}