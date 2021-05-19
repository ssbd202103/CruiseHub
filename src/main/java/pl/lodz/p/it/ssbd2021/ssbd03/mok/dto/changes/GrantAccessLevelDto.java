package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevelType;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Login;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@AllArgsConstructor
@NoArgsConstructor
public class GrantAccessLevelDto {
    public void setAccountLogin(String accountLogin) {
        this.accountLogin = accountLogin;
    }

    public void setAccessLevel(AccessLevelType accessLevel) {
        this.accessLevel = accessLevel;
    }

    public void setAccountVersion(Long accountVersion) {
        this.accountVersion = accountVersion;
    }

    public String getAccountLogin() {
        return accountLogin;
    }

    public AccessLevelType getAccessLevel() {
        return accessLevel;
    }

    public Long getAccountVersion() {
        return accountVersion;
    }

    @Login
    private String accountLogin;

    @NotNull
    private AccessLevelType accessLevel;

    @PositiveOrZero
    private Long accountVersion;
}
