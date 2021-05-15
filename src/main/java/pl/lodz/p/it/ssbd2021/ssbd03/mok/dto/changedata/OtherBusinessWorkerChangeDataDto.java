package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.PhoneNumber;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OtherBusinessWorkerChangeDataDto extends OtherAccountChangeDataDto {
    @PhoneNumber
    private String newPhoneNumber;

    public OtherBusinessWorkerChangeDataDto(String login, Long version, String newFirstName, String newSecondName, String newPhoneNumber, String alteredBy) {
        super(login, version, newFirstName, newSecondName, alteredBy);
        this.newPhoneNumber = newPhoneNumber;
    }
}
