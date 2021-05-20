package pl.lodz.p.it.ssbd2021.ssbd03.config;

import java.sql.Connection;
import javax.annotation.sql.DataSourceDefinition;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@DataSourceDefinition( // Ta pula połączeń jest na potrzeby tworzenia struktur przy wdrażaniu aplikacji
        name = "java:app/jdbc/ssbd03adminDS",
        className = "org.postgresql.ds.PGSimpleDataSource",
        user = "ssbd03admin",
        password = "ssbd03admin_pass",
        serverName = "localhost",
        portNumber = 5432,
        databaseName = "ssbd03",
        initialPoolSize = 1,
        minPoolSize = 0,
        maxPoolSize = 1,
        maxIdleTime = 10, // Nie potrzebujemy przetrzymywać połączeń tej puli
        isolationLevel = Connection.TRANSACTION_READ_COMMITTED
)

@DataSourceDefinition( // Ta pula połączeń jest na potrzeby implementacji uwierzytelnienia w aplikacji
        name = "java:app/jdbc/ssbd03glassfishDS",
        className = "org.postgresql.ds.PGSimpleDataSource",
        user = "ssbd03glassfish",
        password = "glassfishpasswd",
        serverName = "localhost",
        portNumber = 5432,
        databaseName = "ssbd03",
        isolationLevel = Connection.TRANSACTION_READ_COMMITTED,
        minPoolSize = 0,
        maxPoolSize = 1,
        maxIdleTime = 10
)

@DataSourceDefinition( // Ta pula połączeń jest na potrzeby operacji realizowanych przez moduł aplikacji
        name = "java:app/jdbc/ssbd03mokDS",
        className = "org.postgresql.ds.PGSimpleDataSource",
        user = "ssbd03mok",
        password = "ssbd03mok_pass",
        serverName = "localhost",
        portNumber = 5432,
        databaseName = "ssbd03",
        isolationLevel = Connection.TRANSACTION_READ_COMMITTED,
        minPoolSize = 0,
        maxPoolSize = 1,
        maxIdleTime = 10
)

@DataSourceDefinition( // Ta pula połączeń jest na potrzeby operacji realizowanych przez moduł aplikacji
        name = "java:app/jdbc/ssbd03mowDS",
        className = "org.postgresql.ds.PGSimpleDataSource",
        user = "ssbd03mow",
        password = "ssbd03mow_pass",
        serverName = "localhost",
        portNumber = 5432,
        databaseName = "ssbd03",
        isolationLevel = Connection.TRANSACTION_READ_COMMITTED,
        minPoolSize = 0,
        maxPoolSize = 1,
        maxIdleTime = 10
)

public class JDBCConfig {
    @PersistenceContext(unitName = "ssbd03adminPU")
    private EntityManager em;
}
