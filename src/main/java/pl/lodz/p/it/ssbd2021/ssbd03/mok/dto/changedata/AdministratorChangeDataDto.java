package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AdministratorChangeDataDto extends AccountChangeDataDto {
    public AdministratorChangeDataDto(String login, long version, String newFirstName, String newSecondName) {
        super(login, version, newFirstName, newSecondName);
    }
}
