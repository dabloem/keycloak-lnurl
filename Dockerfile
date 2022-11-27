FROM maven:3.8.5-jdk-11 as builder
WORKDIR /usr/src/lnurl-auth

COPY pom.xml .
COPY src/ ./src

RUN mvn clean install

FROM quay.io/keycloak/keycloak:16.1.1 as final
#WORKDIR /opt/jboss/keycloak
ARG LNURL_JAR=lnurl-auth.jar

ENV KEYCLOAK_USER=admin
ENV KEYCLOAK_PASSWORD=admin

COPY --from=builder /usr/src/lnurl-auth/target/${LNURL_JAR} /opt/jboss/keycloak/standalone/deployments/
#COPY ./keycloak/realm-export.json /opt/jboss/keycloak/standalone/deployments

# COPY ./update-startup.cli .

# RUN bin/jboss-cli.sh --command="module add --name=org.keycloak.providers.lnurl-auth --resources=target/${LNURL_JAR} --dependencies=org.keycloak.keycloak-core,org.keycloak.keycloak-services,org.keycloak.keycloak-model-jpa,org.keycloak.keycloak-server-spi,org.keycloak.keycloak-server-spi-private,javax.ws.rs.api,javax.persistence.api,org.hibernate,org.javassist,org.liquibase" \
#     && bin/jboss-cli.sh --file=./update-startup.cli \
#     && rm -rf /opt/jboss/keycloak/standalone/configuration/standalone_xml_history
