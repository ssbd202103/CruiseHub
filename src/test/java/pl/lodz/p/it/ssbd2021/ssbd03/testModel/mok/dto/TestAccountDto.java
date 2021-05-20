package pl.lodz.p.it.ssbd2021.ssbd03.testModel.mok.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevelType;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.LanguageType;
import pl.lodz.p.it.ssbd2021.ssbd03.security.SignableEntity;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Login;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Name;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Set;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_NOT_NULL;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.REGEX_INVALID_EMAIL;

@AllArgsConstructor
@Getter
@Setter
public class TestAccountDto implements SignableEntity {
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

    @NotNull(message = CONSTRAINT_NOT_NULL)
    private Set<AccessLevelType> accessLevels;

    @Override
    public String getSignablePayload() {
        return email;
    }
}
