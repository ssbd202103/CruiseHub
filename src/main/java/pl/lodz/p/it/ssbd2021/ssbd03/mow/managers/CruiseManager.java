package pl.lodz.p.it.ssbd2021.ssbd03.mow.managers;


import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.AlterType;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.wrappers.AlterTypeWrapper;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Cruise;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.NewCruiseDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.facades.AccountFacade;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.facades.CompanyFacadeMow;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.facades.CruiseFacade;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.core.SecurityContext;
import java.time.LocalDateTime;

/**
 * Klasa która zarządza logiką biznesową wycieczek
 */
public class CruiseManager implements CruiseManagerLocal {


    @Inject
    SecurityContext securityContext;

    @EJB
    private CruiseFacade cruiseFacade;

    @EJB
    private AccountFacade accountFacade;

    @Override
    public void addCruise(Cruise cruise) throws BaseAppException {
        cruiseFacade.create(cruise);
        Account account = accountFacade.findByLogin(securityContext.getUserPrincipal().getName());
        setAlterTypeAndAlterAccount(account, accountFacade.getAlterTypeWrapperByAlterType(AlterType.UPDATE), account);

    }
    private void setAlterTypeAndAlterAccount(Account account, AlterTypeWrapper alterTypeWrapper, Account alteredBy) {
        account.setAlteredBy(alteredBy);
        account.setAlterType(alterTypeWrapper);
        account.setLastAlterDateTime(LocalDateTime.now());
    }
}
