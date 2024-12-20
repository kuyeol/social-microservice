package org.acme.core.security.jwt;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Cookie;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;
import java.util.Objects;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    @Inject
    RedisSessionManager sessionManager;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        Map<String, Cookie> cookies = requestContext.getCookies();
        Cookie sessionCookie = cookies.get("SESSION_ID");
        String sessionId = Objects.nonNull(sessionCookie) ? sessionCookie.getValue() : null;
        if (Objects.isNull(sessionId)) {
            return;
        }


        requestContext.setSecurityContext(new SecurityContext() {

            @Override
            public Principal getUserPrincipal() {
                return () -> sessionId;
            }

            @Override
            public boolean isUserInRole(String role) {
                // You can add role-based auth in here

                return true;
            }

            @Override
            public boolean isSecure() {
                return requestContext.getUriInfo().getRequestUri().getScheme().equals("http");
            }

            @Override
            public String getAuthenticationScheme() {
                return "COOKIE"; // You can use OAUTH2, API_KEY etc
            }
        });
    }
}