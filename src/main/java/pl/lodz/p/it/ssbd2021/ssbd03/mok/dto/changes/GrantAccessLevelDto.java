package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevelType;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Login;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GrantAccessLevelDto {
    @Login
    private String accountLogin;

    @NotNull
    private AccessLevelType accessLevel;

    @PositiveOrZero
    private Long accountVersion;
}
