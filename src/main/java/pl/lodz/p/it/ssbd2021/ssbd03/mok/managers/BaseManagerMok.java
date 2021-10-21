package pl.lodz.p.it.ssbd2021.ssbd03.mok.managers;

import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.AlterType;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.BaseEntity;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.wrappers.AlterTypeWrapper;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.facades.AccountFacadeMok;
import pl.lodz.p.it.ssbd2021.ssbd03.utils.interceptors.TrackingInterceptor;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBContext;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;

@Log
@Stateful
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Interceptors(TrackingInterceptor.class)
public class BaseManagerMok {

    @Resource
    private EJBContext context;

    @Inject
    private AccountFacadeMok accountFacade;

    protected void setUpdatedMetadataWithModifier(Account modifier, BaseEntity... entities) throws BaseAppException {
        AlterTypeWrapper update = accountFacade.getAlterTypeWrapperByAlterType(AlterType.UPDATE);
        for (BaseEntity e : entities) {
            e.setAlterType(update);
            e.setAlteredBy(modifier);
        }
    }

    protected void setUpdatedMetadata(BaseEntity... entities) throws BaseAppException {
        AlterTypeWrapper update = accountFacade.getAlterTypeWrapperByAlterType(AlterType.UPDATE);
        for (BaseEntity e : entities) {
            e.setAlterType(update);
            e.setAlteredBy(getCurrentUser());
        }
    }

    protected void setCreatedMetadata(Account creator, BaseEntity... entities) throws BaseAppException {
        AlterTypeWrapper insert = accountFacade.getAlterTypeWrapperByAlterType(AlterType.INSERT);
        for (BaseEntity e : entities) {
            e.setAlterType(insert);
            e.setAlteredBy(creator);
            e.setCreatedBy(creator);
        }
    }

    @RolesAllowed("authenticatedUser")
    public Account getCurrentUser() throws BaseAppException {
        return accountFacade.findByLogin(context.getCallerPrincipal().getName());
    }
}
