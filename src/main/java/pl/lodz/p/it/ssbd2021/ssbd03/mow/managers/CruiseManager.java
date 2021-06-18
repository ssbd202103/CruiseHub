package pl.lodz.p.it.ssbd2021.ssbd03.mow.managers;


import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.AlterType;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.wrappers.AlterTypeWrapper;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevel;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevelType;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.BusinessWorker;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Cruise;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.CruiseGroup;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.CruiseManagerException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.FacadeException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.facades.AccountFacadeMow;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.facades.CompanyFacadeMow;
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
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
    private CompanyFacadeMow companyFacade;

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
        Account account = accountFacade.findByLogin(securityContext.getUserPrincipal().getName());
        try {
            Optional<AccessLevel> accessLevelBusinessWorker = account.getAccessLevels().stream().filter(accessLevel -> accessLevel.getAccessLevelType() == AccessLevelType.BUSINESS_WORKER).findFirst();
            if(accessLevelBusinessWorker.isEmpty()) {
                throw new CruiseManagerException(CANNOT_FIND_ACCESS_LEVEL);
            }
            BusinessWorker businessWorker = (BusinessWorker) accessLevelBusinessWorker.get();
            if(cruise.getCruisesGroup().getCompany().getNIP() != businessWorker.getCompany().getNIP()) {
                throw new CruiseManagerException(BUSINESS_WORKER_ADD_CRUISE_TO_BAD_COMPANY);
            }
        } catch (ClassCastException e)  {
            throw new CruiseManagerException(CANNOT_FIND_ACCESS_LEVEL);
        }
        setAlterTypeAlterCruiseAndCreatedBy(cruise, accountFacade.getAlterTypeWrapperByAlterType(AlterType.INSERT),
                account);
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

        if(!cruise.isActive()) {
            throw new CruiseManagerException(CRUISE_ALREADY_BLOCKED);
        }

        if(!cruise.isPublished()) {
            throw new CruiseManagerException(CANNOT_BLOCK_THIS_CRUISE);
        }

        try {
            Optional<AccessLevel> accessLevelBusinessWorker = account.getAccessLevels().stream().filter(accessLevel -> accessLevel.getAccessLevelType() == AccessLevelType.BUSINESS_WORKER).findFirst();
            if(accessLevelBusinessWorker.isEmpty()) {
                throw new CruiseManagerException(CANNOT_FIND_ACCESS_LEVEL);
            }
            BusinessWorker businessWorker = (BusinessWorker) accessLevelBusinessWorker.get();
            if(cruise.getCruisesGroup().getCompany().getNIP() != businessWorker.getCompany().getNIP()) {
                throw new CruiseManagerException(BUSINESS_WORKER_ADD_CRUISE_TO_BAD_COMPANY);
            }
        } catch (ClassCastException e)  {
            throw new CruiseManagerException(CANNOT_FIND_ACCESS_LEVEL);
        }

        cruise.setActive(false);
        setAlterTypeAndAlterCruise(cruise, accountFacade.getAlterTypeWrapperByAlterType(AlterType.UPDATE), account);
    }

    @RolesAllowed("editCruise")
    @Override
    public void editCruise(LocalDateTime startDate, LocalDateTime endDate, UUID uuid, Long version) throws BaseAppException {
        Cruise cruiseToEdit = cruiseFacadeMow.findByUUID(uuid);
        Account account = accountFacade.findByLogin(securityContext.getUserPrincipal().getName());

        if(!(version == cruiseToEdit.getVersion())) {
            throw FacadeException.optimisticLock();
        }
        if(!cruiseToEdit.isActive() || cruiseToEdit.isPublished()) {
            throw new CruiseManagerException(CANNOT_EDIT_THIS_CRUISE);
        }

        try {
            Optional<AccessLevel> accessLevelBusinessWorker = account.getAccessLevels().stream().filter(accessLevel -> accessLevel.getAccessLevelType() == AccessLevelType.BUSINESS_WORKER).findFirst();
           if(accessLevelBusinessWorker.isEmpty()) {
               throw new CruiseManagerException(CANNOT_FIND_ACCESS_LEVEL);
           }
           BusinessWorker businessWorker = (BusinessWorker) accessLevelBusinessWorker.get();
            if(cruiseToEdit.getCruisesGroup().getCompany().getNIP() != businessWorker.getCompany().getNIP()) {
                throw new CruiseManagerException(BUSINESS_WORKER_ADD_CRUISE_TO_BAD_COMPANY);
            }
        } catch (ClassCastException e)  {
            throw new CruiseManagerException(CANNOT_FIND_ACCESS_LEVEL);
        }

        cruiseToEdit.setEndDate(endDate);
        cruiseToEdit.setStartDate(startDate);
        setAlterTypeAndAlterCruise(cruiseToEdit, accountFacade.getAlterTypeWrapperByAlterType(AlterType.UPDATE),
                account);
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
        Cruise cruise = cruiseFacadeMow.findByUUID(cruiseUuid);
        if(cruise.getVersion() != cruiseVersion){
            throw FacadeException.optimisticLock();
        }
        cruise.setPublished(true);
        cruiseFacadeMow.edit(cruise);
    }

    @PermitAll
    @Override
    public List<Cruise> getPublishedCruises() {
        return cruiseFacadeMow.getPublishedCruises();
    }

}
