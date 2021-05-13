package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata;

import lombok.*;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AbstractAccountDTO;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Login;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Name;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OtherAccountChangeDataDto extends AbstractAccountDTO {
    @Name
    private String newFirstName;
    @Name
    private String newSecondName;
    private String newEmail;
    @Name
    private String alteredBy;

    public OtherAccountChangeDataDto(String login, Long version, String newFirstName, String newSecondName,String newEmail, String alteredBy) {
        super(login, version);
        this.newEmail=newEmail;
        this.newFirstName = newFirstName;
        this.newSecondName = newSecondName;
        this.alteredBy = alteredBy;
    }
}
