package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AbstractAccountDTO;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Name;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountChangeDataDto extends AbstractAccountDTO {
    @Name
    private String newFirstName;
    @Name
    private String newSecondName;

    public AccountChangeDataDto(String login, Long version, String newFirstName, String newSecondName) {
        super(login, version);

        this.newFirstName = newFirstName;
        this.newSecondName = newSecondName;
    }
}
