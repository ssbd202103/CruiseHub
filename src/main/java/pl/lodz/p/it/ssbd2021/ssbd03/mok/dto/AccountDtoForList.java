package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevelType;
import pl.lodz.p.it.ssbd2021.ssbd03.security.EntityIdentitySignerVerifier;
import pl.lodz.p.it.ssbd2021.ssbd03.security.SignableEntity;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Login;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class AccountDtoForList implements SignableEntity {

    @Login
    private String login;

    @Email
    private String email;

    @NotNull
    private boolean active;

    @PositiveOrZero
    private Long version;

    @NotNull
    private Set<AccessLevelType> accessLevels;

    private String etag;

    public AccountDtoForList(@Login String login, @Email String email, @NotNull boolean active, @PositiveOrZero Long version, @NotNull Set<AccessLevelType> accessLevels, String etag) {
        this.login = login;
        this.email = email;
        this.active = active;
        this.version = version;
        this.accessLevels = accessLevels;
        this.etag = etag;
    }

    @JsonIgnore
    @Override
    public String getSignablePayload() {
        return login + "." + version;
    }

    public AccountDtoForList(@Login String login, @Email String email, @NotNull boolean active, @PositiveOrZero Long version, @NotNull Set<AccessLevelType> accessLevels) {
        this.login = login;
        this.email = email;
        this.active = active;
        this.version = version;
        this.accessLevels = accessLevels;
        this.etag = EntityIdentitySignerVerifier.calculateEntitySignature(this);
    }


}
