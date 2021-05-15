package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.detailsview;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.LanguageType;
import pl.lodz.p.it.ssbd2021.ssbd03.security.EntityIdentitySignerVerifier;
import pl.lodz.p.it.ssbd2021.ssbd03.security.SignableEntity;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Login;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Name;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.Set;

@Data
@NoArgsConstructor
public class AccountDetailsViewDto implements SignableEntity {
    @Name
    private String firstName;

    @Name
    private String secondName;

    @Login
    private String login;

    @Email
    private String email;

    private boolean confirmed;

    private boolean active;

    @NotNull
    private LanguageType languageType;

    @NotEmpty
    private Set<AccessLevelDetailsViewDto> accessLevels;

    @PositiveOrZero
    private Long version;

    @NotEmpty
    private String etag;

    public AccountDetailsViewDto(String firstName, String secondName, String login, String email, boolean confirmed, boolean active, LanguageType languageType, Set<AccessLevelDetailsViewDto> accessLevels, Long version) {
        this.firstName = firstName;
        this.secondName = secondName;
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

