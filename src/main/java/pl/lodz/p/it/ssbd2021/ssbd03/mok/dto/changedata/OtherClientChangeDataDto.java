package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AbstractAccountDto;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.PhoneNumber;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OtherClientChangeDataDto extends AbstractAccountDto {
    @NotNull
    private OtherAddressChangeDto newAddress;
    @PhoneNumber
    private String newPhoneNumber;
    private Long accVersion;

    public OtherClientChangeDataDto(String login, Long version, String newPhoneNumber, OtherAddressChangeDto newAddress,Long accVersion) {
        super(login, version);
        this.newPhoneNumber = newPhoneNumber;
        this.newAddress = newAddress;
        this.accVersion= accVersion;
    }
}