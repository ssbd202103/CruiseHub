package pl.lodz.p.it.ssbd2021.ssbd03.mow.managers;


import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.AlterType;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.wrappers.AlterTypeWrapper;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Cruise;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.CruiseGroup;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.CruiseManagerException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.FacadeException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.facades.AccountFacadeMow;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.facades.CruiseFacadeMow;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.facades.CruiseGroupFacadeMow;
import pl.lodz.p.it.ssbd2021.ssbd03.utils.interceptors.TrackingInterceptor;

import javax.annotation.security.PermitAll;
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
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.*;

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

    @RolesAllowed("addCruise")
    @Override
    public void addCruise(Cruise cruise, UUID cruiseGroupUUID) throws BaseAppException {
        LocalDateTime localDateTime = LocalDateTime.now();
        if(cruise.getStartDate().isBefore(localDateTime)){
            throw new CruiseManagerException(START_DATE_BEFORE_CURRENT_DATE);
        }
        if(cruise.getStartDate().isAfter(cruise.getEndDate())){
            throw new CruiseManagerException(START_DATE_AFTER_END_DATE);
        }

        CruiseGroup cruiseGroup = cruiseGroupFacadeMow.findByUUID(cruiseGroupUUID);
        if(!cruiseGroup.isActive()){
            throw new CruiseManagerException(CRUISE_GROUP_NO_ACTIVE);
        }
        cruise.setCruisesGroup(cruiseGroup);
        setAlterTypeAlterCruiseAndCreatedBy(cruise, accountFacade.getAlterTypeWrapperByAlterType(AlterType.INSERT),
                accountFacade.findByLogin(securityContext.getUserPrincipal().getName()));
        cruiseFacadeMow.create(cruise);

    }

    @RolesAllowed("deactivateCruise")
    @Override
    public void deactivateCruise(UUID uuid, Long version) throws BaseAppException {
        Account account = accountFacade.findByLogin(securityContext.getUserPrincipal().getName());
        Cruise cruise = cruiseFacadeMow.findByUUID(uuid);
        if (!(cruise.getVersion() == version)) {
            throw FacadeException.optimisticLock();
        }
        cruise.setActive(false);

        setAlterTypeAndAlterCruise(cruise, accountFacade.getAlterTypeWrapperByAlterType(AlterType.UPDATE), account);
    }

    @RolesAllowed("editCruise")
    @Override
    public void editCruise(String description, LocalDateTime startDate, LocalDateTime endDate, UUID uuid, Long version) throws BaseAppException {
        Cruise cruiseToEdit = cruiseFacadeMow.findByUUID(uuid);
        if(!(version == cruiseToEdit.getVersion())) {
            throw FacadeException.optimisticLock();
        }
//        cruiseToEdit.setDescription(description);
        cruiseToEdit.setEndDate(endDate);
        cruiseToEdit.setStartDate(startDate);
        setAlterTypeAndAlterCruise(cruiseToEdit, accountFacade.getAlterTypeWrapperByAlterType(AlterType.UPDATE),
                accountFacade.findByLogin(securityContext.getUserPrincipal().getName()));
    }


    private void setAlterTypeAndAlterCruise(Cruise cruise, AlterTypeWrapper alterTypeWrapper, Account alteredBy) {
        cruise.setAlteredBy(alteredBy);
        cruise.setAlterType(alterTypeWrapper);
        cruise.setLastAlterDateTime(LocalDateTime.now());
    }
    private void setAlterTypeAlterCruiseAndCreatedBy(Cruise cruise, AlterTypeWrapper alterTypeWrapper, Account alteredBy) {
        cruise.setCreatedBy(alteredBy);
        cruise.setAlteredBy(alteredBy);
        cruise.setAlterType(alterTypeWrapper);
        cruise.setLastAlterDateTime(LocalDateTime.now());
    }

    @PermitAll
    @Override
    public Cruise getCruise(UUID uuid) throws BaseAppException {
        return cruiseFacadeMow.findByUUID(uuid);
    }

    @PermitAll
    @Override
    public List<Cruise> getCruisesByCruiseGroup(UUID uuid) throws BaseAppException {
        return cruiseFacadeMow.findByCruiseGroupUUID(uuid);
    }

    @RolesAllowed("publishCruise")
    @Override
    public void publishCruise(long cruiseVersion, UUID cruiseUuid) throws BaseAppException {
        // todo finish implementation
    }

    @PermitAll
    @Override
    public List<Cruise> getPublishedCruises() {
        return cruiseFacadeMow.getPublishedCruises();
    }

}
