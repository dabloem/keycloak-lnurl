package org.noderunners.authentication.lnurl.resource;

import org.keycloak.models.KeycloakSession;
import org.keycloak.services.resource.RealmResourceProvider;

public class LNUrlResourceProvider implements RealmResourceProvider {

    final KeycloakSession keycloakSession;

    public LNUrlResourceProvider(KeycloakSession keycloakSession) {
        this.keycloakSession = keycloakSession;
    }

    @Override
    public Object getResource() {
        return new LNUrlResource(keycloakSession);
    }

    @Override
    public void close() {

    }
}
