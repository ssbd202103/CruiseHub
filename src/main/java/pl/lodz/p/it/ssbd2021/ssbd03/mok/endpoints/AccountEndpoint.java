package pl.lodz.p.it.ssbd2021.ssbd03.mok.endpoints;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevelType;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.BusinessWorker;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.Client;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.EndpointException;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AccountDtoForList;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AccountDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata.OtherAccountChangeDataDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata.OtherBusinessWorkerChangeDataDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata.OtherClientChangeDataDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changes.GrantAccessLevelDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.IdDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.BusinessWorkerForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.ClientForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.endpoints.converters.AccountMapper;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.managers.AccountManagerLocal;
import pl.lodz.p.it.ssbd2021.ssbd03.security.EntityIdentitySignerVerifier;
import pl.lodz.p.it.ssbd2021.ssbd03.security.SignableEntity;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.mappers.IdMapper;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.persistence.OptimisticLockException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.ACCESS_LEVEL_NOT_ASSIGNABLE_ERROR;
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
    public AccountDto grantAccessLevel(GrantAccessLevelDto grantAccessLevelDto) throws BaseAppException {
        if (grantAccessLevelDto.getAccessLevel().equals(AccessLevelType.MODERATOR)) {
            return AccountMapper.toAccountDto(
                    accountManager.grantModeratorAccessLevel(grantAccessLevelDto.getAccountLogin(), grantAccessLevelDto.getAccountVersion())
            );
        }

        if (grantAccessLevelDto.getAccessLevel().equals(AccessLevelType.ADMINISTRATOR)) {
            return AccountMapper.toAccountDto(
                    accountManager.grantAdministratorAccessLevel(grantAccessLevelDto.getAccountLogin(), grantAccessLevelDto.getAccountVersion())
            );
        }
        throw new EndpointException(ACCESS_LEVEL_NOT_ASSIGNABLE_ERROR);
    }

    @Override
    public String getETagFromSignableEntity(SignableEntity entity) throws BaseAppException {
        return EntityIdentitySignerVerifier.calculateEntitySignature(entity);
    }

    @Override
    public AccountDto getAccountByLogin(String login) throws BaseAppException {
        return AccountMapper.toAccountDto(accountManager.getAccountByLogin(login));
    }

    @Override
    public List<AccountDtoForList> getAllAccounts() {
        return accountManager.getAllAccounts().stream().map(AccountMapper::toAccountListDto).collect(Collectors.toList());
    }

    @Override
    public void blockUser(@Valid @NotNull IdDto id) {
        this.accountManager.blockUser(IdMapper.toLong(id));
    }


    @Override
    public OtherClientChangeDataDto changeOtherClientData(OtherClientChangeDataDto otherClientChangeDataDto) throws OptimisticLockException, BaseAppException {
        Long version = getAccountByLogin(otherClientChangeDataDto.getLogin()).getVersion();

        if (!version.equals(otherClientChangeDataDto.getVersion())) {
            throw new OptimisticLockException(OPTIMISTIC_EXCEPTION);
        }

        Account account = AccountMapper.extractAccountFromClientOtherChangeDataDto(otherClientChangeDataDto);
        String alterBy = AccountMapper.extractAlterByFromOtherClientDataChange(otherClientChangeDataDto);

        return AccountMapper.accountDtoForClientDataChange(accountManager.changeOtherClientData(account,alterBy));
    }

    @Override
    public OtherBusinessWorkerChangeDataDto changeOtherBusinessWorkerData(OtherBusinessWorkerChangeDataDto otherBusinessWorkerChangeDataDto) throws OptimisticLockException, BaseAppException {
        Long version = getAccountByLogin(otherBusinessWorkerChangeDataDto.getLogin()).getVersion();

        if (!version.equals(otherBusinessWorkerChangeDataDto.getVersion())) {
            throw new OptimisticLockException(OPTIMISTIC_EXCEPTION);
        }

        Account account = AccountMapper.extractAccountFromOtherBusinessWorkerChangeDataDto(otherBusinessWorkerChangeDataDto);
        String alterBy = AccountMapper.extractAlterByFromOtherBusinessWorkerDataChange(otherBusinessWorkerChangeDataDto);
       return AccountMapper.accountDtoForBusinnesWorkerDataChange(accountManager.changeOtherBusinessWorkerData(account,alterBy));
    }

    @Override
    public AccountDto changeOtherAccountData(OtherAccountChangeDataDto otherAccountChangeDataDto) throws OptimisticLockException, BaseAppException {
        Long version = getAccountByLogin(otherAccountChangeDataDto.getLogin()).getVersion();

        if (!version.equals(otherAccountChangeDataDto.getVersion())) {
            throw new OptimisticLockException(OPTIMISTIC_EXCEPTION);
        }

        Account account = AccountMapper.extractAccountFromOtherAccountChangeDataDto(otherAccountChangeDataDto);
        String alterBy = AccountMapper.extractAlterByFromAccount(otherAccountChangeDataDto);
       return AccountMapper.toAccountDto(accountManager.changeOtherAccountData(account,alterBy));
    }

}
