package pl.lodz.p.it.ssbd2021.ssbd03.mok.endpoints;


import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.AuthUnauthorizedException;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.managers.AccountManagerLocal;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.inject.Inject;
import java.time.LocalDateTime;

/**
 * Klasa która zajmuje się uwierzytelnieniem użytkownika.
 */
@Stateful
public class AuthenticateEndpoint implements AuthenticateEndpointLocal {

    @Inject
    private AccountManagerLocal accountManager;

    @Override
    public void updateIncorrectAuthenticateInfo(String login, String IpAddr, LocalDateTime time) throws AuthUnauthorizedException {
        accountManager.updateIncorrectAuthenticateInfo(login, IpAddr, time);
    }

    @Override
    public String updateCorrectAuthenticateInfo(String login, String IpAddr, LocalDateTime time) throws AuthUnauthorizedException {
        return accountManager.updateCorrectAuthenticateInfo(login, IpAddr, time);
    }
}
