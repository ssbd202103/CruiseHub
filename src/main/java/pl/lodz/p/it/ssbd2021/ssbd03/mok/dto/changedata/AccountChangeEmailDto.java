package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AbstractAccountDTO;

import javax.validation.constraints.Email;

@Getter
@Setter
@NoArgsConstructor
public class AccountChangeEmailDto extends AbstractAccountDTO {
    @Email
    private String newEmail;

    public AccountChangeEmailDto(String login, Long version, String newEmail) {
        super(login, version);

        this.newEmail = newEmail;
    }
}
