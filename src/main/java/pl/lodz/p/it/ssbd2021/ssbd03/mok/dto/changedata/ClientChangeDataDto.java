package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata;

import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AddressDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClientChangeDataDto extends ConsumerChangeDataDto {
    private AddressDto newAddress;

    public ClientChangeDataDto(String login, Long version, String newFirstName, String newSecondName, String newPhoneNumber, AddressDto newAddress) {
        super(login, version, newFirstName, newSecondName, newPhoneNumber);

        this.newAddress = newAddress;
    }
}
