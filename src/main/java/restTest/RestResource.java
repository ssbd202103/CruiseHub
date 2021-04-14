package restTest;

import pl.lodz.p.it.ssbd2021.ssbd03.mok.model.Account;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.model.Address;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.model.AlterTypeWrapper;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.model.LanguageType;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.model.accesslevels.Administrator;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.model.accesslevels.Client;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/")
public class RestResource {

    public EntityManager getEntityManager(){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ssbd03adminPU");
        return entityManagerFactory.createEntityManager();
    }
//    private final AccountFacade accountFacade = new AccountFacade();
//    private final AddressFacade addressFacade = new AddressFacade();
//    private final ClientFacade clientFacade = new ClientFacade();
//    private final AdministratorFacade administratorFacade = new AdministratorFacade();
//
//    @PersistenceContext(unitName = "ssbd03adminPU")
//    private EntityManager entityManager;

    @GET
    @Produces("text/plain")
    public String hello() {
        return "Hello, stupid World!";
    }

    @Path("/create-account")
    @GET
    @Produces("text/plain")
    public String getMe() {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        Administrator admin = new Administrator(true);
        Account adminUser = new Account("admin", "adminowski", "admin1", "admin@admin.com", "super_passwd", true, true, LanguageType.PL);
        adminUser.setAccessLevel(admin);
        admin.setCreatedBy(adminUser);
        admin.setAlteredBy(adminUser);
        adminUser.setCreatedBy(adminUser);
        adminUser.setAlteredBy(adminUser);

//        entityManager.persist(admin);
        entityManager.persist(adminUser);

        Address address = new Address(123L, "Małostkowa", "174-184", "Łódź", "Polska");

        Client client = new Client(address, "1749-1849-183", true);
//        Client client2 = new Client(address, "18491747-12", true);

        Account account = new Account("Michał", "Milik", "michu123_12", "michu@gmail.com", "oK7asda2f", true, true, LanguageType.PL);
        account.setAccessLevel(client);
//        account.setAccessLevel(client2);
        account.setCreatedBy(account);
        account.setAlteredBy(account);

        address.setCreatedBy(adminUser);
        address.setAlteredBy(adminUser);
        client.setCreatedBy(account);
        client.setAlteredBy(account);
        entityManager.persist(account);

        entityManager.flush();
        entityManager.getTransaction().commit();

        account.setFirstName("Mareczek");
//        account.setAlterType(AlterTypeWrapper.UPDATE);
        entityManager.getTransaction().begin();
//        entityManager.merge(account);
//        entityManager.flush();
        entityManager.getTransaction().commit();

        return account.toString();
    }
}
