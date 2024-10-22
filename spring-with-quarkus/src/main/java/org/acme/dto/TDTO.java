package org.acme.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TDTO {


    @JsonProperty("id")
    private String id;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("LAST_NAME")
    private String lastName;

    @JsonProperty("ADDRESS")
    private String address;


    public TDTO() {
        this(null, null, null, null);
    }


    // Default constructor for Jackson deserialization


    public TDTO(String id, String firstName, String lastName, String address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;

    }

public void setFirstName(String firstName) {
        this.firstName = firstName;
}
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }


    public String getId() {
        return id;
    }

    public static String USERNAME = "username";
    public static String FIRST_NAME = "firstName";
    public static String LAST_NAME = "lastName";
    public static String EMAIL = "email";
    public static String EMAIL_VERIFIED = "emailVerified";
    public static String LOCALE = "locale";
    public static String ENABLED = "enabled";
    public static String IDP_ALIAS = "keycloak.session.realm.users.query.idp_alias";
    public static String IDP_USER_ID = "keycloak.session.realm.users.query.idp_user_id";
    public static String INCLUDE_SERVICE_ACCOUNT = "keycloak.session.realm.users.query.include_service_account";
    public static String GROUPS = "keycloak.session.realm.users.query.groups";
    public static String SEARCH = "keycloak.session.realm.users.query.search";
    public static String EXACT = "keycloak.session.realm.users.query.exact";
    public static String DISABLED_REASON = "disabledReason";

    public static enum RequiredAction {
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
