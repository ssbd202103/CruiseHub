/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2021.ssbd03.mok.model.config;

import java.sql.Connection;
import javax.annotation.sql.DataSourceDefinition;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

//@DataSourceDefinition ( // Ta pula połączeń jest na potrzeby tworzenia struktur przy wdrażaniu aplikacji
//        name = "java:app/jdbc/ssbd03admin",
//        className = "org.postgresql.ds.PGSimpleDataSource",
//        user = "ssbd03admin",
//        password = "adminpasswd",
//        serverName = "studdev.it.p.lodz.pl",
//        portNumber = 5432,
//        databaseName = "ssbd03",
//        initialPoolSize = 1,
//        minPoolSize = 0,
//        maxPoolSize = 1,
//        maxIdleTime = 10 // Nie potrzebujemy przetrzymywać połączeń tej puli
//)
//
//@DataSourceDefinition( // Ta pula połączeń jest na potrzeby implementacji uwierzytelnienia w aplikacji
//        name = "java:app/jdbc/ssbd03glassfish",
//        className = "org.postgresql.ds.PGSimpleDataSource",
//        user = "ssbd03glassfish",
//        password = "glassfishpasswd",
//        serverName = "studdev.it.p.lodz.pl",
//        portNumber = 5432,
//        databaseName = "ssbd03"
//)
//
//@DataSourceDefinition ( // Ta pula połączeń jest na potrzeby operacji realizowanych przez moduł aplikacji
//        name = "java:app/jdbc/ssbd03mok",
//        className = "org.postgresql.ds.PGSimpleDataSource",
//        user = "ssbd03mok",
//        password = "mokpasswd",
//        serverName = "studdev.it.p.lodz.pl",
//        portNumber = 5432,
//        databaseName = "ssbd03",
//        transactional = true,
//        isolationLevel = Connection.TRANSACTION_READ_COMMITTED
//)

@DataSourceDefinition(
        name = "java:app/jdbc/temp",
        className = "org.postgresql.ds.PGSimpleDataSource",
        user = "postgres",
        password = "mysecretpassword",
        serverName = "localhost",
        portNumber = 5432,
        databaseName = "postgres",
        transactional = true,
        isolationLevel = Connection.TRANSACTION_READ_COMMITTED)

public class JDBCConfig {
    @PersistenceContext(unitName = "temp")
    private EntityManager em;
}
