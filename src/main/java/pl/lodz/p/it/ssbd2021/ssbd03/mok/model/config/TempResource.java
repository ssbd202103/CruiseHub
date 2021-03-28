package pl.lodz.p.it.ssbd2021.ssbd03.mok.model.config;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.model.AccessLevel;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.model.Account;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.model.LanguageType;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.model.accesslevels.Administrator;

import javax.ejb.EJB;
import javax.print.attribute.standard.Media;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Path(value = "/temp")
public class TempResource {

    @EJB
    private TempEm tempEm;

    @GET
    public Response get() {

        Account account = new Account();
        account.setFirstName("Artur");
        account.setSecondName("Radiuk");
        account.setLogin("aradiuk");
        account.setEmail("aradiuk@gmail.com");
        account.setPassword("123456");
        account.setConfirmed(true);
        account.setActive(true);
        account.setLastIncorrectAuthenticationDateTime(LocalDateTime.now());
        account.setLastCorrectAuthenticationDateTime(LocalDateTime.now());
        account.setLastIncorrectAuthenticationLogicalAddress("1234");
        account.setLastCorrectAuthenticationLogicalAddress("1234");
        account.setLanguageType(LanguageType.PL);

        List<AccessLevel> accessLevels = new ArrayList<>();
        AccessLevel administrator = new Administrator();
        accessLevels.add(administrator);

        account.setAccessLevels(accessLevels);

        tempEm.create(administrator);
        tempEm.create(account);


        return Response.ok().entity("Hello world").build();
    }

}
