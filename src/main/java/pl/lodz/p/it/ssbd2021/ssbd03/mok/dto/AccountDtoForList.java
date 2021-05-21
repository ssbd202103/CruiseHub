package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevelType;
import pl.lodz.p.it.ssbd2021.ssbd03.security.EntityIdentitySignerVerifier;
import pl.lodz.p.it.ssbd2021.ssbd03.security.SignableEntity;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Login;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Name;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.Set;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.*;

@NoArgsConstructor
@Getter
@Setter
public class AccountDtoForList implements SignableEntity {

    @Login
    private String login;

    @Name
    private String firstName;

    @Name
    private String secondName;

    @Email(message = REGEX_INVALID_EMAIL)
    @NotEmpty(message = CONSTRAINT_NOT_EMPTY)
    private String email;

    private boolean active;

    @PositiveOrZero(message = CONSTRAINT_POSITIVE_OR_ZERO_ERROR)
    private long version;

    @NotEmpty(message = CONSTRAINT_NOT_EMPTY)
    private Set<AccessLevelType> accessLevels;

    @NotEmpty(message = CONSTRAINT_NOT_EMPTY)
    private String etag;

    public AccountDtoForList(@Login String login, @Name String firstName, @Name String secondName, @Email(message = REGEX_INVALID_EMAIL)     @NotEmpty(message = CONSTRAINT_NOT_EMPTY)
             String email, @NotNull(message = CONSTRAINT_NOT_NULL) boolean active, @PositiveOrZero(message = CONSTRAINT_POSITIVE_OR_ZERO_ERROR) long version, @NotNull(message = CONSTRAINT_NOT_NULL) Set<AccessLevelType> accessLevels, String etag) {
        this.login = login;
        this.firstName = firstName;
        this.secondName = secondName;
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

    public AccountDtoForList(@Login String login, @Name String firstName, @Name String secondName, @Email(message = REGEX_INVALID_EMAIL)     @NotEmpty(message = CONSTRAINT_NOT_EMPTY)
            String email, @NotNull(message = CONSTRAINT_NOT_NULL) boolean active, @PositiveOrZero(message = CONSTRAINT_POSITIVE_OR_ZERO_ERROR) long version, @NotNull(message = CONSTRAINT_NOT_NULL) Set<AccessLevelType> accessLevels) {
        this.login = login;
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.active = active;
        this.version = version;
        this.accessLevels = accessLevels;
        this.etag = EntityIdentitySignerVerifier.calculateEntitySignature(this);
    }
}
