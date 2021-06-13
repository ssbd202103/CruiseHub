package pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints.converters;

import lombok.NoArgsConstructor;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Rating;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.RatingDto;

@NoArgsConstructor
public class RatingMapper {
    public static RatingDto toRatingDto(Rating rating) {
        return new RatingDto(rating.getAccount().getLogin(), rating.getCruiseGroup().getUuid(),
                rating.getRating(), rating.getAccount().getFirstName(), rating.getAccount().getSecondName());
    }
}
