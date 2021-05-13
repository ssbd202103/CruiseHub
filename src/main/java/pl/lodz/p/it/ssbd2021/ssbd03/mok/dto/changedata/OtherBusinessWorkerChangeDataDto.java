package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.PhoneNumber;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OtherBusinessWorkerChangeDataDto extends OtherAccountChangeDataDto {
    @PhoneNumber
    private String newPhoneNumber;

    public OtherBusinessWorkerChangeDataDto(String login, Long version, String newFirstName, String newSecondName,String newEmail, String newPhoneNumber, String alteredBy) {
        super(login, version, newFirstName, newSecondName,newEmail, alteredBy);
        this.newPhoneNumber = newPhoneNumber;
    }
}
