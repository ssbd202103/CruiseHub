package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata;


import lombok.*;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AbstractAccountDto;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.PhoneNumber;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_NOT_NULL;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_POSITIVE_OR_ZERO_ERROR;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OtherClientChangeDataDto extends AbstractAccountDto {
    @NotNull(message = CONSTRAINT_NOT_NULL)
    @Valid
    private OtherAddressChangeDto newAddress;
    @PhoneNumber
    private String newPhoneNumber;
    @PositiveOrZero(message = CONSTRAINT_POSITIVE_OR_ZERO_ERROR)
    private long accVersion;

    public OtherClientChangeDataDto(String login, long version, String newPhoneNumber,
                                    OtherAddressChangeDto newAddress, long accVersion) {
        super(login, version);
        this.newPhoneNumber = newPhoneNumber;
        this.newAddress = newAddress;
        this.accVersion = accVersion;
    }
}