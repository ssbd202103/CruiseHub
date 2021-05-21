package pl.lodz.p.it.ssbd2021.ssbd03.mok.facades;

import org.hibernate.exception.ConstraintViolationException;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Company;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.CompanyFacadeException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.FacadeException;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import static pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Company.NIP_NAME_CONSTRAINT;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class CompanyFacadeMok extends AbstractFacade<Company> {

    @PersistenceContext(unitName = "ssbd03mokPU")
    private EntityManager em;

    public CompanyFacadeMok() {
        super(Company.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Company getCompanyByName(String companyName) throws BaseAppException {
        TypedQuery<Company> tq = em.createNamedQuery("Company.findByName", Company.class);
        tq.setParameter("name", companyName);
        try {
            return tq.getSingleResult();
        } catch (NoResultException e) {
            throw FacadeException.noSuchElement();
        }
    }

    @Override
    public void create(Company entity) throws FacadeException {
        try {
            super.create(entity);
        } catch (ConstraintViolationException e) {
            if (e.getConstraintName().equals(NIP_NAME_CONSTRAINT)) {
                throw CompanyFacadeException.nipNameReserved(e);
            }
        }
    }

    @Override
    public void edit(Company entity) throws FacadeException {
        try {
            super.edit(entity);
        } catch (ConstraintViolationException e) {
            if (e.getConstraintName().equals(NIP_NAME_CONSTRAINT)) {
                throw CompanyFacadeException.nipNameReserved(e);
            }
        }
    }
}
