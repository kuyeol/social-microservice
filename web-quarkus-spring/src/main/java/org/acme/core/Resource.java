package org.acme.core;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.core.eventbus.EventBus;
import io.vertx.mutiny.core.eventbus.Message;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import java.util.Objects;
import org.acme.core.security.jwt.RedisSessionManager;
import org.acme.client.customer.entity.Customer;


@Path("/hello")
public class Resource {


    @Inject
    RedisSessionManager sessionManager;


    @Inject
    EventBus bus;


    @GET
    public String hello(){
        return "Hello from Quarkus REST";
    }



    @POST
    @Path("/login")

    public Response login(Customer customer) {
        // In a real application, you would authenticate the customer here.
        Customer authenticatedCustomer = new Customer();

        // Create a session
        String sessionId = sessionManager.createSession(authenticatedCustomer.getId());

        // Set the session ID in a cookie
        NewCookie sessionCookie = new NewCookie("SESSION_ID", sessionId, "/", null, "session-cookie", 3600, true, true);

        return Response.ok(authenticatedCustomer).cookie(sessionCookie).build();
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
        // In a real application, you would load customer data from database
        Customer authenticatedCustomer = new Customer();

        return Response.ok(authenticatedCustomer).build();

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
    public Uni<String> helldo(@QueryParam("name") String name) {
        return bus.<String>request("greetings", name).onItem().transform(Message::body);
    }


}
