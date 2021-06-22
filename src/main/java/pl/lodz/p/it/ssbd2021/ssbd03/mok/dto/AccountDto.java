package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
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
@ToString
public class AccountDto implements SignableEntity {
    @Login
    private String login;

    @Name
    private String firstName;

    @Name
    private String secondName;

    private boolean darkMode;

    @Email(message = REGEX_INVALID_EMAIL)
    @NotEmpty(message = CONSTRAINT_NOT_EMPTY)
    private String email;


    @NotNull(message = CONSTRAINT_NOT_NULL)
    private LanguageType languageType;

    @NotEmpty(message = CONSTRAINT_NOT_EMPTY)
    private Set<AccessLevelType> accessLevels;

    @PositiveOrZero(message = CONSTRAINT_POSITIVE_OR_ZERO)
    private long version;

    @JsonIgnore
    @Override
    public String getSignablePayload() {
        return login + "." + version;
    }
}


