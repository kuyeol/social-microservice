package org.acme;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.core.eventbus.EventBus;
import io.vertx.mutiny.core.eventbus.Message;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import java.util.Objects;
import org.acme.security.RedisSessionManager;
import org.acme.service.customer.entity.User;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.jboss.resteasy.reactive.RestQuery;

@Path("/")
public class Resource {


    @Inject
    RedisSessionManager sessionManager;


    @Inject
    EventBus bus;


    @POST
    @Path("/login")

    public Response login(User user) {
        // In a real application, you would authenticate the user here.
        User authenticatedUser = new User();

        // Create a session
        String sessionId = sessionManager.createSession(authenticatedUser.getId());

        // Set the session ID in a cookie
        NewCookie sessionCookie = new NewCookie("SESSION_ID", sessionId, "/", null, "session-cookie", 3600, true, true);

        return Response.ok(authenticatedUser).cookie(sessionCookie).build();
    }




    @GET
    @Path("/me")
    public Response getMe(@Context SecurityContext sec) {
        String sessionId = getSessionIdFromSecurityContext(sec);

        if (Objects.isNull(sessionId)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        String userId = sessionManager.getUserIdFromSession(sessionId);
        if (Objects.isNull(userId)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        // In a real application, you would load user data from database
        User authenticatedUser = new User();

        return Response.ok(authenticatedUser).build();

    }


    private String getSessionIdFromSecurityContext(SecurityContext sec) {
        if (sec.getUserPrincipal() == null) {
            return null;
        }
        return sec.getUserPrincipal().getName();
    }


    @POST
    @Path("{path}")
    @Produces(MediaType.APPLICATION_JSON)
    public String hello(@PathParam("path") String path) {


        return path;
    }


    @GET
    @Path("/hello")
    public Uni<String> helldo(@RestQuery String name) {
        return bus.<String>request("greetings", name).onItem().transform(Message::body);
    }


}
