package pl.lodz.p.it.ssbd2021.ssbd03.mok.endpoints;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Address;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.Administrator;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.BusinessWorker;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.Client;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.Moderator;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.*;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata.AccountChangeEmailDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata.AdministratorChangeDataDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata.BusinessWorkerChangeDataDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata.ClientChangeDataDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata.ModeratorChangeDataDto;
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
    public void changeEmail(AccountChangeEmailDto accountChangeEmailDto) {
        accountManager.changeEmail(accountChangeEmailDto.getLogin(), accountChangeEmailDto.getVersion(), accountChangeEmailDto.getNewEmail());
    }

    @Override
    public void changeClientData(ClientChangeDataDto clientChangeDataDto) throws OptimisticLockException, BaseAppException {
        Long version = getAccountByLogin(clientChangeDataDto.getLogin()).getVersion();

        if (!version.equals(clientChangeDataDto.getVersion())) {
            throw new OptimisticLockException(OPTIMISTIC_EXCEPTION);
        }

        Account account = AccountMapper.extractAccountFromClientChangeDataDto(clientChangeDataDto);
        accountManager.changeClientData(account);
    }

    @Override
    public void changeBusinessWorkerData(BusinessWorkerChangeDataDto businessWorkerChangeDataDto) throws OptimisticLockException, BaseAppException {
        Long version = getAccountByLogin(businessWorkerChangeDataDto.getLogin()).getVersion();

        if (!version.equals(businessWorkerChangeDataDto.getVersion())) {
            throw new OptimisticLockException(OPTIMISTIC_EXCEPTION);
        }

        Account account = AccountMapper.extractAccountFromBusinessWorkerChangeDataDto(businessWorkerChangeDataDto);
        accountManager.changeBusinessWorkerData(account);
    }

    @Override
    public void changeModeratorData(ModeratorChangeDataDto moderatorChangeDataDto) throws OptimisticLockException, BaseAppException {
        Long version = getAccountByLogin(moderatorChangeDataDto.getLogin()).getVersion();

        if (!version.equals(moderatorChangeDataDto.getVersion())) {
            throw new OptimisticLockException(OPTIMISTIC_EXCEPTION);
        }

        Account account = AccountMapper.extractAccountFromModeratorChangeDataDto(moderatorChangeDataDto);
        accountManager.changeModeratorData(account);
    }

    @Override
    public void changeAdministratorData(AdministratorChangeDataDto administratorChangeDataDto) throws OptimisticLockException, BaseAppException {
        Long version = getAccountByLogin(administratorChangeDataDto.getLogin()).getVersion();

        if (!version.equals(administratorChangeDataDto.getVersion())) {
            throw new OptimisticLockException(OPTIMISTIC_EXCEPTION);
        }

        Account account = AccountMapper.extractAccountFromAdministratorChangeDataDto(administratorChangeDataDto);
        accountManager.changeAdministratorData(account);
    }

    @Override
    public String getETagFromSignableEntity(SignableEntity entity) {
        return EntityIdentitySignerVerifier.calculateEntitySignature(entity);
    }

    @Override
    public AccountDto getAccountByLogin(String login) throws BaseAppException {
        return AccountMapper.toAccountDto(accountManager.getAccountByLogin(login));
    }

    @Override
    public ClientDto getClientByLogin(String login) throws BaseAppException {
        return AccountMapper.toClientDto(accountManager.getAccountByLogin(login));
    }

    @Override
    public BusinessWorkerDto getBusinessWorkerByLogin(String login) throws BaseAppException {
        return AccountMapper.toBusinessWorkerDto(accountManager.getAccountByLogin(login));
    }

    @Override
    public ModeratorDto getModeratorByLogin(String login) throws BaseAppException {
        return AccountMapper.toModeratorDto(accountManager.getAccountByLogin(login));
    }

    @Override
    public AdministratorDto getAdministratorByLogin(String login) throws BaseAppException {
        return AccountMapper.toAdministratorDto(accountManager.getAccountByLogin(login));
    }


}
