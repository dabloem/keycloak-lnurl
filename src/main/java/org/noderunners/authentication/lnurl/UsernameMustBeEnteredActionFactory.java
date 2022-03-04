package org.noderunners.authentication.lnurl;

import org.keycloak.Config;
import org.keycloak.authentication.RequiredActionFactory;
import org.keycloak.authentication.RequiredActionProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

public class UsernameMustBeEnteredActionFactory implements RequiredActionFactory {

    private static final UserNameRequiredAction SINGLETON = new UserNameRequiredAction();

    @Override
    public String getDisplayText() {
        return "User must have a username";
    }

    @Override
    public RequiredActionProvider create(KeycloakSession keycloakSession) {
        return SINGLETON;
    }

    @Override
    public void init(Config.Scope scope) {

    }

    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {

    }

    @Override
    public void close() {

    }

    @Override
    public String getId() {
        return "UsernameAction";
    }
}
