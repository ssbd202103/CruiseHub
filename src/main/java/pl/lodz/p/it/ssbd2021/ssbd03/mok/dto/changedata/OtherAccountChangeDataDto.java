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
    @Name
    private String AlteredBy;

    public OtherAccountChangeDataDto(String login, Long version, String newFirstName, String newSecondName, String alteredBy) {
        super(login, version);

        this.newFirstName = newFirstName;
        this.newSecondName = newSecondName;
        this.AlteredBy = alteredBy;
    }
}
