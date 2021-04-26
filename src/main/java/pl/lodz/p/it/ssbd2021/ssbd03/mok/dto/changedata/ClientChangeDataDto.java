package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata;


import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Address;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.PhoneNumber;

@Data
@NoArgsConstructor
public class ClientChangeDataDto extends AccountChangeDataDto {
    @PhoneNumber
    private String newPhoneNumber;

    private Address newAddress;

    public ClientChangeDataDto(String login, Long version, String newFirstName, String newSecondName, String newLogin, String newPhoneNumber, Address newAddress) {
        super(login, version, newFirstName, newSecondName, newLogin);

        this.newPhoneNumber = newPhoneNumber;
        this.newAddress = newAddress;
    }
}
