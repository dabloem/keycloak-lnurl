package org.noderunners.authentication.lnurl;


import org.keycloak.authentication.InitiatedActionSupport;
import org.keycloak.authentication.RequiredActionContext;
import org.keycloak.authentication.RequiredActionProvider;
import org.keycloak.models.Constants;
import org.keycloak.models.UserModel;
import org.keycloak.models.UserProvider;
import org.keycloak.models.utils.FormMessage;
import org.keycloak.services.validation.Validation;
import org.keycloak.userprofile.*;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.List;

public class UserNameRequiredAction implements RequiredActionProvider {

    @Override
    public InitiatedActionSupport initiatedActionSupport() {
        return InitiatedActionSupport.SUPPORTED;
    }

    @Override
    public void evaluateTriggers(RequiredActionContext context) {
        System.out.println("Evaluating....");
    }

    @Override
    public void requiredActionChallenge(RequiredActionContext requiredActionContext) {
        System.out.println(requiredActionContext.getUser().getUsername());
        System.out.println(requiredActionContext.getAuthenticationSession().getAuthenticatedUser().getUsername());

        if (requiredActionContext.getAuthenticationSession().getClientNote(Constants.KC_ACTION) != null) {
            Response form = requiredActionContext
                    .form()
                    .setAttribute("username", requiredActionContext.getAuthenticationSession().getAuthenticatedUser().getUsername())
                    .createForm("updateUsername.ftl");
            requiredActionContext.challenge(form);
            return;
        }

        if (requiredActionContext.getUser().getUsername().length() != 36) {
            requiredActionContext.success();
        } else {
            Response form = requiredActionContext
                    .form()
                    .setAttribute("username", requiredActionContext.getUser().getUsername()) //.getAuthenticationSession().getAuthenticatedUser().getUsername())
                    .createForm("updateUsername.ftl");
            requiredActionContext.challenge(form);
        }
    }

    @Override
    public void processAction(RequiredActionContext context) {
//        String username = context.getHttpRequest().getDecodedFormParameters().getFirst("username");
        MultivaluedMap<String, String> formData = context.getHttpRequest().getDecodedFormParameters();

        UserModel user = context.getUser();
//        UserProvider userProvider = context.getSession().getProvider(UserProvider.class);
        UserProfileProvider provider = context.getSession().getProvider(UserProfileProvider.class);
        UserProfile profile = provider.create(UserProfileContext.UPDATE_PROFILE, formData, user);

        try {
            profile.update(false); //, new EventAuditingAttributeChangeListener(profile, event));
            context.success();
        } catch (ValidationException var9) {
            List<FormMessage> errors = Validation.getFormErrorsFromValidation(var9.getErrors());
            context.challenge(
                    context.form().createForm("updateUsername.ftl")
            );
        }

//        try {
//            context.getUser().setUsername(username);
//            context.getUser().setLastName(username);
//            context.getSession().users().
//        } catch (Exception ex) {
//            context.form().createForm("updateUsername.ftl");
//        }
//        context.success();
    }

    @Override
    public void close() { }

    @Override
    public int getMaxAuthAge() {
        return 600;
    }
}
