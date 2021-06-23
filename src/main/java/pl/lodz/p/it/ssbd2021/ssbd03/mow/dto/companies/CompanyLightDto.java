package pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.companies;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.CompanyName;

import javax.validation.constraints.Positive;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_POSITIVE;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CompanyLightDto {
    @CompanyName
    private String name;

    @Positive(message = CONSTRAINT_POSITIVE)
    private long nip;
}