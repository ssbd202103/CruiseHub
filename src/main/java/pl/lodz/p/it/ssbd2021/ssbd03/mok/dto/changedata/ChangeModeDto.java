package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata;

import lombok.Data;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AbstractAccountDto;

@Data
//@NoArgsConstructor
public class ChangeModeDto extends AbstractAccountDto {
    private boolean newMode;

    public ChangeModeDto(String login, Long version, boolean newMode) {
        super(login, version);

        this.newMode = newMode;
    }

    public ChangeModeDto() {
        System.out.println("HELLO");
    }
}
