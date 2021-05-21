package pl.lodz.p.it.ssbd2021.ssbd03.mow.managers;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Company;

import javax.ejb.Local;
import java.util.List;

/**
 * Klasa która zarządza logiką biznesową kont
 */
@Local
public interface CompanyManagerLocal {
    /**
     * Pobiera wszystkie obiekty firm z bazy
     *
     * @return the all companies
     */
    List<Company> getAllCompanies();
}
