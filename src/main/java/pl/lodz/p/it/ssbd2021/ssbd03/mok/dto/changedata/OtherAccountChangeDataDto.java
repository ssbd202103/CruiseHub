package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata;

import lombok.*;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AbstractAccountDto;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.FirstName;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Name;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.SecondName;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OtherAccountChangeDataDto extends AbstractAccountDto {
    @FirstName
    private String newFirstName;

    @SecondName
    private String newSecondName;

    public OtherAccountChangeDataDto(String login, long version, String newFirstName, String newSecondName) {
        super(login, version);
        this.newFirstName = newFirstName;
        this.newSecondName = newSecondName;
    }
}
