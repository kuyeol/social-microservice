package org.acme.springouauth2.rest.authetication;

import jakarta.servlet.http.HttpServletRequest;
import org.acme.springouauth2.authetication.CustomCodeGrantAuthenticationToken;
import org.springframework.http.HttpMethod;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;

public class GrantTypeAuthenticationConverter implements AuthenticationConverter {
    private final RequestMatcher deviceAuthorizationRequestMatcher;
    private final RequestMatcher deviceAccessTokenRequestMatcher;

    public static final String GRANT_TYPE = "grant_type";

    public GrantTypeAuthenticationConverter( String authorizationEndpointUri ) {


        RequestMatcher clientIdParameterMatcher = request -> request.getParameter(
                OAuth2ParameterNames.CLIENT_ID ) != null;
        this.deviceAuthorizationRequestMatcher = new AndRequestMatcher(
                new AntPathRequestMatcher( authorizationEndpointUri , HttpMethod.POST.name() ) ,
                clientIdParameterMatcher );
        this.deviceAccessTokenRequestMatcher = request -> AuthorizationGrantType.DEVICE_CODE.getValue()
                                                                                            .equals(
                                                                                                    request.getParameter(
                                                                                                            OAuth2ParameterNames.GRANT_TYPE ) ) && request.getParameter(
                OAuth2ParameterNames.DEVICE_CODE ) != null && request.getParameter(
                OAuth2ParameterNames.CLIENT_ID ) != null;
    }


    @Nullable
    @Override
    public Authentication convert( HttpServletRequest request ) {

        if (!this.deviceAuthorizationRequestMatcher.matches( request ) && !this.deviceAccessTokenRequestMatcher.matches(
                request )) {
            return null;
        }

        // client_id (REQUIRED)
        String clientId = request.getParameter( OAuth2ParameterNames.CLIENT_ID );
        if (!StringUtils.hasText( clientId ) || request.getParameterValues(
                OAuth2ParameterNames.CLIENT_ID ).length != 1) {
            throw new OAuth2AuthenticationException( OAuth2ErrorCodes.INVALID_REQUEST );
        }


        return new CustomCodeGrantAuthenticationToken( clientId , ClientAuthenticationMethod.NONE , null , null );


    }
}