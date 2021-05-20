package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevelType;
import pl.lodz.p.it.ssbd2021.ssbd03.security.SignableEntity;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Login;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_NOT_NULL;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_POSITIVE_OR_ZERO_ERROR;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeAccessLevelStateDto implements SignableEntity {
    @Login
    private String accountLogin;

    @NotNull
    private AccessLevelType accessLevel;

    @PositiveOrZero(message = CONSTRAINT_POSITIVE_OR_ZERO_ERROR)
    private Long accountVersion;

    private boolean enabled;

    @JsonIgnore
    @Override
    public String getSignablePayload() {
        return accountLogin + "." + accountVersion;
    }
}
