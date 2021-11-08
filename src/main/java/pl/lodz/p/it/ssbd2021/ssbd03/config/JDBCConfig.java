package pl.lodz.p.it.ssbd2021.ssbd03.config;

import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@DataSourceDefinition(
        name = "java:app/jdbc/ssbd03adminDS",
        className = "com.mysql.cj.jdbc.MysqlDataSource",
//        user = "ssbd03admin",
//        password = "adminpasswd",
//        serverName = "localhost",
//        portNumber = 3306,
        url="jdbc:mysql://ssbd03_mysql_db:3306/ssbd03?autoReconnect=true&useSSL=false&user=ssbd03admin&password=adminpasswd&allowPublicKeyRetrieval=true"
)

@DataSourceDefinition(
        name = "java:app/jdbc/ssbd03glassfishDS",
        className = "com.mysql.cj.jdbc.MysqlDataSource",
//        user = "ssbd03glassfish",
//        password = "glassfishpasswd",
//        serverName = "localhost",
//        portNumber = 3306,
//        databaseName = "ssbd03",
        url="jdbc:mysql://ssbd03_mysql_db:3306/ssbd03?autoReconnect=true&useSSL=false&user=ssbd03glassfish&password=glassfishpasswd&allowPublicKeyRetrieval=true"
)

@DataSourceDefinition(
        name = "java:app/jdbc/ssbd03mokDS",
        className = "com.mysql.cj.jdbc.MysqlDataSource",
//        user = "ssbd03mok",
//        password = "mokpasswd",
//        serverName = "localhost",
//        portNumber = 3306,
//        databaseName = "ssbd03",
        url="jdbc:mysql://ssbd03_mysql_db:3306/ssbd03?autoReconnect=true&useSSL=false&user=ssbd03mok&password=mokpasswd&allowPublicKeyRetrieval=true"
)

@DataSourceDefinition(
        name = "java:app/jdbc/ssbd03mowDS",
        className = "com.mysql.cj.jdbc.MysqlDataSource",
//        user = "ssbd03mow",
//        password = "mowpasswd",
//        serverName = "localhost",
//        portNumber = 3306,
//        databaseName = "ssbd03",
        url="jdbc:mysql://ssbd03_mysql_db:3306/ssbd03?autoReconnect=true&useSSL=false&user=ssbd03mow&password=mowpasswd&allowPublicKeyRetrieval=true"

)
@Stateless
public class JDBCConfig {
    @PersistenceContext(unitName = "ssbd03adminPU")
    private EntityManager em;
}
