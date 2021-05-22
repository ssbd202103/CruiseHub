package pl.lodz.p.it.ssbd2021.ssbd03.mow.facades;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.CruiseGroup;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Reservation;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.FacadeException;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.facades.AbstractFacade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.sql.ResultSet;
import java.util.List;

@Stateless
public class ReservationFacadeMow extends AbstractFacade<Reservation> {

    @PersistenceContext(unitName = "ssbd03mowPU")
    private EntityManager em;

    public ReservationFacadeMow() {
        super(Reservation.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public List<Reservation> findAll() throws FacadeException {
        return super.findAll();
    }

    @Override
    public void edit(Reservation entity) throws FacadeException {
        super.edit(entity);
    }

    @Override
    public void create(Reservation entity) throws FacadeException {
        super.create(entity);
    }

    public Reservation findByID(long id) throws BaseAppException {
        TypedQuery<Reservation> tq = em.createNamedQuery("Reservation.findByID", Reservation.class);
        tq.setParameter("id", id);
        try {
            return tq.getSingleResult();
        } catch (NoResultException e) {
            throw FacadeException.noSuchElement();
        }
    }
    public List<Reservation> findCruiseReservations(long id) throws BaseAppException {
        TypedQuery<Reservation> tq = em.createNamedQuery("Reservation.findCruiseReservations", Reservation.class);
        tq.setParameter("id", id);
        try {
            return tq.getResultList();
        } catch (NoResultException e) {
            throw FacadeException.noSuchElement();
        }
    }

    public List<Reservation> findWorkerCruiseReservations(long id) throws BaseAppException {
        //TODO Metoda znajdujaca id uzytkownika który jest aktualnie uzytkujacy jako bisnez worker
        //
        //
        TypedQuery<Reservation> tq = em.createNamedQuery("Reservation.findWorkerCruiseReservations", Reservation.class);
        tq.setParameter("id", id);
        try {
            return tq.getResultList();
        } catch (NoResultException e) {
            throw FacadeException.noSuchElement();
        }
    }

}