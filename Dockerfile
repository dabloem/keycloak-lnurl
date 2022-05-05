FROM quay.io/keycloak/keycloak:18.0.0-legacy

COPY target/lnurl-auth-1.0.0-SNAPSHOT.jar /opt/jboss/keycloak/standalone/deployments/lnurl-auth.jar

ENV KEYCLOAK_USER=admin
ENV KEYCLOAK_PASSWORD=admin
ENV PROXY_ADDRESS_FORWARDING=true

#CMD ["-b", "0.0.0.0", "-Dkeycloak.profile=preview"]