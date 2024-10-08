package org.acme.springouauth2.federation;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import java.io.IOException;
import java.util.function.Consumer;

public class FederatedIdentityAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final AuthenticationSuccessHandler delegate = new SavedRequestAwareAuthenticationSuccessHandler();

    private Consumer< OAuth2User > oauth2UserHandler = ( user ) -> {
    };

    private Consumer< OidcUser > oidcUserHandler = ( user ) -> this.oauth2UserHandler.accept( user );


    @Override
    public void onAuthenticationSuccess( HttpServletRequest request ,
                                         HttpServletResponse response ,
                                         FilterChain chain ,
                                         Authentication authentication ) throws IOException, ServletException {
        AuthenticationSuccessHandler.super.onAuthenticationSuccess( request , response , chain , authentication );
    }

    @Override
    public void onAuthenticationSuccess( HttpServletRequest request ,
                                         HttpServletResponse response ,
                                         Authentication authentication ) throws IOException, ServletException {
//        if (authentication instanceof OAuth2AuthenticationToken) {
//            if (authentication.getPrincipal() instanceof OidcUser) {
//                this.oidcUserHandler.accept( (OidcUser) authentication.getPrincipal() );
//            } else if (authentication.getPrincipal() instanceof OAuth2User) {
//                this.oauth2UserHandler.accept( (OAuth2User) authentication.getPrincipal() );
//            }
//        }

        this.delegate.onAuthenticationSuccess( request , response , authentication );
    }


    public void setOAuth2UserHandler( Consumer< OAuth2User > oauth2UserHandler ) {
        this.oauth2UserHandler = oauth2UserHandler;
    }

    public void setOidcUserHandler( Consumer< OidcUser > oidcUserHandler ) {
        this.oidcUserHandler = oidcUserHandler;
    }


}

