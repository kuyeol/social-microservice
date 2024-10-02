package org.ung.oauth;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import java.security.Principal;

import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;
import io.vertx.ext.auth.authentication.AuthenticationProvider;

@Path("/secured")
@ApplicationScoped
public class TokenResource {

  @GET()
  @Path("permit-all")
  @PermitAll
  @Produces(MediaType.TEXT_PLAIN)
  public String hello(@Context SecurityContext ctx) {
    Principal caller =  ctx.getUserPrincipal();
    String name = caller == null ? "anonymous" : caller.getName();
    String helloReply = String.format("hello + %s, isSecure: %s, authScheme: %s", name, ctx.isSecure(), ctx.getAuthenticationScheme());
    return helloReply;
  }

  @GET()
  @Path("roles-allowed")
  @RolesAllowed({"Echoer", "Subscriber"})
  @Produces(MediaType.TEXT_PLAIN)
  public String helloRolesAllowed(@Context SecurityContext ctx) {
    Principal caller =  ctx.getUserPrincipal();
    String name = caller == null ? "anonymous" : caller.getName();
    String helloReply = String.format("hello + %s, isSecure: %s, authScheme: %s", name, ctx.isSecure(), ctx.getAuthenticationScheme());
    return helloReply;
  }
}
