package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AddressDto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_NOT_NULL;

@Getter
@Setter
@NoArgsConstructor
public class ClientChangeDataDto extends ConsumerChangeDataDto {
    @NotNull(message = CONSTRAINT_NOT_NULL)
    @Valid
    private AddressDto newAddress;

    public ClientChangeDataDto(String login, long version, String newFirstName, String newSecondName, String newPhoneNumber, AddressDto newAddress) {
        super(login, version, newFirstName, newSecondName, newPhoneNumber);

        this.newAddress = newAddress;
    }
}
