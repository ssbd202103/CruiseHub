package pl.lodz.p.it.ssbd2021.ssbd03.mok.facades;

import org.hibernate.exception.ConstraintViolationException;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Company;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.CompanyFacadeException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.FacadeException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import static pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Company.NIP_NAME_CONSTRAINT;

@Stateless
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
        return tq.getSingleResult();
    }

    @Override
    public void create(Company entity) throws FacadeException {
        super.create(entity);
    }

    @Override
    public void edit(Company entity) throws FacadeException {
        super.edit(entity);
    }
}
