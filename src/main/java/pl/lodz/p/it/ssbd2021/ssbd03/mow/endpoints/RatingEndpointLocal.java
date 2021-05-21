package pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints;

import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.CreateRatingDto;

public interface RatingEndpointLocal {

    /**
     * Dodaje ocenę użytkownika o podanym loginie dla wycieczki o podanym id
     * @param createRatingDto obiekt dto przechowujący informację do stworzenia oceny
     */
    void createRating(CreateRatingDto createRatingDto) throws BaseAppException;
}
