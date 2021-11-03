FROM jboss/wildfly
ADD ./target/ssbd03.war /opt/jboss/wildfly/standalone/deployments/