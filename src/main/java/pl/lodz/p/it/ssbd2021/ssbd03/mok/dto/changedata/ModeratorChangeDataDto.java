package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ModeratorChangeDataDto extends AccountChangeDataDto {

    public ModeratorChangeDataDto(String login, Long version, String newFirstName, String newSecondName) {
        super(login, version, newFirstName, newSecondName);
    }
}
