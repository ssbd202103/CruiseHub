package pl.lodz.p.it.ssbd2021.ssbd03.mok.endpoints;


import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.managers.AccountManagerLocal;

import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.annotation.security.RunAs;
import javax.ejb.*;
import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.time.LocalDateTime;

/**
 * Klasa która zajmuje się uwierzytelnieniem użytkownika.
 */
@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@PermitAll
public class AuthenticateEndpoint implements AuthenticateEndpointLocal {

    @Inject
    private AccountManagerLocal accountManager;


    @Override
    public void updateIncorrectAuthenticateInfo(String login, String IpAddr, LocalDateTime time) throws BaseAppException {
        accountManager.updateIncorrectAuthenticateInfo(login, IpAddr, time);
    }

    @Override
    public String updateCorrectAuthenticateInfo(String login, String IpAddr, LocalDateTime time) throws BaseAppException {
        return accountManager.updateCorrectAuthenticateInfo(login, IpAddr, time);
    }
}
