package org.acme.client.ungorithm;


import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("ungorithm")
public class JpaResource {

    @Inject
    Dao dao;


    @POST
@Produces(MediaType.APPLICATION_JSON)
    public Response create(JpaEntity entity) {


             Repesentaion R = new Repesentaion();

             dao.create(entity);


        return Response.ok(R).build();
    }


    @GET
    @Path("{entity}")
    public Response read(@PathParam("entity") String name) {


        Repesentaion RE = dao.find(name);

        if (RE == null) {
            Repesentaion ree = new Repesentaion();

            return Response.ok(ree).build();
        } else {


            return Response.ok(RE).build();
        }
    }

    @GET
    @Path("/1/{entity1}")
    public Response read2(@PathParam("entity1") Long name) {


       // UserProperties RE = dao.findProp(name);
        UserProperties RE1 = dao.findProp(name);
        if (RE1 == null) {


            return Response.ok("ddd").build();
        } else {


            return Response.ok(RE1).build();
        }
    }




}

