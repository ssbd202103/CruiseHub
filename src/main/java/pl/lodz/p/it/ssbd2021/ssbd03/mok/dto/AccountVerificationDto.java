package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_NOT_EMPTY;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountVerificationDto {

    @NotEmpty(message = CONSTRAINT_NOT_EMPTY)
    private String token;
}
