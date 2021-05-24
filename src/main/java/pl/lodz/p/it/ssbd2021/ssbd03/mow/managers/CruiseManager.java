package pl.lodz.p.it.ssbd2021.ssbd03.mow.managers;


import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.AlterType;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.wrappers.AlterTypeWrapper;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Cruise;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.CruiseGroup;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.FacadeException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.facades.AccountFacadeMow;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.facades.CruiseFacadeMow;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.facades.CruiseGroupFacadeMow;
import pl.lodz.p.it.ssbd2021.ssbd03.utils.interceptors.TrackingInterceptor;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.Local;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Klasa która zarządza logiką biznesową wycieczek (rejsów)
 */
@Interceptors(TrackingInterceptor.class)
@Stateful
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class CruiseManager implements CruiseManagerLocal {


    @Context
    private SecurityContext securityContext;

    @Inject
    private CruiseFacadeMow cruiseFacadeMow;

    @Inject
    private AccountFacadeMow accountFacade;

    @Inject
    private CruiseGroupFacadeMow cruiseGroupFacadeMow;


    @Override
    public void addCruise(Cruise cruise, String cruiseGroupName) throws BaseAppException {
        CruiseGroup cruiseGroup = cruiseGroupFacadeMow.findByName(cruiseGroupName);
        cruise.setCruisesGroup(cruiseGroup);
        cruiseFacadeMow.create(cruise);
        Account account = accountFacade.findByLogin(securityContext.getUserPrincipal().getName());
        setAlterTypeAndAlterAccount(account, accountFacade.getAlterTypeWrapperByAlterType(AlterType.UPDATE), account);

    }

    @Override
    public void deactivateCruise(UUID uuid, Long version) throws BaseAppException {
        Account account = accountFacade.findByLogin(securityContext.getUserPrincipal().getName());
        if (!(account.getVersion() == version)) {
            throw FacadeException.optimisticLock();
        }
        Cruise cruise = cruiseFacadeMow.findByUUID(uuid);
        cruise.setActive(false);
        setAlterTypeAndAlterCruise(cruise, accountFacade.getAlterTypeWrapperByAlterType(AlterType.UPDATE), account);
    }

    @Override
    public void editCruise(String description, LocalDateTime startDate, LocalDateTime endDate, UUID uuid, Long version) throws BaseAppException {
        Cruise cruiseToEdit = cruiseFacadeMow.findByUUID(uuid);
        if(!(version == cruiseToEdit.getVersion())) {
            throw FacadeException.optimisticLock();
        }
        cruiseToEdit.setDescription(description);
        cruiseToEdit.setEndDate(endDate);
        cruiseToEdit.setStartDate(startDate);
        setAlterTypeAndAlterCruise(cruiseToEdit, accountFacade.getAlterTypeWrapperByAlterType(AlterType.UPDATE),
                accountFacade.findByLogin(securityContext.getUserPrincipal().getName()));
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

    @Override
    public Cruise getCruise(UUID uuid) throws BaseAppException {
        return cruiseFacadeMow.findByUUID(uuid);
    }

    @RolesAllowed("publishCruise")
    @Override
    public void publishCruise(long cruiseVersion, UUID cruiseUuid) throws BaseAppException {
        // todo finish implementation
    }
}
