package org.noderunners.authentication.lnurl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import fr.acinq.bitcoin.Bech32;
import fr.acinq.secp256k1.Secp256k1;
import org.apache.commons.codec.binary.Hex;
import org.jboss.logging.Logger;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.authenticators.browser.AbstractUsernameFormAuthenticator;
import org.keycloak.common.util.KeycloakUriBuilder;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.noderunners.authentication.lnurl.resource.LNUrlResource;

import javax.ws.rs.core.Response;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Stream;

public class LnUrlAuthenticator extends AbstractUsernameFormAuthenticator implements Authenticator {

    @Override
    public void action(AuthenticationFlowContext context) {
        String k1 = context.getAuthenticationSession().getAuthNote("k1");
        String[] credentials = LNUrlResource.getCredentials(k1);

        if (credentials != null && verify(k1, credentials[0], credentials[1])) {
            UserModel user = getOrCreateUser(context, credentials[0]);
            context.getAuthenticationSession().setAuthenticatedUser(user);
            context.setUser(user);
            context.success();
        } else {
            context.failure(AuthenticationFlowError.INVALID_CREDENTIALS, context.form().createForm("login-lnurl-auth.ftl"));
        }
    }

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        //Initial login
        byte[] random = new byte[32];
        new Random().nextBytes(random);
        String hex = Hex.encodeHexString(random);

        URI uri = KeycloakUriBuilder.fromUri(context.getUriInfo().getBaseUri())
                .path("realms")
                .path(context.getRealm().getName())
                .path("lnurl")
                .build();

        try {
            String link = KeycloakUriBuilder.fromUri(uri)
                .queryParam("tag", "login")
                .queryParam("action", "login")
                .queryParam("k1", hex)
                .build()
                .toString();

            String lnurlAuth = toBech32(URI.create(link));
//            Logger.getLogger(this.getClass().getName()).info("ln-url");
//            Logger.getLogger(this.getClass().getName()).info("  link:" + link);
            Logger.getLogger(this.getClass().getName()).info("  bech32:" + lnurlAuth);

            int imageSize = Integer.valueOf(context.getAuthenticatorConfig().getConfig().get("size"));
            Color colorOn = convertRGB(context.getAuthenticatorConfig().getConfig().get("onColor"));
            Color colorOff = convertRGB(context.getAuthenticatorConfig().getConfig().get("offColor"));

            BitMatrix matrix = new MultiFormatWriter().encode(lnurlAuth, BarcodeFormat.QR_CODE, imageSize, imageSize);
            MatrixToImageConfig conf = new MatrixToImageConfig(colorOn.getRGB(), colorOff.getRGB());
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(matrix, "png", bos, conf);
            String image = Base64.getEncoder().encodeToString(bos.toByteArray()); // base64 encode
            context.form().setAttribute("qr", "data:image/png;base64," + image);
            context.form().setAttribute("lnurlAuth", lnurlAuth);
        } catch (WriterException | IOException e) {
            throw new RuntimeException(e);
        }

        String pollingUrl = KeycloakUriBuilder.fromUri(uri).path(hex).build().toString();
        context.form().setAttribute("pollingUrl", pollingUrl);
        context.form().setAttribute("k1", hex);
        Response response = context.form().createForm("login-lnurl-auth.ftl");
//        response.getHeaders().add("Access-Control-Allow-Origin", "*");
        context.challenge(response);

        context.getAuthenticationSession().setAuthNote("k1", hex);
    }

    private Color convertRGB(String colorString) {
        Integer[] colors = Stream.of(colorString.split(","))
                .map(s -> Integer.valueOf(s))
                .toArray(Integer[]::new);
        return new Color(colors[0], colors[1], colors[2]);
    }

    public static boolean verify(String msg, String key, String sig) {
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

    private UserModel getOrCreateUser(AuthenticationFlowContext context, String key) {
        return context.getSession().users()
                .searchForUserByUserAttributeStream(context.getRealm(), "lnkey", key)
                .findFirst()
                .orElseGet(() -> {
                    UserModel u = context.getSession().users()
                            .addUser(context.getRealm(), UUID.randomUUID().toString());
                    u.setEnabled(true);
                    u.setAttribute("lnkey", Arrays.asList(key));
                    return u;
                });
    }
}
