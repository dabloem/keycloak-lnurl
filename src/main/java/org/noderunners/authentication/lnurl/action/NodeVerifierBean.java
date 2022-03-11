package org.noderunners.authentication.lnurl.action;

import org.keycloak.forms.login.freemarker.model.AbstractUserProfileBean;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.UserModel;
import org.keycloak.userprofile.*;

import javax.ws.rs.core.MultivaluedMap;
import java.util.*;
import java.util.stream.Stream;

public class NodeVerifierBean extends AbstractUserProfileBean {

    UserModel user;

    public NodeVerifierBean(UserModel user, MultivaluedMap<String, String> formData, KeycloakSession session) {
        super(formData);
        this.user = user;
        init(session, false);
    }

    @Override
    protected UserProfile createUserProfile(UserProfileProvider provider) {
        return provider.create(UserProfileContext.UPDATE_PROFILE, user);
    }

    @Override
    protected Stream<String> getAttributeDefaultValues(String name){
        return user.getAttributeStream(name);
    }

    @Override
    public String getContext() {
        return "LNNODE_VERIFICATION";
    }

    @Override
    public List<Attribute> getAttributes() {
        UserProfileMetadata meta = new UserProfileMetadata(UserProfileContext.UPDATE_PROFILE);
        Attribute node = new Attribute(meta.addAttribute("node", 1, AttributeMetadata.ALWAYS_TRUE, AttributeMetadata.ALWAYS_TRUE)) {
            @Override
            public String getDisplayName() {
                return "Node";
            }

            @Override
            public boolean isReadOnly() {
                return false;
            }

            @Override
            public boolean isRequired() {
                return true;
            }
        };

        Attribute message = new Attribute(meta.addAttribute("message", 1, AttributeMetadata.ALWAYS_TRUE, AttributeMetadata.ALWAYS_TRUE)) {
            @Override
            public String getDisplayName() {
                return "Message to sign";
            }

            @Override
            public String getValue() {
                return formData.getFirst("message");
            }
        };

        Attribute signature = new Attribute(meta.addAttribute("signature", 1, AttributeMetadata.ALWAYS_TRUE, AttributeMetadata.ALWAYS_TRUE)) {
            @Override
            public String getDisplayName() {
                return "Signature";
            }

            @Override
            public boolean isReadOnly() {
                return false;
            }

            @Override
            public boolean isRequired() {
                return true;
            }
        };

        return Arrays.asList(message, node, signature);
    }

}
