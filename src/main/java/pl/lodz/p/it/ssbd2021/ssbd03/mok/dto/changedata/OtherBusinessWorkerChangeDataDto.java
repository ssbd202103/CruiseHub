package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AbstractAccountDto;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.PhoneNumber;

import javax.validation.constraints.PositiveOrZero;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_POSITIVE_OR_ZERO_ERROR;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OtherBusinessWorkerChangeDataDto extends AbstractAccountDto {
    @PhoneNumber
    private String newPhoneNumber;
    @PositiveOrZero(message = CONSTRAINT_POSITIVE_OR_ZERO_ERROR)
    private Long accVersion;

    public OtherBusinessWorkerChangeDataDto(String login, Long version, String newPhoneNumber, Long accVersion) {
        super(login, version);
        this.newPhoneNumber = newPhoneNumber;
        this.accVersion = accVersion;
    }
}
