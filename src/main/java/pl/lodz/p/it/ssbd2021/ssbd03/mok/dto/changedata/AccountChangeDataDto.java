package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AbstractAccountDTO;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Login;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Name;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountChangeDataDto extends AbstractAccountDTO {
    @Name
    private String newFirstName;
    @Name
    private String newSecondName;
    @Login
    private String newLogin;

    public AccountChangeDataDto(String login, Long version, String newFirstName, String newSecondName, String newLogin) {
        super(login, version);

        this.newFirstName = newFirstName;
        this.newSecondName = newSecondName;
        this.newLogin = newLogin;
    }
}
