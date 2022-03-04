package org.noderunners.authentication.lnurl;


import org.keycloak.authentication.RequiredActionContext;
import org.keycloak.authentication.RequiredActionProvider;
import org.keycloak.utils.StringUtil;

import javax.ws.rs.core.Response;
import java.util.UUID;

public class UserNameRequiredAction implements RequiredActionProvider {

    @Override
    public void evaluateTriggers(RequiredActionContext context) {

    }

    @Override
    public void requiredActionChallenge(RequiredActionContext requiredActionContext) {
        if (requiredActionContext
                .getUser()
                .getUsername().length() < 10) {
            requiredActionContext.success();
        } else {
            Response form = requiredActionContext
                    .form().createForm("updateUsername.ftl");
            requiredActionContext.challenge(form);
        }
    }

    @Override
    public void processAction(RequiredActionContext context) {
        System.out.println("helllo rom proces action");

        context.getUser().setUsername(UUID.randomUUID().toString());
        context.success();
    }

    @Override
    public void close() {

    }
}
