package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AbstractAccountDto;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Name;

import javax.validation.constraints.Email;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.REGEX_INVALID_EMAIL;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OtherAccountChangeDataDto extends AbstractAccountDto {
    @Name
    private String newFirstName;
    @Name
    private String newSecondName;
    @Email(message = REGEX_INVALID_EMAIL)
    private String newEmail;


    public OtherAccountChangeDataDto(String login, Long version, String newFirstName, String newSecondName, String newEmail) {
        super(login, version);
        this.newEmail = newEmail;
        this.newFirstName = newFirstName;
        this.newSecondName = newSecondName;
    }
}
