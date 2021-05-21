package pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints;

import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.RatingDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.RemoveRankingDto;

public interface RatingEndpointLocal {

    /**
     * Dodaje ocenę użytkownika o podanym loginie dla grupy wycieczek o podanej nazwie
     * @param ratingDto obiekt dto przechowujący informację do stworzenia oceny
     */
    void createRating(RatingDto ratingDto) throws BaseAppException;

    /**
     * Usuwa ocenę użytkownika o podanym loginie dla grupy wycieczek o podanej nazwie
     * @param removeRankingDto obiekt dto przechowujący informację do usunięcia oceny
     */
    void removeRating(RemoveRankingDto removeRankingDto) throws BaseAppException;
}
