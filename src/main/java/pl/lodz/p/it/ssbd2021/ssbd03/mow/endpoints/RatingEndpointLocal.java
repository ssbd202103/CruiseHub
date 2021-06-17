package pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints;

import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.ratings.RatingDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.ratings.RemoveClientRatingDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.CreateRatingDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.RatingDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.RemoveClientRatingDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.RemoveRankingDto;

import javax.ejb.Local;
import java.util.List;

@Local
import javax.ejb.Local;

@Local
public interface RatingEndpointLocal {

    /**
     * Dodaje ocenę użytkownika o podanym loginie dla wycieczki o podanym id
     *
     * @param ratingDto obiekt dto przechowujący informację do stworzenia oceny
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

    /**
     * Pobiera ocene klienta dla podanej wycieczki
     *
     * @throws BaseAppException bazowy wyjątek aplikacji, zwracany w przypadku nieznależenia oceny
     */
    List<RatingDto> getOwnRatings() throws BaseAppException;

    /**
     * Metoda służąca do usunięcia oceny klienta przez moderatora
     * @param removeClientRatingDto obiekt dto przechowujący informacje odnosnie oceny do usunięcia
     * @throws BaseAppException bazowy wyjątek aplikacji
     */
    void removeClientRating(RemoveClientRatingDto removeClientRatingDto) throws BaseAppException;
}
