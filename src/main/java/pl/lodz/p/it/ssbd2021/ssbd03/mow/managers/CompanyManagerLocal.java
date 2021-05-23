package pl.lodz.p.it.ssbd2021.ssbd03.mow.managers;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.BusinessWorker;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Company;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;

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
    List<Company> getAllCompanies() throws BaseAppException;

     /**
     * Zwraca listę pracowników firmy
     * @param companyName Nazwa firmy
     * @return Obiekt encji poziomu dostępu danego pracownika firmy
     * @throws BaseAppException bazowy wyjątek aplikacji, zwracany w przypadku nieznalezienia firmy
     * lub naruszenia zasad biznesowych
     */
    List<BusinessWorker> getBusinessWorkersForCompany(String companyName) throws BaseAppException;
}
