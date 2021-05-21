package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.detailsview;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.LanguageType;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.security.EntityIdentitySignerVerifier;
import pl.lodz.p.it.ssbd2021.ssbd03.security.SignableEntity;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Login;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Name;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.Set;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.*;

@Data
@NoArgsConstructor
public class AccountDetailsViewDto implements SignableEntity {
    @Name
    private String firstName;

    @Name
    private String secondName;

    private boolean darkMode;

    @Login
    private String login;

    @Email(message = REGEX_INVALID_EMAIL)
    @NotEmpty(message = CONSTRAINT_NOT_EMPTY)
    private String email;

    private boolean confirmed;

    private boolean active;

    @NotNull(message = CONSTRAINT_NOT_NULL)
    private LanguageType languageType;

    @Valid
    @NotEmpty(message = CONSTRAINT_NOT_EMPTY)
    private Set<AccessLevelDetailsViewDto> accessLevels;

    @PositiveOrZero(message = CONSTRAINT_POSITIVE_OR_ZERO_ERROR)
    private long version;

    @NotEmpty(message = CONSTRAINT_NOT_EMPTY)
    private String etag;

    public AccountDetailsViewDto(String firstName, String secondName, boolean darkMode, String login, String email, boolean confirmed, boolean active, LanguageType languageType, Set<AccessLevelDetailsViewDto> accessLevels, long version)  throws BaseAppException {
        this.firstName = firstName;
        this.secondName = secondName;
        this.darkMode = darkMode;
        this.login = login;
        this.email = email;
        this.confirmed = confirmed;
        this.active = active;
        this.languageType = languageType;
        this.accessLevels = accessLevels;
        this.version = version;
        this.etag = EntityIdentitySignerVerifier.calculateEntitySignature(this);
    }

    @JsonIgnore
    @Override
    public String getSignablePayload() {
        return login + "." + version;
    }
}

