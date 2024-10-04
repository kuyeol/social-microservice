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
import org.springframework.http.MediaType;
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
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

@Configuration
@EnableWebSecurity
public class OAuth2SecurityConfig {

  @Bean
  @Order(1)
  // @Order(Ordered.HIGHEST_PRECEDENCE)
  // Using protocol EndPonint filterChain
  //프로토콜 엔드포인트를 위한 Spring Security 필터 체인
  public SecurityFilterChain authServerFilterChain(HttpSecurity http) throws Exception {

    OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

    //Enabled OPENID CONNECT
    http.getConfigurer(OAuth2AuthorizationServerConfigurer.class).oidc(Customizer.withDefaults());

    // Redirect to the login page when not authenticated from the
    // authorization endpoint
    http.exceptionHandling(
            (exception) -> exception.defaultAuthenticationEntryPointFor(new LoginUrlAuthenticationEntryPoint("/login"),
                new MediaTypeRequestMatcher(MediaType.TEXT_HTML)))
        .oauth2ResourceServer((resourceServer) -> resourceServer.jwt(Customizer.withDefaults()));

    return http.build();
  }

  @Bean
  @Order(2) //INVOKE RRIORTY
  //	인증을 위한 Spring Security 필터 체인 .
  public SecurityFilterChain standardFilterChain(HttpSecurity http) throws Exception {

    http.authorizeHttpRequests((authorizeRequests) -> authorizeRequests.anyRequest().authenticated())
        .formLogin(Customizer.withDefaults());

    return http.build();
  }

  //	고객 관리를 위한 인스턴스입니다 RegisteredClientRepository.
  @Bean
  public RegisteredClientRepository registeredClientInvoke() {

    RegisteredClient oidcClient = RegisteredClient.withId(UUID.randomUUID().toString())
        .clientId("client")
        .clientSecret("secret")
        .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
        .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
        //.authorizationGrantType(AuthorizationGrantType.JWT_BEARER)
        .redirectUri("http://127.0.0.1:8080/login/ouath/code/client")
        .redirectUri("http://127.0.0.1:8080/")
        .scope(OidcScopes.OPENID)
        .scope(OidcScopes.PROFILE)
        .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
        .build();

    RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
        .clientId("messaging-client")
        .clientSecret("{noop}secret")
        .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
        .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
        .scope("message:read")
        .scope("message:write")
        .build();

    return new InMemoryRegisteredClientRepository(oidcClient);
  }

  //	액세스 토큰에 서명하기 위한 인스턴스입니다
  @Bean
  public JWKSource<SecurityContext> jwkSource(KeyPair keyPair) {

    RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

    RSAPrivateCrtKey privateCrtKey = (RSAPrivateCrtKey) keyPair.getPrivate();

    RSAKey rsaKey = new RSAKey.Builder(publicKey).privateKey(privateCrtKey).keyID(UUID.randomUUID().toString()).build();

    JWKSet jwkSet = new JWKSet(rsaKey);

    return new ImmutableJWKSet<>(jwkSet);
  }

  //JwtDecoder서명된 액세스 토큰을 디코딩하는 인스턴스입니다 .
  @Bean
  public JwtDecoder jwtDecoder(KeyPair keyPair) {

    return NimbusJwtDecoder.withPublicKey((RSAPublicKey) keyPair.getPublic()).build();
  }

  //  AuthorizationServerSettingsSpring Authorization Server를 구성하는 인스턴스입니다 .
  @Bean
  public AuthorizationServerSettings providerSettings() {
    return AuthorizationServerSettings.builder().issuer("http://localhost:9000").build();
  }

  //	UserDetailsService인증을 위해 사용자를 검색하는 인스턴스입니다
  @Bean
  public UserDetailsService userDetailsService() {
    UserDetails userDetails =
        User.withDefaultPasswordEncoder().username("user").password("password").roles("USER").build();

    return new InMemoryUserDetailsManager(userDetails);
  }

  //	java.security.KeyPair위의 항목을 만드는 데 사용된 시작 시 생성된 키가 있는 인스턴스입니다 JWKSource.
  @Bean
  @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
  KeyPair genrateRsaKeyPair() {
    KeyPair keyPair;
    try {
      KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
      keyPairGenerator.initialize(2048);
      keyPair = keyPairGenerator.generateKeyPair();
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
    return keyPair;
  }
}
