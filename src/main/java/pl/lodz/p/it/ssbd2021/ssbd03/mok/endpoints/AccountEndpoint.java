package pl.lodz.p.it.ssbd2021.ssbd03.mok.endpoints;

import org.apache.commons.codec.digest.DigestUtils;
import pl.lodz.p.it.ssbd2021.ssbd03.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.AccessLevelType;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.BusinessWorker;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.Client;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.EndpointException;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.*;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata.*;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changes.ChangeAccessLevelStateDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changes.GrantAccessLevelDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.detailsview.AccountDetailsViewDto;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.Moderator;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata.AccountChangeEmailDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata.AdministratorChangeDataDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata.BusinessWorkerChangeDataDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata.ClientChangeDataDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.changedata.ModeratorChangeDataDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.BusinessWorkerForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.dto.registration.ClientForRegistrationDto;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.endpoints.converters.AccountMapper;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.managers.AccountManagerLocal;
import pl.lodz.p.it.ssbd2021.ssbd03.security.EntityIdentitySignerVerifier;
import pl.lodz.p.it.ssbd2021.ssbd03.security.SignableEntity;
import pl.lodz.p.it.ssbd2021.ssbd03.services.EmailService;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.persistence.OptimisticLockException;
import javax.persistence.OptimisticLockException;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.OPTIMISTIC_LOCK_EXCEPTION;

import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.ACCESS_LEVEL_NOT_ASSIGNABLE_ERROR;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.*;

/**
 * Klasa która zajmuje się growadzeniem zmapowanych obiektów klas Dto na obiekty klas modelu związanych z kontami użytkowników i poziomami dostępu, oraz wywołuje metody logiki przekazując zmapowane obiekty.
 */
@Stateful
public class AccountEndpoint implements AccountEndpointLocal {

    @EJB
    private AccountManagerLocal accountManager;

    @Context
    private SecurityContext securityContext;

    @Inject
    I18n i18n;

    @Override
    public String getETagFromSignableEntity(SignableEntity entity) {
        return EntityIdentitySignerVerifier.calculateEntitySignature(entity);
    }


    @Override
    public void createClientAccount(ClientForRegistrationDto clientForRegistrationDto) throws BaseAppException {
        Client client = AccountMapper.extractClientFromClientForRegistrationDto(clientForRegistrationDto);
        Account account = AccountMapper.extractAccountFromClientForRegistrationDto(clientForRegistrationDto);
        this.accountManager.createClientAccount(account, client);
    }

    @Override
    public void createBusinessWorkerAccount(BusinessWorkerForRegistrationDto businessWorkerForRegistrationDto) throws BaseAppException {
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
    public AccountDetailsViewDto getAccountDetailsByLogin(String login) throws BaseAppException {
        return AccountMapper.toAccountDetailsViewDto(accountManager.getAccountByLogin(login));
    }

    @Override
    public List<AccountDtoForList> getAllAccounts() {
        return accountManager.getAllAccounts().stream().map(AccountMapper::toAccountListDto).collect(Collectors.toList());
    }

    @Override
    public void blockUser(@NotNull String login, @NotNull Long version) throws BaseAppException {
        Account account = this.accountManager.blockUser(login, version);
        Locale locale = new Locale(account.getLanguageType().getName().name());
        String body = i18n.getMessage(BLOCKED_ACCOUNT_BODY, locale);
        String subject = i18n.getMessage(BLOCKED_ACCOUNT_SUBJECT, locale);
        EmailService.sendEmailWithContent(account.getEmail().trim(), subject, body);
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
        Account account = this.accountManager.unblockUser(unblockedUserLogin, version);

        Locale locale = new Locale(account.getLanguageType().getName().name());
        String body = i18n.getMessage(UNBLOCKED_ACCOUNT_BODY, locale);
        String subject = i18n.getMessage(UNBLOCKED_ACCOUNT_SUBJECT, locale);
        EmailService.sendEmailWithContent(account.getEmail().trim(), subject, body);
    }


    @Override
    public void requestSomeonesPasswordReset(String login, String email) throws BaseAppException {
        this.accountManager.requestSomeonesPasswordReset(login, email);
    }

    @Override
    public void verifyAccount(AccountVerificationDto accountVerificationDto) throws BaseAppException {
        this.accountManager.verifyAccount(accountVerificationDto.getToken());
    }


    @Override
    public OtherClientChangeDataDto changeOtherClientData(OtherClientChangeDataDto otherClientChangeDataDto) throws OptimisticLockException, BaseAppException {
        Long version = getAccountByLogin(otherClientChangeDataDto.getLogin()).getVersion();

        if (!version.equals(otherClientChangeDataDto.getVersion())) {
            throw new OptimisticLockException(OPTIMISTIC_LOCK_EXCEPTION);
        }

        Account account = AccountMapper.extractAccountFromClientOtherChangeDataDto(otherClientChangeDataDto);
        String alterBy = AccountMapper.extractAlterByFromOtherClientDataChange(otherClientChangeDataDto);

        return AccountMapper.accountDtoForClientDataChange(accountManager.changeOtherClientData(account, alterBy));
    }

    @Override
    public OtherBusinessWorkerChangeDataDto changeOtherBusinessWorkerData(OtherBusinessWorkerChangeDataDto otherBusinessWorkerChangeDataDto) throws OptimisticLockException, BaseAppException {
        Long version = getAccountByLogin(otherBusinessWorkerChangeDataDto.getLogin()).getVersion();

        if (!version.equals(otherBusinessWorkerChangeDataDto.getVersion())) {
            throw new OptimisticLockException(OPTIMISTIC_LOCK_EXCEPTION);
        }

        Account account = AccountMapper.extractAccountFromOtherBusinessWorkerChangeDataDto(otherBusinessWorkerChangeDataDto);
        String alterBy = AccountMapper.extractAlterByFromOtherBusinessWorkerDataChange(otherBusinessWorkerChangeDataDto);
        return AccountMapper.accountDtoForBusinnesWorkerDataChange(accountManager.changeOtherBusinessWorkerData(account, alterBy));
    }

    @Override
    public AccountDto changeOtherAccountData(OtherAccountChangeDataDto otherAccountChangeDataDto) throws OptimisticLockException, BaseAppException {
        Long version = getAccountByLogin(otherAccountChangeDataDto.getLogin()).getVersion();

        if (!version.equals(otherAccountChangeDataDto.getVersion())) {
            throw new OptimisticLockException(OPTIMISTIC_LOCK_EXCEPTION);
        }

        Account account = AccountMapper.extractAccountFromOtherAccountChangeDataDto(otherAccountChangeDataDto);
        String alterBy = AccountMapper.extractAlterByFromAccount(otherAccountChangeDataDto);
        return AccountMapper.toAccountDto(accountManager.changeOtherAccountData(account, alterBy));
    }


    @Override
    public void changeEmail(AccountChangeEmailDto accountChangeEmailDto) throws BaseAppException, OptimisticLockException {
        Long version = getAccountByLogin(accountChangeEmailDto.getLogin()).getVersion();

        if (!version.equals(accountChangeEmailDto.getVersion())) {
            throw new OptimisticLockException(OPTIMISTIC_LOCK_EXCEPTION);
        }

        accountManager.changeEmail(accountChangeEmailDto.getLogin(), accountChangeEmailDto.getVersion(), accountChangeEmailDto.getNewEmail());
    }

    @Override
    public void changeClientData(ClientChangeDataDto clientChangeDataDto) throws OptimisticLockException, BaseAppException {
        Long version = getAccountByLogin(clientChangeDataDto.getLogin()).getVersion();

        if (!version.equals(clientChangeDataDto.getVersion())) {
            throw new OptimisticLockException(OPTIMISTIC_LOCK_EXCEPTION);
        }

        Account account = AccountMapper.extractAccountFromClientChangeDataDto(clientChangeDataDto);
        accountManager.changeClientData(account);
    }

    @Override
    public void changeBusinessWorkerData(BusinessWorkerChangeDataDto businessWorkerChangeDataDto) throws OptimisticLockException, BaseAppException {
        Long version = getAccountByLogin(businessWorkerChangeDataDto.getLogin()).getVersion();

        if (!version.equals(businessWorkerChangeDataDto.getVersion())) {
            throw new OptimisticLockException(OPTIMISTIC_LOCK_EXCEPTION);
        }

        Account account = AccountMapper.extractAccountFromBusinessWorkerChangeDataDto(businessWorkerChangeDataDto);
        accountManager.changeBusinessWorkerData(account);
    }

    @Override
    public void changeModeratorData(ModeratorChangeDataDto moderatorChangeDataDto) throws OptimisticLockException, BaseAppException {
        Long version = getAccountByLogin(moderatorChangeDataDto.getLogin()).getVersion();

        if (!version.equals(moderatorChangeDataDto.getVersion())) {
            throw new OptimisticLockException(OPTIMISTIC_LOCK_EXCEPTION);
        }

        Account account = AccountMapper.extractAccountFromModeratorChangeDataDto(moderatorChangeDataDto);
        accountManager.changeModeratorData(account);
    }

    @Override
    public void changeAdministratorData(AdministratorChangeDataDto administratorChangeDataDto) throws OptimisticLockException, BaseAppException {
        Long version = getAccountByLogin(administratorChangeDataDto.getLogin()).getVersion();

        if (!version.equals(administratorChangeDataDto.getVersion())) {
            throw new OptimisticLockException(OPTIMISTIC_LOCK_EXCEPTION);
        }

        Account account = AccountMapper.extractAccountFromAdministratorChangeDataDto(administratorChangeDataDto);
        accountManager.changeAdministratorData(account);
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

    @Override
    public String getCurrentUserLogin() {
        return securityContext.getUserPrincipal().getName();
    }
}
