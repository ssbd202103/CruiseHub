package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.detailsview.accesslevels;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevelType;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AddressDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.detailsview.AccessLevelDetailsViewDto;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.PhoneNumber;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_NOT_NULL;

@NoArgsConstructor
@Getter
@Setter
public class ClientDetailsViewDto extends AccessLevelDetailsViewDto {
    @NotNull(message = CONSTRAINT_NOT_NULL)
    @Valid
    private AddressDto address;

    @PhoneNumber
    private String phoneNumber;


    public ClientDetailsViewDto(boolean enabled,AddressDto address, String phoneNumber,long accLevelVersion) {
        super(enabled, AccessLevelType.CLIENT,accLevelVersion);
        this.address = address;
        this.phoneNumber = phoneNumber;

    }
}
