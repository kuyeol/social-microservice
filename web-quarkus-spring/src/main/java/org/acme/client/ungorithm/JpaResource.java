package org.acme.client.ungorithm;



import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import java.util.Collection;
import java.util.LinkedList;
import org.acme.client.ungorithm.dto.PasswordTransForm;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("ungorithm")
public class JpaResource {

    @Inject
    Dao dao;

    
    @POST
    @Path("name")
    @Blocking
    public Uni<Response> hello(Repesentaion dto) {
        JpaEntity entity = new JpaEntity();
        entity.setUsername(dto.getUsername());


        dao.create(entity);

        //return Uni.createFrom().item(Response.ok("Hello " + name).build());


        return Uni.createFrom()
                  .emitter(emitter -> {
                      emitter.complete(Response.ok(dto)
                                               .build());
                  });

    }

    @POST
    @Path("{a}/{b}")
    @Produces(APPLICATION_JSON)
    public Response create(@Valid UserDto rep, @PathParam("a") String a, @PathParam("b") String b) {

        JpaEntity entity = new JpaEntity();

        entity.setUsername(rep.repesentaion()
                              .getUsername());
        entity.getCredentials();

        Collection<TestCredential> testCredential = new LinkedList<>();

        dao.create(entity);

        return Response.ok(entity)
                       .build();
    }


    @POST
    @Path("findSecret")
    @Produces(APPLICATION_JSON)
    public Response findSecret(@QueryParam("id") String id) {

        TestCredential testCredential;
        JpaEntity      entity;


        if (dao.reference(id) != null) {

            entity         = dao.reference(id);
            testCredential = dao.findCred(id, entity);

            PasswordTransForm psf = new PasswordTransForm(testCredential);

            testCredential = psf.input(testCredential);
            dao.save(testCredential);



            return Response.ok(psf.output(testCredential))
                           .build();
        } else {

            return Response.ok("testCredential")
                           .build();
        }
    }

    @PUT
    @Path("update")
    @Produces(APPLICATION_JSON)
    public Response update(@QueryParam("id") String id) {

        if (dao.update(id)
               .isPresent()) {

            return Response.ok("dddddd")
                           .build();
        } else {

            JsonObject jo = new JsonObject(id);
            jo.getInstant(id);
            jo.fieldNames();

            return Response.serverError()
                           .build();
        }

    }

    @DELETE
    @Path("remove")
    @Produces(APPLICATION_JSON)
    public Response remove(@QueryParam("id") String id) {

        if (dao.remove(id)
               .isPresent()) {

            return Response.ok("dddddd")
                           .build();
        } else {
            JsonObject jo = new JsonObject(id);
            jo.getInstant(id);
            jo.fieldNames();

            return Response.serverError()
                           .build();
        }

    }


    @POST
    @Path("{pass}")
    @Produces(APPLICATION_JSON)
    public Response createPass(@Valid JpaEntity entity, @PathParam("pass") String x) {


        //if (dao.findByName(entity.getId()) != null) {
        //    return Response.status(Response.Status.CONFLICT).build();
        //} else {
        //
        //    entity.addAttributes(x, x+"1");
        //
        //    PasswordStore passwordStore = new PasswordStore(entity);
        //
        //    passwordStore.createCredential(x, x);
        //
        //    entity.setCredentials(passwordStore.getCredentials());
        //
        //    Repesentaion R = dao.create(entity);
        //
        //    return Response.ok(R).build();
        //}


        PasswordStore ps = new PasswordStore(entity);

        entity.addAttributes(x, x);
        ps.createCredential(x, x);

        entity.setCredentials(ps.getCredentials());

        Repesentaion R = dao.create(entity);

        return Response.ok(R)
                       .build();
    }


    @GET
    @Path("{entity}")
    public Response read(@PathParam("entity") String name) {


        Repesentaion RE = dao.findByName(name);

        if (RE == null) {

            Repesentaion ree = new Repesentaion();

            return Response.ok(ree)
                           .build();
        } else {


            return Response.ok(RE)
                           .build();
        }
    }


    @GET
    @Path("/1/{entity1}")
    public Response read2(@PathParam("entity1") String name) {


        // UserProperties RE = dao.findProp(name);
        UserAttributes RE1 = dao.findProp(name);
        if (RE1 == null) {


            return Response.ok("ddd")
                           .build();
        } else {


            return Response.ok(RE1)
                           .build();
        }
    }


}

