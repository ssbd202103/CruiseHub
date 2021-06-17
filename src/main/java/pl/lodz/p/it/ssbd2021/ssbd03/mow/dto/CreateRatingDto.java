package pl.lodz.p.it.ssbd2021.ssbd03.mow.dto;

import lombok.*;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Login;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_NOT_EMPTY;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.RATING_CONSTRAINT_ERROR;

@Data
@Getter
@Setter
@NoArgsConstructor
public class CreateRatingDto {
    @Login
    private String login;

    @NotEmpty(message = CONSTRAINT_NOT_EMPTY)
    private UUID cruiseGroupUUID;

    @Max(value = 5, message = RATING_CONSTRAINT_ERROR)
    @Min(value = 1, message = RATING_CONSTRAINT_ERROR)
    private Double rating;

    public CreateRatingDto(String login, String cruiseGroupUUID, Double rating) {
        this.login = login;
        this.cruiseGroupUUID = UUID.fromString(cruiseGroupUUID);
        this.rating = rating;
    }
}
