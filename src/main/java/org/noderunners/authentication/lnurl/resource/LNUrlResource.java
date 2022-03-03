package org.noderunners.authentication.lnurl.resource;

import org.keycloak.models.KeycloakSession;
import org.noderunners.authentication.lnurl.LnUrlAuthenticator;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

public class LNUrlResource {

    private static Map<String, String[]> verifiedList = new HashMap<>();

    private KeycloakSession ks;

    public LNUrlResource(KeycloakSession keycloakSession) {
        this.ks = keycloakSession;
    }

    public static String[] getCredentials(String k1) {
        return verifiedList.remove(k1);
    }

    @GET
    public Response verify(@QueryParam("k1") String k1, @QueryParam("key") String key, @QueryParam("sig") String sig) {
        if (LnUrlAuthenticator.verify(k1, key, sig)) {
            verifiedList.put(k1, new String[]{key, sig});
            return Response.ok("{ \"status\" : \"ok\" }").build();
        }

        return Response.status(400)
                .entity("{\"status\": \"ERROR\", \"reason\": \"signature validation failed\"}")
                .build();
    }

    @GET
    @Path("{k1}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response poll(@PathParam("k1") String k1) {
        if (verifiedList.containsKey(k1)) {
            return Response.ok("{\"result\":\"ok\"}").build();
        }

        return Response.status(404).entity("{\"result\":\"not found\"}").build();
    }
}
