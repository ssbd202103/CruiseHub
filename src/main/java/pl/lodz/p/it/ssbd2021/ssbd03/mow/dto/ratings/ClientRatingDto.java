package pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.ratings;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Login;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.util.UUID;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientRatingDto {
    @Login
    private String login;

    @NotEmpty(message = CONSTRAINT_NOT_EMPTY)
    private String cruiseGroupName;

    @NotNull(message = CONSTRAINT_NOT_NULL)
    @NotEmpty(message = CONSTRAINT_NOT_EMPTY)
    private String cruiseGroupUUID;

    @Max(value = 5, message = RATING_CONSTRAINT_ERROR)
    @Min(value = 1, message = RATING_CONSTRAINT_ERROR)
    private Double rating;

    private String ratingUuid;
}
