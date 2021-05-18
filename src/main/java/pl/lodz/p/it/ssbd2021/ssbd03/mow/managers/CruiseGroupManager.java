package pl.lodz.p.it.ssbd2021.ssbd03.mow.managers;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.CruiseGroup;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.facades.CruiseGroupFacadeMow;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Klasa która zarządza logiką biznesową grupy wycieczek
 */
@Stateful
public class CruiseGroupManager implements CruiseGroupManagerLocal {

    @EJB
   private CruiseGroupFacadeMow cruiseGroupFacadeMow;

    //TODO mozliwe wypisanie wszytchi grup wycieczek.
/*    @Override
    public List<CruiseGroup> getAllCruiseGroups() {
        return cruiseGroupFacadeMow.findAll().stream().map(o -> (CruiseGroup) o).collect(Collectors.toList());
    }*/
}