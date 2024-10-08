package org.acme.springouauth2.authetication;

import java.util.Map;

import org.springframework.lang.Nullable;
import org.springframework.security.core.Transient;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

@Transient
public class CustomCodeGrantAuthenticationToken extends OAuth2ClientAuthenticationToken {


    /**
     * Constructs an {@code OAuth2ClientAuthenticationToken} using the provided
     * parameters.
     *
     * @param clientId                   the client identifier
     * @param clientAuthenticationMethod the authentication method used by the client
     *                                   <p>
     *                                   public static final ClientAuthenticationMethod CLIENT_SECRET_BASIC =
     *                                   new ClientAuthenticationMethod("client_secret_basic");
     *                                   <p>
     *                                   public static final ClientAuthenticationMethod CLIENT_SECRET_POST =
     *                                   new ClientAuthenticationMethod("client_secret_post");
     *                                   <p>
     *                                   public static final ClientAuthenticationMethod CLIENT_SECRET_JWT =
     *                                   new ClientAuthenticationMethod("client_secret_jwt");
     *                                   <p>
     *                                   public static final ClientAuthenticationMethod PRIVATE_KEY_JWT =
     *                                   new ClientAuthenticationMethod("private_key_jwt");
     *                                   <p>
     *                                   public static final ClientAuthenticationMethod NONE =
     *                                   new ClientAuthenticationMethod("none");
     *                                   <p>
     *                                   public static final ClientAuthenticationMethod TLS_CLIENT_AUTH =
     *                                   new ClientAuthenticationMethod("tls_client_auth");
     *                                   <p>
     *                                   public static final ClientAuthenticationMethod SELF_SIGNED_TLS_CLIENT_AUTH =
     *                                   new ClientAuthenticationMethod("self_signed_tls_client_auth");
     * @param credentials                the client credentials
     * @param additionalParameters       the additional parameters
     */
    public CustomCodeGrantAuthenticationToken( String clientId ,
                                               ClientAuthenticationMethod clientAuthenticationMethod ,
                                               @Nullable    Object credentials ,
                                               @Nullable   Map< String, Object > additionalParameters ) {
        super( clientId , clientAuthenticationMethod , credentials , additionalParameters );
    }

    /**
     * Constructs an {@code OAuth2ClientAuthenticationToken} using the provided
     * parameters.
     *
     * @param registeredClient           the authenticated registered client
     * @param clientAuthenticationMethod the authentication method used by the client
     * @param credentials                the client credentials
     */
    public CustomCodeGrantAuthenticationToken( RegisteredClient registeredClient ,
                                               ClientAuthenticationMethod clientAuthenticationMethod ,
                                               @Nullable Object credentials ) {
        super( registeredClient , clientAuthenticationMethod , credentials );
    }
}
