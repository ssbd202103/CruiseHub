FROM jboss/wildfly:24.0.0.Final
ADD target/ssbd03.war /opt/jboss/wildfly/standalone/deployments/
RUN /bin/bash -c '/opt/jboss/wildfly/bin/standalone.sh -b 0.0.0.0 &'
CMD /opt/jboss/wildfly/bin/standalone.sh -b 0.0.0.0