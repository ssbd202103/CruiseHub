package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata;


import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.PhoneNumber;

@Data
@NoArgsConstructor
public class ClientChangeDataDto extends AccountChangeDataDto {
    @PhoneNumber
    private String newPhoneNumber;

    private AddressChangeDto newAddress;

    public ClientChangeDataDto(String login, Long version, String newFirstName, String newSecondName, String newPhoneNumber, AddressChangeDto newAddress) {
        super(login, version, newFirstName, newSecondName);

        this.newPhoneNumber = newPhoneNumber;
        this.newAddress = newAddress;
    }
}
