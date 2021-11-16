FROM jboss/wildfly:24.0.0.Final

USER root

COPY wait-for-it.sh /usr/wait-for-it.sh
RUN chmod +x /usr/wait-for-it.sh

RUN /bin/bash -c /usr/wait-for-it.sh ssbd03_mysql_db:3306 -- /opt/jboss/wildfly/bin/standalone.sh -b 0.0.0.0 &