package pl.lodz.p.it.ssbd2021.ssbd03.mow.managers;


import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.AlterType;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.wrappers.AlterTypeWrapper;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Cruise;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.facades.AccountFacadeMow;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.FacadeException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.NewCruiseDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.facades.CompanyFacadeMow;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.facades.CruiseFacade;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Klasa która zarządza logiką biznesową wycieczek (rejsów)
 */
public class CruiseManager implements CruiseManagerLocal {


    @Context
    private SecurityContext securityContext;

    @Inject
    private CruiseFacade cruiseFacade;

    @Inject
    private AccountFacadeMow accountFacade;

    @Override
    public void addCruise(Cruise cruise) throws BaseAppException {
        cruiseFacade.create(cruise);
        Account account = accountFacade.findByLogin(securityContext.getUserPrincipal().getName());
        setAlterTypeAndAlterAccount(account, accountFacade.getAlterTypeWrapperByAlterType(AlterType.UPDATE), account);

    }

    @Override
    public void deactivateCruise(UUID uuid, Long version) throws BaseAppException {
        Account account = accountFacade.findByLogin(securityContext.getUserPrincipal().getName());
        if (!(account.getVersion() == version)) {
            throw FacadeException.optimisticLock();
        }
        Cruise cruise = cruiseFacade.findByUUID(uuid);
        cruise.setActive(false);
        setAlterTypeAndAlterCruise(cruise , accountFacade.getAlterTypeWrapperByAlterType(AlterType.UPDATE),account);
    }

    private void setAlterTypeAndAlterAccount(Account account, AlterTypeWrapper alterTypeWrapper, Account alteredBy) {
        account.setAlteredBy(alteredBy);
        account.setAlterType(alterTypeWrapper);
        account.setLastAlterDateTime(LocalDateTime.now());
    }
    private void setAlterTypeAndAlterCruise(Cruise cruise, AlterTypeWrapper alterTypeWrapper, Account alteredBy) {
        cruise.setAlteredBy(alteredBy);
        cruise.setAlterType(alterTypeWrapper);
        cruise.setLastAlterDateTime(LocalDateTime.now());
    }



}
