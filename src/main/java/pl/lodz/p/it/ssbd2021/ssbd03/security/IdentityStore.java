package pl.lodz.p.it.ssbd2021.ssbd03.security;

import javax.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;


/**
 * Definiuje repozytorium tożsamości używane do logowania poprzez Soteria API
 */
@DatabaseIdentityStoreDefinition(
    dataSourceLookup = "java:app/jdbc/ssbd03glassfishDS",
    callerQuery = "SELECT DISTINCT passw FROM accounts WHERE login = ?",
    groupsQuery = "SELECT login FROM accounts WHERE login = ?",
    hashAlgorithm = SHA256Hash.class
)
public class IdentityStore {
}

//public class OurIdentityStore implements IdentityStore, IdentityStoreHandler {
//
//    @PersistenceContext(unitName = "ssbd03mokPU")
//    private EntityManager em;
//
//    @Override
//    public CredentialValidationResult validate(Credential credential) {
//        UsernamePasswordCredential upc = (UsernamePasswordCredential) credential;
//        TypedQuery<Account> tq = em.createNamedQuery("Account.findByLogin", Account.class);
//        tq.setParameter("login", upc.getCaller());
//        Account account = tq.getSingleResult();
//        SHA256Hash hash = new SHA256Hash();
//
//        if (account.getPasswordHash().equals(hash.generate(upc.getPassword().getValue()))) {
//            return new CredentialValidationResult(upc.getCaller());
//        }
//        return CredentialValidationResult.INVALID_RESULT;
//    }
//
//    @Override
//    public Set<String> getCallerGroups(CredentialValidationResult validationResult) {
//        return IdentityStore.super.getCallerGroups(validationResult);
//    }
//
//    @Override
//    public int priority() {
//        return IdentityStore.super.priority();
//    }
//
//    @Override
//    public Set<ValidationType> validationTypes() {
//        return IdentityStore.super.validationTypes();
//    }
//}