package org.acme.client.customer.model;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.acme.core.model.Model;

public interface UserModel extends Model {


    String USERNAME = "username";
    String FIRST_NAME = "firstName";
    String LAST_NAME = "lastName";
    String EMAIL = "email";
    String EMAIL_VERIFIED = "emailVerified";
    String LOCALE = "locale";
    String ENABLED = "enabled";
    String DISABLED_REASON = "disabledReason";


    String getId();


    String getUsername();


    void setUsername(String username);

    void setEmail(String email);

    Long getCreatedTimestamp();

    void setCreatedTimestamp(Long timestamp);

    Boolean isEnabled();

    void setEnabled(boolean enabled);


    void setSingleAttribute(String name, String value);

    void setAttribute(String name, List<String> values);

    void removeAttribute(String name);

    String getFirstAttribute(String name);


    Stream<String> getAttributeStream(final String name);

    Map<String, List<String>> getAttributes();


    Stream<String> getRequiredActionsStream();

    void addRequiredAction(String action);

    void removeRequiredAction(String action);


}
