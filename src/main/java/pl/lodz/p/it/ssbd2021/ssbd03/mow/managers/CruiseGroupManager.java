package pl.lodz.p.it.ssbd2021.ssbd03.mow.managers;

import pl.lodz.p.it.ssbd2021.ssbd03.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.AlterType;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.BaseEntity;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.wrappers.AlterTypeWrapper;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Company;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.CruiseAddress;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.CruiseGroup;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.CruisePicture;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.FacadeException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.facades.AccountFacadeMow;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.facades.CompanyFacadeMow;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.facades.CruiseGroupFacadeMow;
import pl.lodz.p.it.ssbd2021.ssbd03.utils.interceptors.TrackingInterceptor;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import java.util.List;
import java.util.UUID;


/**
 * Klasa która zarządza logiką biznesową grupy wycieczek
 */
@Stateful
@Interceptors(TrackingInterceptor.class)
public class CruiseGroupManager implements CruiseGroupManagerLocal {

    @Inject
    private CruiseGroupFacadeMow cruiseGroupFacadeMow;
    @Inject
    private CompanyFacadeMow companyFacadeMow;
    @Inject
    private AccountFacadeMow accountFacadeMow;

    @Context
    private SecurityContext context;

    @Inject
    I18n i18n;

    @RolesAllowed("addCruiseGroup")
    @Override
    public void addCruiseGroup(String companyName, String name, long number_of_seats, Double price, CruiseAddress start_address, List<CruisePicture> pictures,String description) throws BaseAppException {
        Company company = companyFacadeMow.getCompanyByName(companyName);
        CruiseGroup cruisegroup = new CruiseGroup(company,start_address,name,number_of_seats,price, pictures,description);
        setCreatedMetadata(accountFacadeMow.findByLogin(context.getUserPrincipal().getName()),cruisegroup);
        setCreatedMetadata(accountFacadeMow.findByLogin(context.getUserPrincipal().getName()),start_address);
        for (CruisePicture picture :  pictures
             ) {
            setCreatedMetadata(accountFacadeMow.findByLogin(context.getUserPrincipal().getName()),picture);
        }
        cruiseGroupFacadeMow.create(cruisegroup);
    }
    private void setCreatedMetadata(Account creator, BaseEntity... entities) throws BaseAppException {
        AlterTypeWrapper insert = accountFacadeMow.getAlterTypeWrapperByAlterType(AlterType.INSERT);
        for (BaseEntity e : entities) {
            e.setAlterType(insert);
            e.setAlteredBy(creator);
            e.setCreatedBy(creator);
        }
    }

    private void setUpdatedMetadata(BaseEntity... entities) throws BaseAppException {
        AlterTypeWrapper update = accountFacadeMow.getAlterTypeWrapperByAlterType(AlterType.UPDATE);
        for (BaseEntity e : entities) {
            e.setAlterType(update);
            e.setAlteredBy(getCurrentUser());
        }
    }

    @RolesAllowed("authenticatedUser")
    public Account getCurrentUser() throws BaseAppException {
        return accountFacadeMow.findByLogin(context.getUserPrincipal().getName());
    }

    @RolesAllowed("changeCruiseGroup")
    @Override
    public CruiseGroup changeCruiseGroup(String name, long number_of_seats, Double price, CruiseAddress start_address, long version) {
        return null; //todo
    }


    @RolesAllowed("getAllCruiseGroupsList")
    @Override
    public List<CruiseGroup> getAllCruiseGroups() throws FacadeException {
        return cruiseGroupFacadeMow.findAll();
    }

    @RolesAllowed("deactivateCruiseGroup")
    @Override
    public CruiseGroup deactivateCruiseGroup(UUID uuid, Long version) throws BaseAppException {
        CruiseGroup cruiseGroup = this.cruiseGroupFacadeMow.findByUUID(uuid);
        if (!(cruiseGroup.getVersion() == version)) {
            throw FacadeException.optimisticLock();
        }
        cruiseGroup.setActive(false);
        setUpdatedMetadata(cruiseGroup);
        cruiseGroupFacadeMow.edit(cruiseGroup);
        return cruiseGroup;
    }
}

