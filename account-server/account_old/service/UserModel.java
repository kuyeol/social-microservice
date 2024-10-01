
package org.account.service;



import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;






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


    String getId();

    // No default method here to allow Abstract subclasses where the username is provided in a different manner
    String getUsername();


    void setUsername(String username);


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

    void setEmail(String email);

    boolean isEmailVerified();

    void setEmailVerified(boolean verified);



    String getFederationLink();
    void setFederationLink(String link);

    String getServiceAccountClientLink();
    void setServiceAccountClientLink(String clientInternalId);



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
