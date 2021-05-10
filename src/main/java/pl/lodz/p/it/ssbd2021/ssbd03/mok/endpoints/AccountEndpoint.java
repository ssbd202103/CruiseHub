package pl.lodz.p.it.ssbd2021.ssbd03.mok.endpoints;

import org.apache.commons.codec.digest.DigestUtils;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevelType;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.BusinessWorker;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.Client;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.EndpointException;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AccountDtoForList;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.AccountDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.PasswordResetDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changes.ChangeAccessLevelStateDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changes.GrantAccessLevelDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.IdDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.detailsview.AccountDetailsViewDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.BusinessWorkerForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.ClientForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.endpoints.converters.AccountMapper;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.managers.AccountManagerLocal;
import pl.lodz.p.it.ssbd2021.ssbd03.security.EntityIdentitySignerVerifier;
import pl.lodz.p.it.ssbd2021.ssbd03.security.SignableEntity;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.ACCESS_LEVEL_NOT_ASSIGNABLE_ERROR;

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
    public AccountDto changeAccessLevelState(ChangeAccessLevelStateDto changeAccessLevelStateDto) throws BaseAppException {
        return AccountMapper.toAccountDto(
                accountManager.changeAccessLevelState(changeAccessLevelStateDto.getAccountLogin(),
                        changeAccessLevelStateDto.getAccessLevel(), changeAccessLevelStateDto.isEnabled(),
                        changeAccessLevelStateDto.getAccountVersion())
        );
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
    public AccountDetailsViewDto getAccountDetailsByLogin(String login) throws BaseAppException {
        return AccountMapper.toAccountDetailsViewDto(accountManager.getAccountByLogin(login));
    }

    @Override
    public List<AccountDtoForList> getAllAccounts() {
        return accountManager.getAllAccounts().stream().map(AccountMapper::toAccountListDto).collect(Collectors.toList());
    }

    @Override
    public void blockUser(@NotNull String login, @NotNull Long version) throws BaseAppException {
        this.accountManager.blockUser(login, version);
    }

    @Override
    public void requestPasswordReset(String login) throws BaseAppException {
        this.accountManager.requestPasswordReset(login);
    }

    @Override
    public void resetPassword(PasswordResetDto passwordResetDto) throws BaseAppException {
        this.accountManager.resetPassword(passwordResetDto.getLogin(), DigestUtils.sha256Hex(passwordResetDto.getPassword()), passwordResetDto.getToken());
    }

    @Override
    public void unblockUser(@NotNull String unblockedUserLogin, @NotNull Long version) throws BaseAppException {
        this.accountManager.unblockUser(unblockedUserLogin, version);
    }


    @Override
    public void requestSomeonesPasswordReset(String login, String email) throws BaseAppException {
        this.accountManager.requestSomeonesPasswordReset(login, email);
    }
}
