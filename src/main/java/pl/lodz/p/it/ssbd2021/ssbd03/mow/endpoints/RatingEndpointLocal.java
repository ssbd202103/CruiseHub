package pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints;

import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.RatingDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.RemoveRankingDto;

public interface RatingEndpointLocal {

    /**
     * Dodaje ocenę użytkownika o podanym loginie dla wycieczki o podanym id
     * @param ratingDto obiekt dto przechowujący informację do stworzenia oceny
     *
     * @throws BaseAppException bazowy wyjątek aplikacji, zwracany w przypadku nieznależenia użytkownika lub grupy wycieczek
     */
    void createRating(RatingDto ratingDto) throws BaseAppException;

    /**
     * Usuwa ocenę o podanym użytkowniku oraz grupie wycieczek
     *
     * @param removeRankingDto obiekt dto przechowujący informację do usuwania oceny
     * @throws BaseAppException bazowy wyjątek aplikacji, zwracany w przypadku nieznależenia użytkownika lub grupy wycieczek
     */
    void removeRating(RemoveRankingDto removeRankingDto) throws BaseAppException;
}
