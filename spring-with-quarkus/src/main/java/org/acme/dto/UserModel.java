package org.acme.dto;

public interface UserModel {

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







}
