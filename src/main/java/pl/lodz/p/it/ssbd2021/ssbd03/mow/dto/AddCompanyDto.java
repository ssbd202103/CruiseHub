package pl.lodz.p.it.ssbd2021.ssbd03.mow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AddressDto;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.CompanyName;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.PhoneNumber;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_NOT_NULL;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_POSITIVE_ERROR;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddCompanyDto {
    @NotNull(message = CONSTRAINT_NOT_NULL)
    @CompanyName
    private String name;

    @NotNull(message = CONSTRAINT_NOT_NULL)
    @Valid
    private AddressDto addressDto;

    @NotNull(message = CONSTRAINT_NOT_NULL)
    @PhoneNumber
    private String phoneNumber;

    @Positive(message = CONSTRAINT_POSITIVE_ERROR)
    private long nip;
}
