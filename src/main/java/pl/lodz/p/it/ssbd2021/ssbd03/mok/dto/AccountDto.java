package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevelType;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.LanguageType;
import pl.lodz.p.it.ssbd2021.ssbd03.security.SignableEntity;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Login;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Name;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.Set;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccountDto implements SignableEntity {
    @Login
    private String login;

    @Name
    private String firstName;

    @Name
    private String secondName;

    @Email(message = REGEX_INVALID_EMAIL)
    private String email;

    @NotNull(message = CONSTRAINT_NOT_NULL)
    private LanguageType languageType;

    @NotEmpty(message = CONSTRAINT_NOT_EMPTY)
    private Set<AccessLevelType> accessLevels;

    @PositiveOrZero(message = CONSTRAINT_POSITIVE_OR_ZERO_ERROR)
    private Long version;

    @JsonIgnore
    @Override
    public String getSignablePayload() {
        return login + "." + version;
    }
}


