package org.acme.springouauth2.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;


@Configuration
@EnableWebSecurity
public class OAuth2SecurityConfig {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authServerFilterChain( HttpSecurity http ) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity( http );

        http.getConfigurer( OAuth2AuthorizationServerConfigurer.class )
            .oidc( Customizer.withDefaults() );

        http.exceptionHandling( ( exception ) -> {
                exception.authenticationEntryPoint( new LoginUrlAuthenticationEntryPoint( "/login" ) );
            } )
            .oauth2ResourceServer( ( resourceServer ) -> {
                resourceServer.jwt( Customizer.withDefaults() );
            } );

        return http.build();
    }

    @Bean
    @Order(2) //INVOKE RRIORTY
    public SecurityFilterChain standardFilterChain( HttpSecurity http ) throws Exception {

        http.authorizeHttpRequests( ( authorizeRequests ) -> {
                authorizeRequests.anyRequest()
                                 .authenticated();
            } )
            .formLogin( Customizer.withDefaults() );

        return http.build();
    }

    @Bean
    public RegisteredClientRepository registeredClientInvoke() {

        RegisteredClient loginClient = RegisteredClient.withId( UUID.randomUUID()
                                                                    .toString() )
                                                       .clientId( "client" )
                                                       .clientSecret( "secret" )
                                                       .clientAuthenticationMethod(
                                                               ClientAuthenticationMethod.CLIENT_SECRET_BASIC )
                                                       .authorizationGrantType(
                                                               AuthorizationGrantType.AUTHORIZATION_CODE )
                                                       .authorizationGrantType( AuthorizationGrantType.REFRESH_TOKEN )
                                                       //.authorizationGrantType(AuthorizationGrantType.JWT_BEARER)
                                                       .redirectUri(
                                                               "http://127.0.0.1.com/login/ouath/code/login-client" )
                                                     //  .redirectUri( "http://localhost:8080/authorized" )
                                                       .scope( OidcScopes.OPENID )
                                                       //.scope( OidcScopes.PROFILE )
                                                       .clientSettings( ClientSettings.builder()
                                                                                      .requireAuthorizationConsent(
                                                                                              true )
                                                                                      .build() )
                                                       .build();


        RegisteredClient registeredClient = RegisteredClient.withId( UUID.randomUUID()
                                                                         .toString() )
                                                            .clientId( "messaging-client" )
                                                            .clientSecret( "{noop}secret" )
                                                            .clientAuthenticationMethod(
                                                                    ClientAuthenticationMethod.CLIENT_SECRET_BASIC )
                                                            .authorizationGrantType(
                                                                    AuthorizationGrantType.CLIENT_CREDENTIALS )
                                                            .scope( "message:read" )
                                                            .scope( "message:write" )
                                                            .build();

        return new InMemoryRegisteredClientRepository( loginClient , registeredClient );
    }


    @Bean
    public JWKSource< SecurityContext > jwkSource( KeyPair keyPair ) {

        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

        RSAPrivateCrtKey privateCrtKey = (RSAPrivateCrtKey) keyPair.getPrivate();

        RSAKey rsaKey = new RSAKey.Builder( publicKey ).privateKey( privateCrtKey )
                                                       .keyID( UUID.randomUUID()
                                                                   .toString() )
                                                       .build();

        JWKSet jwkSet = new JWKSet( rsaKey );

        return new ImmutableJWKSet<>( jwkSet );
    }


    @Bean
    public JwtDecoder jwtDecoder( KeyPair keyPair ) {

        return NimbusJwtDecoder.withPublicKey( (RSAPublicKey) keyPair.getPublic() )
                               .build();
    }


    @Bean
    public AuthorizationServerSettings providerSettings() {
        return AuthorizationServerSettings.builder()
                                          .issuer( "http://localhost:9000" )
                                          .build();
    }

    @Bean
    public UserDetailsService inMemoryUserDetailsService() {
        UserDetails userDetails = User.withDefaultPasswordEncoder()
                                      .username( "user" )
                                      .password( "password" )
                                      .roles( "USER" )
                                      .build();

        return new InMemoryUserDetailsManager( userDetails );
    }


    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    KeyPair genrateRsaKeyPair() {
        KeyPair keyPair;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance( "RSA" );
            keyPairGenerator.initialize( 2048 );
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception e) {
            throw new IllegalStateException( e );
        }
        return keyPair;
    }




}
