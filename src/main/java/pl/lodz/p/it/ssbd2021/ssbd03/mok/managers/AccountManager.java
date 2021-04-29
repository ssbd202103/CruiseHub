package pl.lodz.p.it.ssbd2021.ssbd03.mok.managers;

import org.apache.commons.codec.digest.DigestUtils;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.AlterType;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.wrappers.AlterTypeWrapper;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Account;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Address;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.Administrator;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.BusinessWorker;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.Client;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.accesslevels.Moderator;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.facades.AccountFacade;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.mappers.AccountMapper;
import pl.lodz.p.it.ssbd2021.ssbd03.security.JWTHandler;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * Klasa która zarządza logiką biznesową kont
 */
@Stateful
public class AccountManager implements AccountManagerLocal {

    @EJB
    private AccountFacade accountFacade;

    @Override
    public void createClientAccount(Account account, Client client, Address address) {
        AlterTypeWrapper insertAlterType = accountFacade.getAlterTypeWrapperByAlterType(AlterType.INSERT);

        account.setPasswordHash(DigestUtils.sha256Hex(account.getPasswordHash()));
        account.setLanguageType(accountFacade.getLanguageTypeWrapperByLanguageType(account.getLanguageType().getName()));
        account.setAlterType(insertAlterType);
        account.setAlteredBy(account);
        account.setCreatedBy(account);

        client.setAlteredBy(account);
        client.setCreatedBy(account);
        client.setAlterType(insertAlterType);
        client.setAccount(account);

        address.setCreatedBy(account);
        address.setAlteredBy(account);
        address.setAlterType(insertAlterType);

        client.setHomeAddress(address);

        account.addAccessLevel(client);

        accountFacade.create(account);
    }

    @Override
    public void createBusinessWorker(Account account, BusinessWorker businessWorker) {
        AlterTypeWrapper insertAlterType = accountFacade.getAlterTypeWrapperByAlterType(AlterType.INSERT);

        account.setPasswordHash(DigestUtils.sha256Hex(account.getPasswordHash()));
        account.setLanguageType(accountFacade.getLanguageTypeWrapperByLanguageType(account.getLanguageType().getName()));
        account.setAlterType(insertAlterType);
        account.setAlteredBy(account);
        account.setCreatedBy(account);

        businessWorker.setAlteredBy(account);
        businessWorker.setCreatedBy(account);
        businessWorker.setAlterType(insertAlterType);
        businessWorker.setAccount(account);

        account.addAccessLevel(businessWorker);

        this.accountFacade.create(account);
    }

    @Override
    public void createAdministrator(Account account, Administrator administrator) {
        AlterTypeWrapper insertAlterType = accountFacade.getAlterTypeWrapperByAlterType(AlterType.INSERT);

        account.setPasswordHash(DigestUtils.sha256Hex(account.getPasswordHash()));
        account.setLanguageType(accountFacade.getLanguageTypeWrapperByLanguageType(account.getLanguageType().getName()));
        account.setAlterType(insertAlterType);
        account.setAlteredBy(account);
        account.setCreatedBy(account);

        administrator.setAlteredBy(account);
        administrator.setCreatedBy(account);
        administrator.setAlterType(insertAlterType);
        administrator.setAccount(account);

        account.addAccessLevel(administrator);

        this.accountFacade.create(account);
    }

    @Override
    public void createModerator(Account account, Moderator moderator) {
        AlterTypeWrapper insertAlterType = accountFacade.getAlterTypeWrapperByAlterType(AlterType.INSERT);

        account.setPasswordHash(DigestUtils.sha256Hex(account.getPasswordHash()));
        account.setLanguageType(accountFacade.getLanguageTypeWrapperByLanguageType(account.getLanguageType().getName()));
        account.setAlterType(insertAlterType);
        account.setAlteredBy(account);
        account.setCreatedBy(account);

        moderator.setAlteredBy(account);
        moderator.setCreatedBy(account);
        moderator.setAlterType(insertAlterType);
        moderator.setAccount(account);

        account.addAccessLevel(moderator);

        this.accountFacade.create(account);
    }

    @Override
    public void updateIncorrectAuthenticateInfo(String login, String IpAddr, LocalDateTime time) {
        this.accountFacade.updateAuthenticateInfo(login, IpAddr, time, false);
    }

    @Override
    public String updateCorrectAuthenticateInfo(String login, String IpAddr, LocalDateTime time) {
        Account account = this.accountFacade.updateAuthenticateInfo(login, IpAddr, time, true);

        Map<String, Object> map = Map.of("login", login, "accessLevels", AccountMapper.toAccountDto(account).getAccessLevels().toString());
        return JWTHandler.createToken(map, account.getId().toString());
    }
}
