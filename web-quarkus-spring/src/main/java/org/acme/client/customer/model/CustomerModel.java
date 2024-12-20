package org.acme.client.customer.model;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public interface CustomerModel {


    String CUSTOMER_NAME = "customername";
    String FIRST_NAME = "firstName";
    String LAST_NAME = "lastName";
    String EMAIL = "email";
    String EMAIL_VERIFIED = "emailVerified";
    String LOCALE = "locale";
    String ENABLED = "enabled";


    Comparator<CustomerModel> COMPARE_BY_USERNAME = Comparator.comparing(CustomerModel::getCustomerName, String.CASE_INSENSITIVE_ORDER);
    String getId();

    // No default method here to allow Abstract subclasses where the username is provided in a different manner
    String getCustomerName();

    /**
     * Sets username for this customer.
     *
     * No default method here to allow Abstract subclasses where the username is provided in a different manner
     *
     * @param username username string
     */
    void setCustomerName(String name);

    /**
     * Get timestamp of customer creation. May be null for old users created before this feature introduction.
     */
    Long getCreatedTimestamp();

    void setCreatedTimestamp(Long timestamp);

    boolean isEnabled();

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

    default void addRequiredAction(RequiredAction action) {
        if (action == null) return;
        String actionName = action.name();
        addRequiredAction(actionName);
    }

    default void removeRequiredAction(RequiredAction action) {
        if (action == null) return;
        String actionName = action.name();
        removeRequiredAction(actionName);
    }

    String getFirstName();

    void setFirstName(String firstName);

    String getLastName();

    void setLastName(String lastName);

    String getEmail();

    /**
     * Sets email for this customer.
     *
     * @param email the email
     */
    void setEmail(String email);

    boolean isEmailVerified();

    void setEmailVerified(boolean verified);


    enum RequiredAction {
        VERIFY_EMAIL,
        UPDATE_PROFILE,
        CONFIGURE_TOTP,
        CONFIGURE_RECOVERY_AUTHN_CODES,
        UPDATE_PASSWORD,
        TERMS_AND_CONDITIONS,
        VERIFY_PROFILE,
        UPDATE_EMAIL
    }
}
