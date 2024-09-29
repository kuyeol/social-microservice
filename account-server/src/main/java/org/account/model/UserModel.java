
package org.account.model;



import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.acme.account.represetion.identitymanagement.ProviderEvent;


import static org.acme.account.util.StringUtil.isNotBlank;


/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public interface UserModel extends RoleMapperModel {

    String USERNAME = "username";
    String FIRST_NAME = "firstName";
    String LAST_NAME = "lastName";
    String EMAIL = "email";
    String EMAIL_VERIFIED = "emailVerified";
    String LOCALE = "locale";
    String ENABLED = "enabled";
    String IDP_ALIAS = "keycloak.session.realm.users.query.idp_alias";
    String IDP_USER_ID = "keycloak.session.realm.users.query.idp_user_id";
    String INCLUDE_SERVICE_ACCOUNT = "keycloak.session.realm.users.query.include_service_account";
    String GROUPS = "keycloak.session.realm.users.query.groups";
    String SEARCH = "keycloak.session.realm.users.query.search";
    String EXACT = "keycloak.session.realm.users.query.exact";
    String DISABLED_REASON = "disabledReason";

    Comparator<UserModel> COMPARE_BY_USERNAME = Comparator.comparing(UserModel::getUsername, String.CASE_INSENSITIVE_ORDER);

    interface UserRemovedEvent extends ProviderEvent {

        UserModel getUser();
        KeycloakSession getKeycloakSession();
    }

    interface UserPreRemovedEvent extends ProviderEvent
    {

        UserModel getUser();
        KeycloakSession getKeycloakSession();
    }

    String getId();

    // No default method here to allow Abstract subclasses where the username is provided in a different manner
    String getUsername();

    /**
     * Sets username for this user.
     *
     * No default method here to allow Abstract subclasses where the username is provided in a different manner
     *
     * @param username username string
     */
    void setUsername(String username);

    /**
     * Get timestamp of user creation. May be null for old users created before this feature introduction.
     */
    Long getCreatedTimestamp();

    void setCreatedTimestamp(Long timestamp);

    boolean isEnabled();

    void setEnabled(boolean enabled);

    /**
     * Set single value of specified attribute. Remove all other existing values of this attribute
     *
     * @param name
     * @param value
     */
    void setSingleAttribute(String name, String value);

    void setAttribute(String name, List<String> values);

    void removeAttribute(String name);

    /**
     * @param name
     * @return null if there is not any value of specified attribute or first value otherwise. Don't throw exception if there are more values of the attribute
     */
    String getFirstAttribute(String name);

    /**
     * Obtains all values associated with the specified attribute name.
     *
     * @param name the name of the attribute.
     * @return a non-null {@link Stream} of attribute values.
     */
    Stream<String> getAttributeStream(final String name);

    Map<String, List<String>> getAttributes();

    /**
     * Obtains the aliases of required actions associated with the user.
     *
     * @return a non-null {@link Stream} of required action aliases.
     */
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
     * Sets email for this user.
     *
     * @param email the email
     */
    void setEmail(String email);

    boolean isEmailVerified();

    void setEmailVerified(boolean verified);



    String getFederationLink();
    void setFederationLink(String link);

    String getServiceAccountClientLink();
    void setServiceAccountClientLink(String clientInternalId);

    /**
     * Indicates if this {@link UserModel} maps to a local account or an account
     * federated from an external user storage.
     *
     * @return {@code true} if a federated account. Otherwise, {@code false}.
     */
    default boolean isFederated() {
        return isNotBlank(getFederationLink());
    }


    SubjectCredentialManager credentialManager();

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
