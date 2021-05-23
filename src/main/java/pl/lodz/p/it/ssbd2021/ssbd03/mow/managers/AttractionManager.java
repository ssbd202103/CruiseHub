package pl.lodz.p.it.ssbd2021.ssbd03.mow.managers;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Attraction;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.facades.AttractionFacadeMow;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.inject.Inject;
import java.util.List;

/**
 * Klasa która zarządza logiką biznesową atrakcji
 */
@Stateful
public class AttractionManager implements AttractionManagerLocal {

    @Inject
    private AttractionFacadeMow attractionFacadeMow;

    @Override
    public List<Attraction> getAllAttractions() throws BaseAppException {
        return attractionFacadeMow.findAll();
    }

    @Override
    public void deleteAttraction(String name) throws BaseAppException {
        Attraction attraction = this.attractionFacadeMow.findByName(name);
        //TODO sprawdzenie czy atrakcja nie jest zarezerwowana
        if (attraction != null) {   //if na te chwile do zmiany

        } else {
            this.attractionFacadeMow.deleteAttraction(name);
        }
    }


    @RolesAllowed("addAttraction")
    @Override
    public void addAttraction(Attraction attraction) throws BaseAppException {
        throw new UnsupportedOperationException();
    }

    @RolesAllowed("editAttraction")
    @Override
    public void editAttraction(Attraction attraction) throws BaseAppException {
        throw new UnsupportedOperationException();
    }


}