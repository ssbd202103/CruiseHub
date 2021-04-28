package pl.lodz.p.it.ssbd2021.ssbd03.mok.endpoints;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.BusinessWorker;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.Client;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.BusinessWorkerForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.ClientForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.endpoints.converters.AccountMapper;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.managers.AccountManagerLocal;

import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 * Klasa która zajmuje się growadzeniem zmapowanych obiektów klas Dto na obiekty klas modelu związanych z kontami użytkowników i poziomami dostępu, oraz wywołuje metody logiki przekazując zmapowane obiekty.
 */
@Stateful
public class AccountEndpoint implements AccountEndpointLocal {

    @EJB
    private AccountManagerLocal accountManager;

    @Override
    public void createClientAccount(ClientForRegistrationDto clientForRegistrationDto) {
        Client client = AccountMapper.extractClientFromClientForRegistrationDto(clientForRegistrationDto);
        Account account = AccountMapper.extractAccountFromClientForRegistrationDto(clientForRegistrationDto);
        this.accountManager.createClientAccount(account, client);
    }

    @Override
    public void createBusinessWorkerAccount(BusinessWorkerForRegistrationDto businessWorkerForRegistrationDto) {
        BusinessWorker businessWorker = AccountMapper.extractBusinessWorkerFromBusinessWorkerForRegistrationDto(businessWorkerForRegistrationDto);
        Account account = AccountMapper.extractAccountFromBusinessWorkerForRegistrationDto(businessWorkerForRegistrationDto);
        this.accountManager.createBusinessWorkerAccount(account, businessWorker, businessWorkerForRegistrationDto.getCompanyName());
    }

    @Override
    public void grantModeratorAccessLevel(String accountLogin) throws BaseAppException {
        accountManager.grantModeratorAccessLevel(accountLogin);
    }

    @Override
    public void grantAdministratorAccessLevel(String accountLogin) throws BaseAppException {
        accountManager.grantAdministratorAccessLevel(accountLogin);
    }
}
