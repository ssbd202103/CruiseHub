package pl.lodz.p.it.ssbd2021.ssbd03.mok.facades;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.wrappers.AlterTypeWrapper;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Company;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.FacadeException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

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

    public Company getCompanyByName(String companyName) {
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
