package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata;

import lombok.*;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AbstractAccountDto;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Name;

@Getter
@Setter
@ToString
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
