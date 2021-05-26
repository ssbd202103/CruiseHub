package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AbstractAccountDto;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Name;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_NOT_EMPTY;
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



    public OtherAccountChangeDataDto(String login, long version, String newFirstName, String newSecondName) {
        super(login, version);
        this.newFirstName = newFirstName;
        this.newSecondName = newSecondName;
    }
}
