package org.noderunners.authentication.lnurl.action;

import fr.acinq.secp256k1.Secp256k1;
import org.apache.commons.codec.binary.Hex;
import org.jboss.logging.Logger;
import org.keycloak.Config;
import org.keycloak.authentication.InitiatedActionSupport;
import org.keycloak.authentication.RequiredActionContext;
import org.keycloak.authentication.RequiredActionFactory;
import org.keycloak.authentication.RequiredActionProvider;
import org.keycloak.authentication.requiredactions.util.UpdateProfileContext;
import org.keycloak.authentication.requiredactions.util.UserUpdateProfileContext;
import org.keycloak.forms.login.LoginFormsPages;
import org.keycloak.forms.login.freemarker.Templates;
import org.keycloak.forms.login.freemarker.model.ProfileBean;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.models.utils.FormMessage;
import org.keycloak.theme.beans.MessagesPerFieldBean;
import org.keycloak.utils.StringUtil;
import org.lightningj.util.ZBase32;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.UUID;

public class NodeVerifierAction implements RequiredActionProvider, RequiredActionFactory {

    private static final Logger logger = Logger.getLogger(NodeVerifierAction.class);

    @Override
    public InitiatedActionSupport initiatedActionSupport() {
        return InitiatedActionSupport.SUPPORTED;
    }

    @Override
    public String getDisplayText() {
        return "Node Verification";
    }

    @Override
    public void evaluateTriggers(RequiredActionContext context) {
    }

    @Override
    public void requiredActionChallenge(RequiredActionContext context) {
        UpdateProfileContext userCtx = new UserUpdateProfileContext(context.getRealm(), context.getUser());
        MultivaluedHashMap<String, String> formData = new MultivaluedHashMap<>();
        String msg = UUID.randomUUID().toString();
        formData.add("message", msg);
        context.getAuthenticationSession().setAuthNote("message", msg);

        Response response = context.form()
                .setAttribute("messagesPerField", new MessagesPerFieldBean())
                .setAttribute("user", new ProfileBean(userCtx, formData))
                .setAttribute("profile", new NodeVerifierBean(context.getUser(), formData, context.getSession()))
                .createForm("verify-lightning-node-id.ftl");
        context.challenge(response);
    }

    @Override
    public void processAction(RequiredActionContext context) {
        String signature = context.getHttpRequest().getDecodedFormParameters().getFirst("signature");
        String pubkey = context.getHttpRequest().getDecodedFormParameters().getFirst("node");
        String msg = context.getAuthenticationSession().getAuthNote("message");

        if (StringUtil.isBlank(signature)) {
            context.form().addError(new FormMessage("signature", "Signature cannot be empty"));
        }
        if (StringUtil.isBlank(pubkey)) {
            context.form().addError(new FormMessage("node", "Node id cannot be empty"));
        }

        boolean verification = verify(msg, signature, pubkey);

        if (!verification) {
            context.form().setError("Verification failed.");
            requiredActionChallenge(context);
        } else {
            context.getUser().setSingleAttribute("node", pubkey);
            context.success();
            logger.infof("Node verification success for %s", pubkey);
        }
    }

    @Override
    public RequiredActionProvider create(KeycloakSession keycloakSession) {
        return this;
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
        return "LightningNodeVerification";
    }

    static Secp256k1 secp256k1 = Secp256k1.get();

    static String PREFIX = "Lightning Signed Message:";

    public static boolean verify(String message, String signature, String pubkey) {
        logger.debugf("starting verification for: msg = %s, sig = %s, key = %s", message, signature, pubkey);
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest((PREFIX + message).getBytes(StandardCharsets.UTF_8));
            hash = md.digest(hash); //double SHA-256 ( Hex: 954d5a49fd70d9b8bcdb35d252267829957f7ef7fa6c74f88419bdc5e82209f4 )

            byte[] sig = ZBase32.decode(signature);
            //remove the first byte (rec_id), result is byte[64],
            //otherwise 'secp256k1_ecdsa_signature_parse_der failed' exception, which tells me converting to DER succeeds in verify()
            sig = Arrays.copyOfRange(sig, 1, sig.length);

            byte[] key = Hex.decodeHex(pubkey.toCharArray()); //node-id

            //public abstract fun verify(signature: kotlin.ByteArray, message: kotlin.ByteArray, pubkey: kotlin.ByteArray): kotlin.Boolean
            return secp256k1.verify(sig, hash, key);
        } catch (Exception ex) {
            logger.debugf(ex, "Exception occured for %s", pubkey);
            return false;
        }
    }
}
