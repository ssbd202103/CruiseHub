package pl.lodz.p.it.ssbd2021.ssbd03.mow.managers;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Rating;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;

public interface RatingManagerLocal {

    /**
     * Tworzy ocenę przypisując do niej odpowiednego użytkownika oraz wycieczkę, dla której ta ocena została wystawiona
     * @param rating obiekt repezentujący ocenę
     * @throws BaseAppException bazowy wyjątek aplikacji, zwracany w przypadku nieznalezienia użytkownika lub wycieczki
     */
    void createRating(Rating rating) throws BaseAppException;
}
