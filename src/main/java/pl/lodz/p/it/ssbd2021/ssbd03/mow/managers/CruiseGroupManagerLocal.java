package pl.lodz.p.it.ssbd2021.ssbd03.mow.managers;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.CruiseGroup;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;

import java.util.List;
/**
 * Klasa która zarządza logiką biznesową rezerwacji
 */

public interface CruiseGroupManagerLocal {

    //TODO mozliwe wypisanie wszytchi grup wycieczek.
/*    /**
     * Pobiera wszystkie obiekty grup wycieczek z bazy
     *
     * @return the all companies
     *//*
    List<CruiseGroup> getAllCruiseGroups();*/


    /**
     * Pobiera wszystkie obiekty grup wycieczek z bazy
     *
     * @return the all companies
     */
    CruiseGroup deactivateCruiseGroup(String name, Long version) throws BaseAppException;

}
