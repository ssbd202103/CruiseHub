package pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.detailsview;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.LanguageType;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Login;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Name;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDetailsViewDto {
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

    private Set<AccessLevelDetailsViewDto> accessLevels;
}
