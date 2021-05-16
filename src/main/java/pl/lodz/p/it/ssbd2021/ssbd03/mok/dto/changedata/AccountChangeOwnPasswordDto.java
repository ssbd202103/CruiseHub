package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata;

import lombok.*;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AbstractAccountDto;
import pl.lodz.p.it.ssbd2021.ssbd03.security.SignableEntity;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Password;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class AccountChangeOwnPasswordDto extends AbstractAccountDto {
    @NotNull
    @Password
    private String oldPassword;

    @NotNull
    @Password
    private String newPassword;

    public AccountChangeOwnPasswordDto(String login, Long version, String oldPassword, String newPassword) {
        super(login, version);
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }
}
