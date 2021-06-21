package pl.lodz.p.it.ssbd2021.ssbd03.mow.managers;

import pl.lodz.p.it.ssbd2021.ssbd03.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.AlterType;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.BaseEntity;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.wrappers.AlterTypeWrapper;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevel;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevelType;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.BusinessWorker;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.*;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.AccountManagerException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.CruiseGroupManagerException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.FacadeException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.facades.AccountFacadeMow;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.facades.CompanyFacadeMow;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.facades.CruiseFacadeMow;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.facades.CruiseGroupFacadeMow;
import pl.lodz.p.it.ssbd2021.ssbd03.utils.interceptors.TrackingInterceptor;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.*;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.IntegrityUtils.checkForOptimisticLock;


/**
 * Klasa która zarządza logiką biznesową grupy wycieczek
 */
@Stateful
@Interceptors(TrackingInterceptor.class)
public class CruiseGroupManager extends BaseManagerMow implements CruiseGroupManagerLocal {

    @Inject
    private CruiseGroupFacadeMow cruiseGroupFacadeMow;
    @Inject
    private CompanyFacadeMow companyFacadeMow;
    @Inject
    private AccountFacadeMow accountFacadeMow;
    @Inject
    private CruiseFacadeMow cruiseFacadeMow;

    @Context
    private SecurityContext context;


    @RolesAllowed("addCruiseGroup")
    @Override
    public void addCruiseGroup(String companyName, String name, long number_of_seats, Double price, CruiseAddress start_address, List<CruisePicture> pictures, String description) throws BaseAppException {
        Company company = companyFacadeMow.findByName(companyName);
        BusinessWorker worker = (BusinessWorker) accountFacadeMow.findByLogin(context.getUserPrincipal().getName())
        .getAccessLevels().stream()
                .filter(accessLevel -> accessLevel.getAccessLevelType() == AccessLevelType.BUSINESS_WORKER).findFirst().get();
        if(worker.getCompany().getName() != company.getName())
        {
            throw new CruiseGroupManagerException(OPERATION_NOT_AUTHORIZED_ERROR);
        }
        CruiseGroup cruisegroup = new CruiseGroup(company, start_address, name, number_of_seats, price, pictures, description);
        setCreatedMetadata(accountFacadeMow.findByLogin(context.getUserPrincipal().getName()), cruisegroup);
        setCreatedMetadata(accountFacadeMow.findByLogin(context.getUserPrincipal().getName()), start_address);
        for (CruisePicture picture : pictures
        ) {
            setCreatedMetadata(accountFacadeMow.findByLogin(context.getUserPrincipal().getName()), picture);
        }
        cruiseGroupFacadeMow.create(cruisegroup);
    }

    @RolesAllowed("authenticatedUser")
    public Account getCurrentUser() throws BaseAppException {
        return accountFacadeMow.findByLogin(context.getUserPrincipal().getName());
    }

    @RolesAllowed("changeCruiseGroup")
    @Override
    public void changeCruiseGroup(String name, long number_of_seats, Double price, CruiseAddress start_address, long version, String description, CruisePicture picture, UUID uuid) throws BaseAppException {

        CruiseGroup cruiseGroup = cruiseGroupFacadeMow.findByUUID(uuid);
        checkForOptimisticLock(cruiseGroup, version);
        BusinessWorker worker = (BusinessWorker) accountFacadeMow.findByLogin(context.getUserPrincipal().getName())
                .getAccessLevels().stream()
                .filter(accessLevel -> accessLevel.getAccessLevelType() == AccessLevelType.BUSINESS_WORKER).findFirst().get();
        if(worker.getCompany().getName() != cruiseGroup.getCompany().getName())
        {
            throw new CruiseGroupManagerException(OPERATION_NOT_AUTHORIZED_ERROR);
        }
        if(cruiseFacadeMow.findByCruiseGroupUUID(cruiseGroup.getUuid()).isEmpty()){
            throw new CruiseGroupManagerException(OPERATION_NOT_AUTHORIZED_ERROR);
        }

        cruiseGroup.setName(name);
        cruiseGroup.setNumberOfSeats(number_of_seats);
        cruiseGroup.setPrice(price);
        CruiseAddress target_adress = cruiseGroup.getAddress();
        target_adress.setStreet(start_address.getStreet());
        target_adress.setStreetNumber(start_address.getStreetNumber());
        target_adress.setHarborName(start_address.getHarborName());
        target_adress.setCityName(start_address.getCityName());
        target_adress.setCountryName(start_address.getCountryName());
        cruiseGroup.setDescription(description);
        setUpdatedMetadata(cruiseGroup);
        setUpdatedMetadata(start_address);
        if (!picture.getImg().isEmpty()) {
            if (cruiseGroup.getCruisePictures().isEmpty()) {
                setCreatedMetadata(accountFacadeMow.findByLogin(context.getUserPrincipal().getName()), picture);
                cruiseGroup.getCruisePictures().add(picture);
                setUpdatedMetadata(cruiseGroup.getCruisePictures().get(0));
            } else {
                cruiseGroup.getCruisePictures().get(0).setImg(picture.getImg());
                setUpdatedMetadata(cruiseGroup.getCruisePictures().get(0));
            }
        }


    }


    @RolesAllowed("getAllCruiseGroupList")
    @Override
    public List<CruiseGroup> getAllCruiseGroups() throws FacadeException {
        return cruiseGroupFacadeMow.findAll();
    }

    @RolesAllowed("getAllCruiseGroupList")
    @Override
    public List<Cruise> getCruiseBelongsToCruiseGroup(CruiseGroup cruiseGroup) throws BaseAppException {
        BusinessWorker worker = (BusinessWorker) accountFacadeMow.findByLogin(context.getUserPrincipal().getName())
                .getAccessLevels().stream()
                .filter(accessLevel -> accessLevel.getAccessLevelType() == AccessLevelType.BUSINESS_WORKER).findFirst().get();
        if(worker.getCompany().getName() != cruiseGroup.getCompany().getName())
        {
            throw new CruiseGroupManagerException(OPERATION_NOT_AUTHORIZED_ERROR);
        }
        return cruiseGroupFacadeMow.findCruisesForCruiseGroup(cruiseGroup);
    }

    @RolesAllowed("deactivateCruiseGroup")
    @Override
    public CruiseGroup deactivateCruiseGroup(UUID uuid, Long version) throws BaseAppException { //todo refactor this method
        CruiseGroup cruiseGroup = this.cruiseGroupFacadeMow.findByUUID(uuid);
        checkForOptimisticLock(cruiseGroup, version);

        Account account = getCurrentUser();

        if (!cruiseGroup.isActive()) {
            throw new AccountManagerException(CRUISE_GROUP_ALREADY_DEACTIVATED);
        }
        try {
            Optional<AccessLevel> accessLevelAdministrator = account.getAccessLevels().stream().filter(accessLevel -> accessLevel.getAccessLevelType() == AccessLevelType.ADMINISTRATOR).findFirst();
            if (accessLevelAdministrator.isEmpty()) {
                try {
                    Optional<AccessLevel> accessLevelBusinessWorker = account.getAccessLevels().stream().filter(accessLevel -> accessLevel.getAccessLevelType() == AccessLevelType.BUSINESS_WORKER).findFirst();
                    if (accessLevelBusinessWorker.isEmpty()) {
                        throw new CruiseGroupManagerException(NOT_CORRECT_ACCESS_LEVEL);
                    }
                    BusinessWorker businessWorker = (BusinessWorker) accessLevelBusinessWorker.get();
                    if (cruiseGroup.getCompany().getNIP() != businessWorker.getCompany().getNIP()) {
                        throw new CruiseGroupManagerException(BUSINESS_WORKER_DONT_OWN_CRUISE_GROUP);
                    }
                } catch (ClassCastException e) {
                    throw new CruiseGroupManagerException(CANNOT_FIND_ACCESS_LEVEL);
                }
            }
        } catch (ClassCastException e) {
            throw new CruiseGroupManagerException(CANNOT_FIND_ACCESS_LEVEL);
        }
        cruiseGroup.setActive(false);
        setUpdatedMetadata(cruiseGroup);
        cruiseGroupFacadeMow.edit(cruiseGroup);
        return cruiseGroup;
    }

    @RolesAllowed("getCruiseGroupForBusinessWorker")
    @Override
    public List<CruiseGroup> getCruiseGroupForBusinessWorker(String companyName) throws BaseAppException {
        Company company = companyFacadeMow.findByName(companyName);
        BusinessWorker worker = (BusinessWorker) accountFacadeMow.findByLogin(context.getUserPrincipal().getName())
                .getAccessLevels().stream()
                .filter(accessLevel -> accessLevel.getAccessLevelType() == AccessLevelType.BUSINESS_WORKER).findFirst().get();
        if(worker.getCompany().getName() != company.getName())
        {
            throw new CruiseGroupManagerException(OPERATION_NOT_AUTHORIZED_ERROR);
        }
        return cruiseGroupFacadeMow.getCruiseGroupForBusinessWorker(company);
    }
}

