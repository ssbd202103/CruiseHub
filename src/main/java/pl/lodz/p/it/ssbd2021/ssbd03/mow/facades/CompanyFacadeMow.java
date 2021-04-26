package pl.lodz.p.it.ssbd2021.ssbd03.mow.facades;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Company;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.facades.AbstractFacade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class CompanyFacadeMow extends AbstractFacade<Company> {

    @PersistenceContext(unitName = "ssbd03mowPU")
    private EntityManager em;

    public CompanyFacadeMow() {
        super(Company.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
