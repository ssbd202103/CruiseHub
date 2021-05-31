package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AbstractAccountDto;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ChangeLanguageDto extends AbstractAccountDto {
    public ChangeLanguageDto(String login, Long version) {
        super(login, version);
    }
}
