package pl.lodz.p.it.ssbd2021.ssbd03.mow.managers;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Rating;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;

import javax.ejb.Local;

@Local
public interface RatingManagerLocal {

    /**
     * Tworzy ocenę przypisując do niej odpowiednego użytkownika oraz wycieczkę, dla której ta ocena została wystawiona
     * @param login login użytkownika wystawiający ocenę
     * @param cruiseName nazwa grupy wycieczek, dla której wystawia się ocena
     * @throws BaseAppException bazowy wyjątek aplikacji, zwracany w przypadku nieznalezienia użytkownika lub grupy wycieczek
     */
    void createRating(String login, String cruiseName, Integer rating) throws BaseAppException;

    /**
     * Usuwa ocenę użytkownika o podanym loginie dla grupy wycieczek o podanej nazwie
     * @param login login użytkownika, którego ocena zostanie usunięta
     * @param cruiseName nazwa grupy wycieczek, dla której ocena zostanie usunięta
     * @throws BaseAppException bazowy wyjątek aplikacji, zwracany w przypadku nieznalezienia użytkownika lub grupy wycieczek
     */
    void removeRating(String login, String cruiseName) throws BaseAppException;
}
