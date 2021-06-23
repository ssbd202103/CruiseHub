package pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints;

import pl.lodz.p.it.ssbd2021.ssbd03.common.dto.MetadataDto;
import pl.lodz.p.it.ssbd2021.ssbd03.common.endpoints.TransactionalEndpoint;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.ratings.ClientRatingDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.CreateRatingDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.ratings.RatingDto;

import javax.ejb.Local;
import java.util.List;
import java.util.UUID;

@Local
public interface RatingEndpointLocal extends TransactionalEndpoint {

    /**
     * Dodaje ocenę użytkownika o podanym loginie dla wycieczki o podanym id
     *
     * @param ratingDto obiekt dto przechowujący informację do stworzenia oceny
     * @throws BaseAppException bazowy wyjątek aplikacji, zwracany w przypadku nieznależenia użytkownika lub grupy wycieczek
     */
    void createRating(CreateRatingDto ratingDto) throws BaseAppException;

    /**
     * Usuwa ocenę o podanym użytkowniku oraz grupie wycieczek
     *
     * @param cruiseGroupUUID uuid grupy wycieczek
     * @throws BaseAppException bazowy wyjątek aplikacji, zwracany w przypadku nieznależenia użytkownika lub grupy wycieczek
     */
    void removeRating(UUID cruiseGroupUUID) throws BaseAppException;

    /**
     * Pobiera wszystkie oceny klienta
     * @param login login klienta
     * @return listę ocen
     * @throws BaseAppException bazowy wyjątek aplikacji
     */
    List<ClientRatingDto> getClientRatings(String login) throws BaseAppException;

    /**
     * Pobiera ocene klienta dla podanej wycieczki
     *
     * @throws BaseAppException bazowy wyjątek aplikacji, zwracany w przypadku nieznależenia oceny
     */
    List<RatingDto> getOwnRatings() throws BaseAppException;

    /**
     * Metoda służąca do usunięcia oceny klienta przez moderatora
     * @param login login klienta
     * @param cruiseGroupUUID uuid grupy wycieczek
     * @throws BaseAppException
     */
    void removeClientRating(String login, UUID cruiseGroupUUID) throws BaseAppException;

    /**
     * Pobiera mewtadane oceny o danym uuid
     *
     * @param uuid uuid oceny
     * @return zwraca informacje o ocenie
     * @throws BaseAppException wyjątek wyrzucany w razie nie znależenia atrakcji
     */
    MetadataDto getRatingMetadata(UUID uuid) throws BaseAppException;
}
