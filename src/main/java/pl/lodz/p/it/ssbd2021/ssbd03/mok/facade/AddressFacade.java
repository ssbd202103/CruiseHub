package pl.lodz.p.it.ssbd2021.ssbd03.mok.facade;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Address;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class AddressFacade extends AbstractFacade<Address> {

    @PersistenceContext(unitName = "ssbd03mokPU")
    private EntityManager em;

    public AddressFacade() {
        super(Address.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
