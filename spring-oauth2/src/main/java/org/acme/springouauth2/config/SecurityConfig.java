package org.acme.springouauth2.config;

import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.InMemoryOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.token.*;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import static org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer.authorizationServer;
import java.util.UUID;

@Configuration
public class SecurityConfig {

  @Bean
  public AuthorizationServerSettings authorizationServerSettings() {

    return AuthorizationServerSettings.builder().build();
  }

  // @formatter:off
    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {




        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);


        http.getConfigurer( OAuth2AuthorizationServerConfigurer.class)
            .oidc(Customizer.withDefaults());

        http.exceptionHandling((e) -> e.authenticationEntryPoint(
                new LoginUrlAuthenticationEntryPoint("/login") ) );

        return http.build();
  }








    @Bean
    RegisteredClientRepository registeredClientRepository() {
        RegisteredClient messagingClient = RegisteredClient.withId( UUID.randomUUID()
                                                                        .toString() )
                                                           .clientId( "messaging-client" )
                                                           .clientSecret( "{noop}secret" )
                                                           .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC )
                                                           .authorizationGrantType( new AuthorizationGrantType(
                                                                   "urn:ietf:params:oauth:grant-type:custom_code" ) )
                                                           .scope( "message.read" )
                                                           .scope( "message.write" )
                                                           .build();


        RegisteredClient registeredClient = RegisteredClient
                .withId(UUID.randomUUID().toString())
                .clientId("client")
                .clientSecret( "{noop}secret" )

                .clientSettings(ClientSettings.builder()
                                              .requireProofKey(false)
                                              .build())
                .build();



        return new InMemoryRegisteredClientRepository( registeredClient);
    }
    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
            throws Exception {

        http.formLogin(Customizer.withDefaults());

        http.authorizeHttpRequests( c -> c.anyRequest().authenticated()
        );

        return http.build();
    }

    @Bean
    OAuth2AuthorizationService authorizationService() {
        return new InMemoryOAuth2AuthorizationService();
    }

    @Bean
    OAuth2TokenGenerator< ? > tokenGenerator( JWKSource< SecurityContext > jwkSource ) {
        JwtGenerator jwtGenerator = new JwtGenerator( new NimbusJwtEncoder( jwkSource ) );
        OAuth2AccessTokenGenerator accessTokenGenerator = new OAuth2AccessTokenGenerator();
        OAuth2RefreshTokenGenerator refreshTokenGenerator = new OAuth2RefreshTokenGenerator();
        return new DelegatingOAuth2TokenGenerator( jwtGenerator , accessTokenGenerator , refreshTokenGenerator );
    }

}