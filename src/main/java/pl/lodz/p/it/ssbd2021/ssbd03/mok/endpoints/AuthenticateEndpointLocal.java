package pl.lodz.p.it.ssbd2021.ssbd03.mok.endpoints;

import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.utils.TransactionRepeater;

import java.time.LocalDateTime;

/**
 * Klasa służąca do logowania się użytkowników
 */
public interface AuthenticateEndpointLocal {

    /**
     * Metoda odpowiedzialna za edycję pól w bazie danych w przypadku niepoprawnego logowania.
     *
     * @param login  Login użytkownika
     * @param IpAddr Adres IP użytkownika
     * @param time   Czas
     */
    void updateIncorrectAuthenticateInfo(String login, String IpAddr, LocalDateTime time) throws BaseAppException;

    /**
     * Metoda odpowiedzialna za edycję pól w bazie danych w przypadku poprawnego logowania.
     *
     * @param login  Login użytkownika
     * @param IpAddr Adres IP użytkownika
     * @param time   Czas
     * @return Token JWT
     */
    String updateCorrectAuthenticateInfo(String login, String IpAddr, LocalDateTime time) throws BaseAppException;

    /**
     *
     * @param login
     * @param IpAddr
     * @param time
     * @return
     * @throws BaseAppException
     */
    String signInCorrectAuthenticateInfo(String login, String IpAddr, LocalDateTime time, String kod) throws BaseAppException;

    /**
     *
     * @param login
     * @param code
     * @return
     * @throws BaseAppException
     */
    void verifySignCode(String login, String code, String IpAddr, LocalDateTime time) throws BaseAppException;

        /**
         *
         * @param login
         * @throws BaseAppException
         */
   void sendAuthenticationCodeEmail(String login) throws BaseAppException;

        /**
         * Metoda odpowiedzialna za odświeżanie tokenu JWT
         * @param token Aktualny token JWT
         * @return token Odnowiony token JWT
         * @throws BaseAppException Bazowy wyjątek aplikacyjny, rzucany w przypadku gdy przekazany token nie jest ważny,
         * lub jego podmiot nie jest upoważniony do ponownego uwierzytelnienia
         */
    String refreshToken(String token) throws BaseAppException;
}
