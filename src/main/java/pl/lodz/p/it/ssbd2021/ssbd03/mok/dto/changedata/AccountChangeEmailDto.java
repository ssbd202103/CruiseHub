package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AbstractAccountDto;

import javax.validation.constraints.Email;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.REGEX_INVALID_EMAIL;

@Getter
@Setter
@NoArgsConstructor
public class AccountChangeEmailDto extends AbstractAccountDto {
    @Email(message = REGEX_INVALID_EMAIL)
    private String newEmail;

    public AccountChangeEmailDto(String login, Long version, String newEmail) {
        super(login, version);

        this.newEmail = newEmail;
    }
}
