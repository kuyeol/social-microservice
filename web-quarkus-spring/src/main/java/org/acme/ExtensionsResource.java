package org.acme;


import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;
import org.acme.service.customer.entity.User;


@ApplicationScoped
@Path("/user")
public class ExtensionsResource {


    @Inject
    EntityManager em;

    @Transactional
    public void addUser(String a) {

        User u = new User();
        u.setUsername(a);
        u.setPassword(a);
        em.persist(u);

    }


    @POST
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello(String a) {

        addUser(a);

        return "hello ";
    }


    @GET
    @RolesAllowed("user")
    @Path("/me")
    public String me(@Context SecurityContext securityContext) {
        String ctx = securityContext.getUserPrincipal().getName();

        if (ctx == null) {
            return "NOT ALLOWED";
        } else {
            return ctx;
        }
    }
    // curl -i -X GET -u user:user http://localhost:8080/user/me

}
