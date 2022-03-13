package org.noderunners.authentication.lnurl.action;

import org.jboss.logging.Logger;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.authenticators.browser.AbstractUsernameFormAuthenticator;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.UserProvider;
import org.keycloak.models.utils.FormMessage;
import org.keycloak.theme.beans.MessagesPerFieldBean;
import org.keycloak.utils.StringUtil;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;
import java.util.Objects;
import java.util.UUID;

public class LNNodeAuthenticator extends AbstractUsernameFormAuthenticator implements Authenticator {

    private static final Logger logger = Logger.getLogger(LNNodeAuthenticator.class);

    @Override
    public void action(AuthenticationFlowContext context) {
        String signature = context.getHttpRequest().getDecodedFormParameters().getFirst("signature");
        String pubkey = context.getHttpRequest().getDecodedFormParameters().getFirst("node");
        String msg = context.getAuthenticationSession().getAuthNote("message");

        if (StringUtil.isBlank(signature)) {
            context.form().addError(new FormMessage("signature", "Signature cannot be empty"));
        }
        if (StringUtil.isBlank(pubkey)) {
            context.form().addError(new FormMessage("node", "Node id cannot be empty"));
        }

        boolean verification = NodeVerifierAction.verify(msg, signature, pubkey);

        if (!verification) {
            context.form().setError("Verification failed.");
            authenticate(context);
        } else {
            logger.infof("Node verification success for %s", pubkey);
            UserProvider userProvider = context.getSession().getProvider(UserProvider.class);
            UserModel user = userProvider.searchForUserByUserAttributeStream(context.getRealm(), "node", pubkey)
                    .findFirst()
                    .orElse(null);
            if (user == null) {
                context.form().setError("Unknown node-id.");
                authenticate(context);
                context.failure(AuthenticationFlowError.INVALID_CREDENTIALS); //, context.form().createForm("login-lnurl-auth.ftl"));
            } else {
                context.getAuthenticationSession().setAuthenticatedUser(user);
                context.setUser(user);
                context.success();
            }
        }

    }

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        String msg = Objects.requireNonNullElse(
                context.getAuthenticationSession().getAuthNote("message"), UUID.randomUUID().toString());
        context.getAuthenticationSession().setAuthNote("message", msg);

        Response response = context.form()
                .setAttribute("messagesPerField", new MessagesPerFieldBean())
                .setAttribute("login", new LoginBean(msg))
                .createForm("login-lightning-node-id.ftl");
        context.challenge(response);
    }

    @Override
    public boolean requiresUser() {
        return false;
    }

    @Override
    public boolean configuredFor(KeycloakSession session, RealmModel realm, UserModel user) {
        return true;
    }

    @Override
    public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {

    }
}
