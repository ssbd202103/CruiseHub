package pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.ratings;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.RemoveRankingDto;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Login;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_NOT_EMPTY;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.RATING_CONSTRAINT_ERROR;

@Getter
@Setter
@NoArgsConstructor
public class RatingDto extends RemoveRankingDto {
    @Max(value = 5, message = RATING_CONSTRAINT_ERROR)
    @Min(value = 1, message = RATING_CONSTRAINT_ERROR)
    private Integer rating;

    public RatingDto(@Login String login,
                     @NotEmpty(message = CONSTRAINT_NOT_EMPTY) String cruiseName,
                     @Max(value = 5, message = RATING_CONSTRAINT_ERROR)
                     @Min(value = 1, message = RATING_CONSTRAINT_ERROR) Integer rating) {

        super(login, cruiseName);

        this.rating = rating;
    }
}
