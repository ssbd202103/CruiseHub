package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.ssbd2021.ssbd03.security.SignableEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractAccountDTO implements SignableEntity {
    private String login;
    private Long version;

    @Override
    public String getSignablePayload() {
        return login + '.' + version;
    }
}
