package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.ssbd2021.ssbd03.security.SignableEntity;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Login;

import javax.validation.constraints.PositiveOrZero;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractAccountDTO implements SignableEntity {
    @Login
    private String login;
    @PositiveOrZero
    private Long version;

    @Override
    @JsonIgnore
    public String getSignablePayload() {
        return login + '.' + version;
    }
}
