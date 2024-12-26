package org.acme.client.ungorithm;


import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("ungorithm")
public class JpaResource {

    @Inject
    Dao dao;

    @POST
    @Path("{record}")
    @Produces(APPLICATION_JSON)
    public Response createRecord( @Valid JpaEntityRep entityRep, @PathParam("record") String x) {
      JpaEntity J =  entityRep.jpaEntity();
        dao.create(J);

        return Response.ok(J).build();
    }


    @POST
    @Path("{a}/{b}")
    @Produces(APPLICATION_JSON)
    public Response create(JpaEntity entity, @PathParam("a") String a, @PathParam("b") String b) {

        entity.addAttributes(a, b);
        Repesentaion R = dao.create(entity);


        return Response.ok(R).build();
    }


    @POST
    @Path("{pass}")
    @Produces(APPLICATION_JSON)
    public Response createPass(@RequestBody(
        name = "entity",
        required = true,
        content = @Content(
            mediaType = APPLICATION_JSON,
            schema = @Schema(implementation = JpaEntity.class),
            examples = @ExampleObject(name = "fightRequest", value = Examples.create_Entity)
        )
    ) @Valid JpaEntity entity, @PathParam("pass") String x) {

        dao.findByName(entity.getId());

        PasswordStore passwordStore = new PasswordStore(entity);
        passwordStore.createCredential(x, x);
        entity.setCredentials(passwordStore.getCredentials());

        dao.save(entity);
        return Response.ok().build();
    }


    @GET
    @Path("{entity}")
    public Response read(@PathParam("entity") String name) {


        Repesentaion RE = dao.findByName(name);

        if (RE == null) {

            Repesentaion ree = new Repesentaion();

            return Response.ok(ree).build();
        } else {


            return Response.ok(RE).build();
        }
    }


    @GET
    @Path("/1/{entity1}")
    public Response read2(@PathParam("entity1") String name) {


        // UserProperties RE = dao.findProp(name);
        UserAttributes RE1 = dao.findProp(name);
        if (RE1 == null) {


            return Response.ok("ddd").build();
        } else {


            return Response.ok(RE1).build();
        }
    }


}

