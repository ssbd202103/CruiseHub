package pl.lodz.p.it.ssbd2021.ssbd03.mow.managers;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Rating;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;

import javax.ejb.Local;
import java.util.List;
import java.util.UUID;

@Local
public interface RatingManagerLocal {

    /**
     * Tworzy ocenę przypisując do niej odpowiednego użytkownika oraz wycieczkę, dla której ta ocena została wystawiona
     *
     * @param login      login użytkownika, który wystawia ocenę
     * @param cruiseGroupUUID uuid grupy wycieczek, dla której zostanie wystawiona ocena
     * @param rating     wartość oceny
     * @throws BaseAppException bazowy wyjątek aplikacji, zwracany w przypadku nieznależenia użytkownika lub grupy wycieczek
     */
    void createRating(String login, UUID cruiseGroupUUID, Double rating) throws BaseAppException;

    /**
     * Usuwa ocenę o podanym użytkowniku oraz wycieczce
     *
     * @param login      login użytkownika, którego opinia zostanie usunięta
     * @param cruiseGroupUUID uuid grupy wycieczek, dla której opinia zostanie usunięta
     * @throws BaseAppException bazowy wyjątek aplikacji, zwracany w przypadku nieznależenia wycieczki, użytkownika lub oceny
     */
    void removeRating(String login, UUID cruiseGroupUUID) throws BaseAppException;


    /**
     * Pobiera ocene klienta dla podanej wycieczki
     *
     * @throws BaseAppException bazowy wyjątek aplikacji, zwracany w przypadku nieznależenia oceny
     */
    List<Rating> getOwnRatings() throws BaseAppException;

    /**
     * Usuwa ocenę klienta zgloszoną przez moderatora
     * @param login login klienta
     * @param cruiseGroupName nazwa grupy wycieczek, dla ktorej zostanie usunieta ocena
     * @throws BaseAppException bazowy wyjątek aplikacji
     */
    void removeClientRating(String login, String cruiseGroupName) throws BaseAppException;
}
