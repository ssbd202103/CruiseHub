package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AbstractAccountDto;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Password;

@Getter
@Setter
@NoArgsConstructor
public class AccountChangeOwnPasswordDto extends AbstractAccountDto {
    @Password
    private String oldPassword;

    @Password
    private String newPassword;

    public AccountChangeOwnPasswordDto(String login, long version, String oldPassword, String newPassword) {
        super(login, version);
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }
}
