package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.ssbd2021.ssbd03.security.SignableEntity;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Login;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlockAccountDto implements SignableEntity {
    @Login
    private String login;

    private Long version;

    @JsonIgnore
    @Override
    public String getSignablePayload() {
        return login + "." + version;
    }
}
