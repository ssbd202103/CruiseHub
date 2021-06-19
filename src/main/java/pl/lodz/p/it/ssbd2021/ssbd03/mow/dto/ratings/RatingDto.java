package pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.ratings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Login;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Name;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RatingDto {
    @NotNull(message = CONSTRAINT_NOT_NULL)
    @NotEmpty(message = CONSTRAINT_NOT_EMPTY)
    private String cruiseGroupUUID;

    @Max(value = 5, message = RATING_CONSTRAINT_ERROR)
    @Min(value = 1, message = RATING_CONSTRAINT_ERROR)
    private Double rating;

    @Login
    private String login;

    @Name
    private String accountFirstName;

    @Name
    private String accountSecondName;
}
