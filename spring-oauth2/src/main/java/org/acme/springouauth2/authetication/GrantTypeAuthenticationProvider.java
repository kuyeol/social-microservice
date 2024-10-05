package org.acme.springouauth2.authetication;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.context.AuthorizationServerContextHolder;
import org.springframework.security.oauth2.server.authorization.token.DefaultOAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;


//todo: return new OAuth2AccessTokenAuthenticationToken(registeredClient, clientPrincipal, accessToken);
//	}
//	@Override
//	public boolean supports(Class<?> authentication) {
//		return CustomCodeGrantAuthenticationToken.class.isAssignableFrom(authentication);
//	}


public class GrantTypeAuthenticationProvider  implements AuthenticationProvider {

  private final OAuth2AuthorizationService authorizationService;
  private final OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    GrantTypeAuthenticationToken customCodeGrantAuthentication =
        (GrantTypeAuthenticationToken) authentication;

    // Ensure the client is authenticated
    OAuth2ClientAuthenticationToken clientPrincipal =
        getAuthenticatedClientElseThrowInvalidClient(customCodeGrantAuthentication);
    RegisteredClient registeredClient = clientPrincipal.getRegisteredClient();

    // Ensure the client is configured to use this authorization grant type
    if (!registeredClient.getAuthorizationGrantTypes().contains(customCodeGrantAuthentication.getGrantType())) {
      throw new OAuth2AuthenticationException(OAuth2ErrorCodes.UNAUTHORIZED_CLIENT);
    }

    //TODO: Validate the code parameter

    // Generate the access token
    OAuth2TokenContext tokenContext = DefaultOAuth2TokenContext.builder()
        .registeredClient(registeredClient)
        .principal(clientPrincipal)
        .authorizationServerContext(AuthorizationServerContextHolder.getContext())
        .tokenType(OAuth2TokenType.ACCESS_TOKEN)
        .authorizationGrantType(customCodeGrantAuthentication.getGrantType())
        .authorizationGrant(customCodeGrantAuthentication)
        .build();

    OAuth2Token generatedAccessToken = this.tokenGenerator.generate(tokenContext);
    if (generatedAccessToken == null) {
      OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR,
          "The token generator failed to generate the access token.", null);
      throw new OAuth2AuthenticationException(error);
    }
    OAuth2AccessToken accessToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER,
        generatedAccessToken.getTokenValue(), generatedAccessToken.getIssuedAt(),
        generatedAccessToken.getExpiresAt(), null);

    // Initialize the OAuth2Authorization
    OAuth2Authorization.Builder authorizationBuilder = OAuth2Authorization.withRegisteredClient(registeredClient)
        .principalName(clientPrincipal.getName())
        .authorizationGrantType(customCodeGrantAuthentication.getGrantType());
    if (generatedAccessToken instanceof ClaimAccessor) {
      authorizationBuilder.token(accessToken, (metadata) ->
          metadata.put(
              OAuth2Authorization.Token.CLAIMS_METADATA_NAME,
              ((ClaimAccessor) generatedAccessToken).getClaims())
      );
    } else {
      authorizationBuilder.accessToken(accessToken);
    }
    OAuth2Authorization authorization = authorizationBuilder.build();

    // Save the OAuth2Authorization
    this.authorizationService.save(authorization);

    return new OAuth2AccessTokenAuthenticationToken(registeredClient, clientPrincipal, accessToken);
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return GrantTypeAuthenticationToken.class.isAssignableFrom(authentication);;
  }
}
