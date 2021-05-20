package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AddressDto;

import javax.validation.Valid;

@Getter
@Setter
@NoArgsConstructor
public class ClientChangeDataDto extends ConsumerChangeDataDto {
    @NotNull
    private AddressDto newAddress;

    public ClientChangeDataDto(String login, Long version, String newFirstName, String newSecondName, String newPhoneNumber, AddressDto newAddress) {
        super(login, version, newFirstName, newSecondName, newPhoneNumber);

        this.newAddress = newAddress;
    }
}
