package org.noderunners.authentication.lnurl;

import org.keycloak.Config;
import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticatorFactory;
import org.keycloak.models.AuthenticationExecutionModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LnUrlAuthAutenticatorFactory implements AuthenticatorFactory {

    public static final String ID = "lnurl-form";

    private static final AuthenticationExecutionModel.Requirement[] REQUIREMENT_CHOICES = {
            AuthenticationExecutionModel.Requirement.REQUIRED,
            AuthenticationExecutionModel.Requirement.ALTERNATIVE,
            AuthenticationExecutionModel.Requirement.CONDITIONAL,
            AuthenticationExecutionModel.Requirement.DISABLED
    };

    @Override
    public String getDisplayType() {
        return "LNURL";
    }

    @Override
    public String getReferenceCategory() {
        return "lnurl-auth";
    }

    @Override
    public boolean isConfigurable() {
        return true;
    }

    @Override
    public AuthenticationExecutionModel.Requirement[] getRequirementChoices() {
        return REQUIREMENT_CHOICES;
    }

    @Override
    public boolean isUserSetupAllowed() {
        return true;
    }

    @Override
    public String getHelpText() {
        return "LNURL authentication";
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        ProviderConfigProperty onColor = new ProviderConfigProperty("onColor", "RGB on-color", "Format: 255,109,1", "String", "0,0,0");
        ProviderConfigProperty offColor = new ProviderConfigProperty("offColor", "RGB off-color", "Format: 255,109,1", "String", "255,255,255");
        ProviderConfigProperty imageSize = new ProviderConfigProperty("size", "Image size", "Size in pixels", "String", "200");

        return Arrays.asList(onColor, offColor, imageSize);
    }

    @Override
    public Authenticator create(KeycloakSession keycloakSession) {
        return new LnUrlAuthenticator();
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
        return ID;
    }
}
