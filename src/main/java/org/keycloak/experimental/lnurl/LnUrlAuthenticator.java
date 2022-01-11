package org.keycloak.experimental.lnurl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import fr.acinq.bitcoin.Bech32;
import fr.acinq.secp256k1.Secp256k1;
import org.apache.commons.codec.binary.Hex;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.FlowStatus;
import org.keycloak.authentication.authenticators.browser.AbstractUsernameFormAuthenticator;
import org.keycloak.common.util.KeycloakUriBuilder;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class LnUrlAuthenticator extends AbstractUsernameFormAuthenticator implements Authenticator {

    @Override
    public void action(AuthenticationFlowContext context) {
        //response from login form
        System.out.println("Form submission");

        context.success();
        super.action(context);
    }

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        //response from wallet
        if (context.getHttpRequest().getUri().getQueryParameters().getFirst("k1") != null) {
            System.out.println("Response from LN Wallet!");

            String k1 = context.getHttpRequest().getUri().getQueryParameters().getFirst("k1");
            String key = context.getHttpRequest().getUri().getQueryParameters().getFirst("key");
            String sig = context.getHttpRequest().getUri().getQueryParameters().getFirst("sig");

            if (!verify(k1, key, sig)) {
                context.failure(AuthenticationFlowError.INVALID_CREDENTIALS, Response.status(400).entity("{\"status\": \"ERROR\", \"reason\": \"signature validation failed\"}").build());
                return;
            }

            UserModel user = context.getSession().users().searchForUserByUserAttribute("bsn", key, context.getRealm())
                    .stream().findFirst().orElseGet(() -> {
                        UserModel u = context.getSession().users()
                                .addUser(context.getRealm(), UUID.randomUUID().toString());
                        u.setEnabled(true);
                        u.setAttribute("bsn", Arrays.asList(key));
                        return u;
                    });

            context.getAuthenticationSession().setAuthenticatedUser(user);
            context.setUser(user);
            context.challenge(Response.ok("{ \"status\" : \"ok\" }").build());
            return;
        }

        //polling
        if (context.getAuthenticationSession().getAuthNote("lnurl") != null) {
            System.out.println("polling");
            if (context.getUser() != null) { //succesfull authentication
                System.out.println("success");
                context.challenge(Response.ok("{}").type(MediaType.APPLICATION_JSON_TYPE).build());
            } else {
                System.out.println("not yet");
                context.challenge(Response.status(Response.Status.UNAUTHORIZED).build());
            }
            return;
        }


        //Initial login
        byte[] random = new byte[32];
        new Random().nextBytes(random);
        String hex = Hex.encodeHexString(random);

        String link = KeycloakUriBuilder.fromUri(context.getRefreshUrl(true))
                .queryParam("tag", "login")
                .queryParam("action", "login")
                .queryParam("k1", hex)
                .build()
                .toString();

        String lnurl = toBech32(URI.create(link));
//                "&tag=login&k1=" + hex + "&action=login"));
//        System.out.println("bech32:" + s);

        String image = "";
        try {
            int imageSize = 200;
            BitMatrix matrix = new MultiFormatWriter().encode(lnurl, BarcodeFormat.QR_CODE, imageSize, imageSize);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(matrix, "png", bos);
            image = Base64.getEncoder().encodeToString(bos.toByteArray()); // base64 encode
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
        context.form().setAttribute("lnurlimg", "data:image/png;base64," + image);

        String kc = KeycloakUriBuilder.fromUri(context.getRefreshExecutionUrl()).build().toString();
        context.form().setAttribute("kcurl", kc);
        Response response = context.form().createForm("login-lnurl-auth.ftl");
        response.getHeaders().add("Access-Control-Allow-Origin", "*");
        context.challenge(response);

        context.getAuthenticationSession().setAuthNote("lnurl", lnurl);
    }

    private boolean verify(String msg, String key, String sig) {
        try {
            byte[] k1 = Hex.decodeHex(msg.toCharArray());
            byte[] pubkey = Hex.decodeHex(key.toCharArray());
            byte[] signature = Hex.decodeHex(sig.toCharArray());

            Secp256k1 secpk256k1 = Secp256k1.get();
            return secpk256k1.verify(signature, k1,pubkey);
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public boolean requiresUser() {
        return false;
    }

    @Override
    public boolean configuredFor(KeycloakSession keycloakSession, RealmModel realmModel, UserModel userModel) {
        return true;
    }

    @Override
    public void setRequiredActions(KeycloakSession keycloakSession, RealmModel realmModel, UserModel userModel) {

    }

    private static String toBech32(URI uri) {
        byte[] data = uri.toString().getBytes(StandardCharsets.UTF_8);
        return Bech32.encode("lnurl", toPrimitives(Bech32.eight2five(data)), Bech32.Encoding.Bech32);
    }

    private static byte[] toPrimitives(Byte[] oBytes) {
        byte[] bytes = new byte[oBytes.length];
        for (int i = 0; i < oBytes.length; i++) {
            bytes[i] = oBytes[i];
        }
        return bytes;
    }
}
