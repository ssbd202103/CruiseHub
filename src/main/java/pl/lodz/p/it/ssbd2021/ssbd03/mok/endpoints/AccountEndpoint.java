package pl.lodz.p.it.ssbd2021.ssbd03.mok.endpoints;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Address;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.Administrator;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.BusinessWorker;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.Client;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.Moderator;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AccountDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata.AccountChangeEmailDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.AdministratorForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.BusinessWorkerForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.ClientForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.ModeratorForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.endpoints.converters.AccountMapper;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.managers.AccountManagerLocal;
import pl.lodz.p.it.ssbd2021.ssbd03.security.EntityIdentitySignerVerifier;
import pl.lodz.p.it.ssbd2021.ssbd03.security.SignableEntity;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.persistence.OptimisticLockException;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.OPTIMISTIC_EXCEPTION;

/**
 * Klasa która zajmuje się growadzeniem zmapowanych obiektów klas Dto na obiekty klas modelu związanych z kontami użytkowników i poziomami dostępu, oraz wywołuje metody logiki przekazując zmapowane obiekty.
 */
@Stateful
public class AccountEndpoint implements AccountEndpointLocal {

    @EJB
    private AccountManagerLocal accountManager;

    @Override
    public String getETagFromSignableEntity(SignableEntity entity) {
        return EntityIdentitySignerVerifier.calculateEntitySignature(entity);
    }

    @Override
    public AccountDto getAccountByLogin(String login) throws BaseAppException {
        return AccountMapper.toAccountDto(accountManager.getAccountByLogin(login));
    }

    @Override
    public void createClientAccount(ClientForRegistrationDto clientForRegistrationDto) {
        Client client = AccountMapper.extractClientFromClientForRegistrationDto(clientForRegistrationDto);
        Address address = AccountMapper.extractAddressFromClientForRegistrationDto(clientForRegistrationDto);
        Account account = AccountMapper.extractAccountFromClientForRegistrationDto(clientForRegistrationDto);
        this.accountManager.createClientAccount(account, client, address);
    }

    @Override
    public void createBusinessWorkerAccount(BusinessWorkerForRegistrationDto businessWorkerForRegistrationDto) {
        BusinessWorker businessWorker = AccountMapper.extractBusinessWorkerFromBusinessWorkerForRegistrationDto(businessWorkerForRegistrationDto);
        Account account = AccountMapper.extractAccountFromBusinessWorkerForRegistrationDto(businessWorkerForRegistrationDto);
        this.accountManager.createBusinessWorker(account, businessWorker, businessWorkerForRegistrationDto.getCompanyName());
    }

    @Override
    public void createAdministratorAccount(AdministratorForRegistrationDto administratorForRegistrationDto) {
        Account account = AccountMapper.extractAccountFromAdministratorForRegistrationDto(administratorForRegistrationDto);
        this.accountManager.createAdministrator(account, new Administrator());
    }

    @Override
    public void createModeratorAccount(ModeratorForRegistrationDto moderatorForRegistrationDto) {
        Account account = AccountMapper.extractAccountFromModeratorForRegistrationDto(moderatorForRegistrationDto);
        this.accountManager.createModerator(account, new Moderator());
    }

    @Override
    public void changeEmail(AccountChangeEmailDto accountChangeEmailDto) throws BaseAppException, OptimisticLockException {
        Long version = getAccountByLogin(accountChangeEmailDto.getLogin()).getVersion();

        if (!version.equals(accountChangeEmailDto.getVersion())) {
            throw new OptimisticLockException(OPTIMISTIC_EXCEPTION);
        }

        accountManager.changeEmail(accountChangeEmailDto.getLogin(), accountChangeEmailDto.getVersion(), accountChangeEmailDto.getNewEmail());
    }
}
