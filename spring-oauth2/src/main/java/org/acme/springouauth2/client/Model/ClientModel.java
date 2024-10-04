package org.acme.springouauth2.client.Model;


import org.springframework.lang.Nullable;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.util.SpringAuthorizationServerVersion;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.Set;


public class ClientModel implements Serializable {

    private static final long serialVersionUID = SpringAuthorizationServerVersion.SERIAL_VERSION_UID;

    private String id;

    private String clientId;

    private Instant clientIdIssuedAt;

    private String clientSecret;

    private Instant clientSecretExpiresAt;

    private String clientName;

    private String  clientAuthenticationMethods;

    private String authorizationGrantTypes;

    private String  redirectUris;

    private  String  postLogoutRedirectUris;

    private String  scopes;

    private String clientSettings;

    private String tokenSettings;

    public ClientModel() {
    }

    /**
     * Returns the identifier for the registration.
     *
     * @return the identifier for the registration
     */
    public String getId() {
        return this.id;
    }

    /**
     * Returns the client identifier.
     *
     * @return the client identifier
     */
    public String getClientId() {
        return this.clientId;
    }

    /**
     * Returns the time at which the client identifier was issued.
     *
     * @return the time at which the client identifier was issued
     */
    @Nullable
    public Instant getClientIdIssuedAt() {
        return this.clientIdIssuedAt;
    }

    /**
     * Returns the client secret or {@code null} if not available.
     *
     * @return the client secret or {@code null} if not available
     */
    @Nullable
    public String getClientSecret() {
        return this.clientSecret;
    }

    /**
     * Returns the time at which the client secret expires or {@code null} if it does not
     * expire.
     *
     * @return the time at which the client secret expires or {@code null} if it does not
     * expire
     */
    @Nullable
    public Instant getClientSecretExpiresAt() {
        return this.clientSecretExpiresAt;
    }

    /**
     * Returns the client name.
     *
     * @return the client name
     */
    public String getClientName() {
        return this.clientName;
    }

    /**
     * Returns the {@link ClientAuthenticationMethod authentication method(s)} that the
     * client may use.
     *
     * @return the {@code Set} of {@link ClientAuthenticationMethod authentication
     * method(s)}
     */
    public String getClientAuthenticationMethods() {
        return this.clientAuthenticationMethods;
    }

    /**
     * Returns the {@link AuthorizationGrantType authorization grant type(s)} that the
     * client may use.
     *
     * @return the {@code Set} of {@link AuthorizationGrantType authorization grant
     * type(s)}
     */
    public String getAuthorizationGrantTypes() {
        return this.authorizationGrantTypes;
    }

    /**
     * Returns the redirect URI(s) that the client may use in redirect-based flows.
     *
     * @return the {@code Set} of redirect URI(s)
     */
    public String getRedirectUris() {
        return this.redirectUris;
    }

    /**
     * Returns the post logout redirect URI(s) that the client may use for logout. The
     * {@code post_logout_redirect_uri} parameter is used by the client when requesting
     * that the End-User's User Agent be redirected to after a logout has been performed.
     *
     * @return the {@code Set} of post logout redirect URI(s)
     * @since 1.1
     */
    public String getPostLogoutRedirectUris() {
        return this.postLogoutRedirectUris;
    }

    /**
     * Returns the scope(s) that the client may use.
     *
     * @return the {@code Set} of scope(s)
     */
    public String getScopes() {
        return this.scopes;
    }

    /**
     * Returns the {@link ClientSettings client configuration settings}.
     *
     * @return the {@link ClientSettings}
     */
    public String getClientSettings() {
        return this.clientSettings;
    }

    /**
     * Returns the {@link TokenSettings token configuration settings}.
     *
     * @return the {@link TokenSettings}
     */
    public String getTokenSettings() {
        return this.tokenSettings;
    }



    @Override
    public int hashCode() {
        return Objects.hash( this.id , this.clientId , this.clientIdIssuedAt , this.clientSecret ,
                             this.clientSecretExpiresAt , this.clientName , this.clientAuthenticationMethods ,
                             this.authorizationGrantTypes , this.redirectUris , this.postLogoutRedirectUris ,
                             this.scopes ,
                             this.clientSettings , this.tokenSettings );
    }

    @Override
    public String toString() {
        return "RegisteredClient {" + "id='" + this.id + '\'' + ", clientId='" + this.clientId + '\'' + ", clientName='"
                + this.clientName + '\'' + ", clientAuthenticationMethods=" + this.clientAuthenticationMethods
                + ", authorizationGrantTypes=" + this.authorizationGrantTypes + ", redirectUris=" + this.redirectUris
                + ", postLogoutRedirectUris=" + this.postLogoutRedirectUris + ", scopes=" + this.scopes
                + ", clientSettings=" + this.clientSettings + ", tokenSettings=" + this.tokenSettings + '}';
    }


    public void setId( String id ) {
        this.id = id;
    }

    public void setClientId( String clientId ) {
        this.clientId = clientId;
    }

    public void setClientIdIssuedAt( Instant clientIdIssuedAt ) {
        this.clientIdIssuedAt = clientIdIssuedAt;
    }

    public void setClientSecret( String clientSecret ) {
        this.clientSecret = clientSecret;
    }

    public void setClientSecretExpiresAt( Instant clientSecretExpiresAt ) {
        this.clientSecretExpiresAt = clientSecretExpiresAt;
    }

    public void setClientName( String clientName ) {
        this.clientName = clientName;
    }

    public void setClientAuthenticationMethods( String clientAuthenticationMethods ) {
        this.clientAuthenticationMethods = clientAuthenticationMethods;
    }

    public void setAuthorizationGrantTypes( String authorizationGrantTypes ) {
        this.authorizationGrantTypes = authorizationGrantTypes;
    }

    public void setRedirectUris( String redirectUris ) {
        this.redirectUris = redirectUris;
    }

    public void setPostLogoutRedirectUris( String postLogoutRedirectUris ) {
        this.postLogoutRedirectUris = postLogoutRedirectUris;
    }

    public void setScopes( String scopes ) {
        this.scopes = scopes;
    }

    public void setClientSettings( String clientSettings ) {
        this.clientSettings = clientSettings;
    }

    public void setTokenSettings( String tokenSettings ) {
        this.tokenSettings = tokenSettings;
    }
}
