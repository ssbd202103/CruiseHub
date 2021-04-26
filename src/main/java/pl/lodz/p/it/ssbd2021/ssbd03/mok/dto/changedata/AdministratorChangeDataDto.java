package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdministratorChangeDataDto extends AccountChangeDataDto {
    public AdministratorChangeDataDto(String login, Long version, String newFirstName, String newSecondName, String newLogin) {
        super(login, version, newFirstName, newSecondName, newLogin);
    }
}
