package pl.lodz.p.it.ssbd2021.ssbd03.mok.endpoints;


import pl.lodz.p.it.ssbd2021.ssbd03.common.endpoints.BaseEndpoint;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.managers.AccountManagerLocal;
import pl.lodz.p.it.ssbd2021.ssbd03.utils.interceptors.TrackingInterceptor;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.time.LocalDateTime;

/**
 * Klasa która zajmuje się uwierzytelnieniem użytkownika.
 */
@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@PermitAll
@Interceptors(TrackingInterceptor.class)
public class AuthenticateEndpoint extends BaseEndpoint implements AuthenticateEndpointLocal {

    @Inject
    private AccountManagerLocal accountManager;

    @PermitAll
    @Override
    public void updateIncorrectAuthenticateInfo(String login, String IpAddr, LocalDateTime time) throws BaseAppException {
        accountManager.updateIncorrectAuthenticateInfo(login, IpAddr, time);
    }

    @PermitAll
    @Override
    public String updateCorrectAuthenticateInfo(String login, String IpAddr, LocalDateTime time) throws BaseAppException {
        return accountManager.updateCorrectAuthenticateInfo(login, IpAddr, time);
    }

    @PermitAll
    @Override
    public void sendAuthenticationCodeEmail(String login) throws BaseAppException {
        accountManager.sendAuthenticationCodeEmail(login);
    }

    @PermitAll
    @Override
    public String authWCodeUpdateCorrectAuthenticateInfo(String login, String code, String IpAddr, LocalDateTime time) throws BaseAppException{
        return accountManager.authWCodeUpdateCorrectAuthenticateInfo(login, code, IpAddr, time);
    }

    @RolesAllowed("authenticatedUser")
    @Override
    public String refreshToken(String token) throws BaseAppException {
        return accountManager.refreshJWTToken(token);
    }
}
