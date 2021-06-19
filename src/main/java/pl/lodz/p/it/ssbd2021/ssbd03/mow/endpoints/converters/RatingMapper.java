package pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints.converters;

import lombok.NoArgsConstructor;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Rating;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.ratings.ClientRatingDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.ratings.RatingDto;

@NoArgsConstructor
public class RatingMapper {
    public static RatingDto mapRatingToRatingDto(Rating rating) {
        return new RatingDto(rating.getAccount().getLogin(), rating.getCruiseGroup().getName(), rating.getRating(), rating.getCruiseGroup().getUuid());
    }

    /**
     * Mapuje obiekt klasy Rating oraz login klienta na obiekt klasy ClientRatingDto
     * @param login login klienta
     * @param rating obiekt klasy Rating
     * @return obiekt klasy ClientRatingDto
     */
    public static ClientRatingDto toClientRatingDto(String login, Rating rating) {
        return new ClientRatingDto(
            login, rating.getCruiseGroup().getName(), rating.getRating()
        );
    }
}
