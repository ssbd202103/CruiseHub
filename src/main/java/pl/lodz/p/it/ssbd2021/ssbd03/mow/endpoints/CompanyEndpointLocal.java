package pl.lodz.p.it.ssbd2021.ssbd03.mow.endpoints;

import pl.lodz.p.it.ssbd2021.ssbd03.common.dto.MetadataDto;
import pl.lodz.p.it.ssbd2021.ssbd03.common.endpoints.TransactionalEndpoint;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.BusinessWorkerDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.companies.AddCompanyDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.companies.CompanyLightDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mow.dto.companies.CompanyDto;

import javax.ejb.Local;
import java.util.List;
import java.util.UUID;

/**
 * Interfejs który zajmuje się gromadzeniem zmapowanych obiektów klas Dto na obiekty klas modelu związanych z firmami oraz wywołuje metody logiki przekazując zmapowane obiekty.
 */
@Local
public interface CompanyEndpointLocal extends TransactionalEndpoint {
    /**
     * Zwraca liste obiektów dto prezentujące informacje o fimach niezbędne dla utworzenia użytkownika z poziomem dostępu BusinessWorker
     *
     * @return lista obiektów reprezentjace informacje o fimach niezbędne dla utworzenia użytkownika z poziomem dostępu BusinessWorker
     */
    List<CompanyLightDto> getCompaniesInfo() throws BaseAppException;

    /**
     * Zwraca listę pracowników firmy
     *
     * @param companyName Nazwa firmy
     * @return Reprezentacja Dto pracownika firmy
     * @throws BaseAppException bazowy wyjątek aplikacji, zwracany w przypadku nieznalezienia firmy
     *                          lub naruszenia zasad biznesowych
     */
    List<BusinessWorkerDto> getBusinessWorkersForCompany(String companyName) throws BaseAppException;

    /**
     * Zwraca lista obiektów dto prezentujące informacje o fimach
     *
     * @return lista obiektów dto prezentujące informacje o fimach
     */
    List<CompanyDto> getAllCompanies() throws BaseAppException;

    /**
     * Metoda sluzaca do dodania nowej firmy
     * @param addCompanyDto obiekt klasy AddCompanyDto zawierajacy informacje od uzytkownika potrzebne do utworzenia nowej firmy
     * @throws BaseAppException bazowy wyjatek aplikacji
     */
    void addCompany(AddCompanyDto addCompanyDto) throws BaseAppException;

    /**
     * Pobiera metadane firmy
     *
     * @param nip nip firmy wybranej do metadanych
     * @return Reprezentacja DTO metadanych
     * @throws BaseAppException Bazowy wyjątek aplikacji
     */
    MetadataDto getCompanyMetadata(long nip) throws BaseAppException;
}
