FROM jboss/wildfly:24.0.0.Final

USER root

COPY wait-for-it.sh /usr/wait-for-it.sh
RUN chmod +x /usr/wait-for-it.sh

USER jboss
ADD target/ssbd03.war /opt/jboss/wildfly/standalone/deployments/

RUN /bin/bash -c /usr/wait-for-it.sh ssbd03_mysql_db:3306 -- /opt/jboss/wildfly/bin/standalone.sh -b 0.0.0.0 &
#RUN /bin/bash -c '/opt/jboss/wildfly/bin/standalone.sh -b 0.0.0.0 &'
#CMD /opt/jboss/wildfly/bin/standalone.sh -b 0.0.0.0