package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AbstractAccountDto;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Password;

import javax.validation.constraints.NotNull;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_NOT_NULL;

@Getter
@Setter
@NoArgsConstructor
public class AccountChangeOwnPasswordDto extends AbstractAccountDto {
    @Password
    private String oldPassword;

    @Password
    private String newPassword;

    public AccountChangeOwnPasswordDto(String login, Long version, String oldPassword, String newPassword) {
        super(login, version);
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }
}
