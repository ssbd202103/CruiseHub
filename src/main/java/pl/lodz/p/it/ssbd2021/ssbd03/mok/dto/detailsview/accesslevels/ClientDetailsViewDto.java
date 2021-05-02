package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.detailsview.accesslevels;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.*;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevelType;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AddressDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.detailsview.AccessLevelDetailsViewDto;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.PhoneNumber;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
public class ClientDetailsViewDto extends AccessLevelDetailsViewDto {
    @NotNull
    private AddressDto address;

    @PhoneNumber
    private String phoneNumber;

    public ClientDetailsViewDto(boolean enabled,AddressDto address, String phoneNumber) {
        super(enabled, AccessLevelType.CLIENT);
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
}
