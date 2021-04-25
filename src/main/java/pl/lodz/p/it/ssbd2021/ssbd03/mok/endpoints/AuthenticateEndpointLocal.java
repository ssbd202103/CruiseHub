package pl.lodz.p.it.ssbd2021.ssbd03.mok.endpoints;

import javassist.compiler.ast.Pair;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevel;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AuthenticateResponse;

import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Response;

/**
 * Klasa służąca do logowania się użytkowników
 */
public interface AuthenticateEndpointLocal {
    /**
     * Metoda służąca do logowania się użytkownika
     * @param login Login użytkownika
     * @param passwordHash Hasło użytkownika
     * @return Kod HTTP dotyczący wyniku logowania, konto użytkownika oraz token
     */
    Response authenticate(@NotNull String login, @NotNull String passwordHash);
}
