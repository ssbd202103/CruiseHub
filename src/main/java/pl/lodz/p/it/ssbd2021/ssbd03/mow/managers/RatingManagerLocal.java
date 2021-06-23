package pl.lodz.p.it.ssbd2021.ssbd03.mow.managers;

import com.fasterxml.jackson.databind.ser.Serializers;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Rating;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.FacadeException;

import javax.ejb.Local;
import java.util.List;
import java.util.UUID;

@Local
public interface RatingManagerLocal {

    /**
     * Tworzy ocenę przypisując do niej odpowiednego użytkownika oraz wycieczkę, dla której ta ocena została wystawiona
     *
     * @param cruiseGroupUUID uuid grupy wycieczek, dla której zostanie wystawiona ocena
     * @param rating          wartość oceny
     * @throws BaseAppException bazowy wyjątek aplikacji, zwracany w przypadku nieznależenia użytkownika lub grupy wycieczek
     */
    void createRating(UUID cruiseGroupUUID, Double rating) throws BaseAppException;

    /**
     * Usuwa ocenę grupy wycieczek o podanym uuid
     *
     * @param cruiseGroupUUID uuid grupy wycieczek, dla której ocena zostanie usunięta
     * @throws BaseAppException bazowy wyjątek aplikacji, zwracany w przypadku nieznależenia grupy wycieczek, użytkownika lub oceny
     */
    void removeRating(UUID cruiseGroupUUID) throws BaseAppException;

    /**
     * Pobiera ocene klienta dla podanej wycieczki
     *
     * @throws BaseAppException bazowy wyjątek aplikacji, zwracany w przypadku nieznależenia oceny
     */
    List<Rating> getOwnRatings() throws BaseAppException;

    /**
     * Metoda pobierająca wszystkie oceny klienta
     *
     * @param login login klienta
     * @return listę ocen
     * @throws BaseAppException bazowy wyjątek aplikacji
     */
    List<Rating> getClientRatings(String login) throws BaseAppException;

    /**
     * Usuwa ocenę klienta zgloszoną przez moderatora
     *
     * @param login           login klienta
     * @param cruiseGroupUUID UUID grupy wycieczek, do ktorej nalezy ocena
     * @throws BaseAppException bazowy wyjątek aplikacji
     */
    void removeClientRating(String login, UUID cruiseGroupUUID) throws BaseAppException;

    /**
     * Zwraca ocenę o podanym uuid
     *
     * @param uuid UUID oceny
     * @throws BaseAppException bazowy wyjątek aplikacji
     */
    Rating findByUuid(UUID uuid) throws FacadeException;

}
