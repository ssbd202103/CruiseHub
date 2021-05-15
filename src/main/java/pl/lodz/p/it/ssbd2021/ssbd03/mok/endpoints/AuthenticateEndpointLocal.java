package pl.lodz.p.it.ssbd2021.ssbd03.mok.endpoints;

import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.AuthUnauthorizedException;

import java.time.LocalDateTime;

/**
 * Klasa służąca do logowania się użytkowników
 */
public interface AuthenticateEndpointLocal {

    /**
     * Metoda odpowiedzialna za edycję pól w bazie danych w przypadku niepoprawnego logowania.
     * @param login Login użytkownika
     * @param IpAddr Adres IP użytkownika
     * @param time Czas
     */
    void updateIncorrectAuthenticateInfo(String login, String IpAddr, LocalDateTime time) throws AuthUnauthorizedException;

    /**
     * Metoda odpowiedzialna za edycję pól w bazie danych w przypadku poprawnego logowania.
     * @param login Login użytkownika
     * @param IpAddr Adres IP użytkownika
     * @param time Czas
     * @return Token JWT
     */
    String updateCorrectAuthenticateInfo(String login, String IpAddr, LocalDateTime time) throws AuthUnauthorizedException;
}
