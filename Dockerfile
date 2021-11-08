FROM jboss/wildfly:24.0.0.Final
ADD target/ssbd03.war /opt/jboss/wildfly/standalone/deployments/
RUN /opt/jboss/wildfly/bin/add-user.sh admin Admin#70365 --silent
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]