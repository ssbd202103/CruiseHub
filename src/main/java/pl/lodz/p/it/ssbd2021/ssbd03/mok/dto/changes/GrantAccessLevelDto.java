package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevelType;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Login;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_NOT_NULL;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_POSITIVE_OR_ZERO_ERROR;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GrantAccessLevelDto {
    @Login
    private String accountLogin;

    @NotNull(message = CONSTRAINT_NOT_NULL)
    @Valid
    private AccessLevelType accessLevel;

    @PositiveOrZero(message = CONSTRAINT_POSITIVE_OR_ZERO_ERROR)
    private long accountVersion;
}
