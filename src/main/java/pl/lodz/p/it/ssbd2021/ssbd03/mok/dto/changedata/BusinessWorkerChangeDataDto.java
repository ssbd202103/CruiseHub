package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.PhoneNumber;

@Data
@NoArgsConstructor
public class BusinessWorkerChangeDataDto extends ConsumerChangeDataDto {

    public BusinessWorkerChangeDataDto(String login, Long version, String newFirstName, String newSecondName, String newPhoneNumber) {
        super(login, version, newFirstName, newSecondName, newPhoneNumber);
    }
}