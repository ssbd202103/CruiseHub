package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AbstractAccountDto;
import pl.lodz.p.it.ssbd2021.ssbd03.security.SignableEntity;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Password;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountOwnPasswordDto extends AbstractAccountDto {
    @NotNull
    @Password
    private String oldPassword;

    @NotNull
    @Password
    private String newPassword;

    public AccountOwnPasswordDto(String login, Long version, String oldPassword, String newPassword) {
        super(login, version);
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }
}
