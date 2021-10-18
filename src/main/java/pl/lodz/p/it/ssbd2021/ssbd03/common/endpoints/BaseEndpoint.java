package pl.lodz.p.it.ssbd2021.ssbd03.common.endpoints;

import lombok.Getter;
import lombok.extern.java.Log;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.ejb.AfterBegin;
import javax.ejb.AfterCompletion;
import javax.ejb.EJBContext;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Log
@PermitAll
public abstract class BaseEndpoint implements TransactionalEndpoint {
    @Resource
    private EJBContext context;

    @Getter
    private String lastTransactionID;

    private LocalDateTime transactionStartTime;

    @Getter
    private boolean transactionRolledBack;

    @AfterBegin
    public void afterBegin() {
        lastTransactionID = UUID.randomUUID().toString();
        transactionStartTime = LocalDateTime.now();
        String loggedMessage = String.format("Transaction: %s " +
                        "\nstarted at endpoint %s, at time %s" +
                        "\nby user \"%s\"",
                lastTransactionID, this.getClass().getSimpleName(), transactionStartTime, getCurrentUserLogin());
        log.info(loggedMessage);
    }

    @AfterCompletion
    public void afterCompletion(boolean committed) {
        transactionRolledBack = !committed;

        String loggedMessage = String.format("Transaction: %s " +
                        "\nfinished at time %s" +
                        "\ntotal transaction time: %d µs" +
                        "\nTransaction result: %s",
                lastTransactionID, LocalDateTime.now(), ChronoUnit.MICROS.between(transactionStartTime, LocalDateTime.now()),
                committed ? "COMMIT" : "ROLLBACK");

        if (committed) {
            log.info(loggedMessage);
        } else {
            log.warning(loggedMessage);
        }
    }

    private String getCurrentUserLogin() {
        return context.getCallerPrincipal().getName();
    }
}