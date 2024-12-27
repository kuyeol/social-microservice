package org.acme.client.ungorithm;


import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("ungorithm")
public class JpaResource {

    @Inject
    Dao dao;

    @POST
    @Path("{record}")
    @Produces(APPLICATION_JSON)
    public Response createRecord(@Valid JpaEntity rep, @PathParam("record") String x) {


JpaEntityRep rr=JpaEntityRep.from(rep);

JpaEntity entity = new JpaEntity();
entity.setUsername(x);


        dao.create(entity);

        return Response.ok( rr).build();
    }

    @POST
    @Path("{a}/{b}")
    @Produces(APPLICATION_JSON)
    public Response create(@Valid Repesentaion rep, @PathParam("a") String a, @PathParam("b") String b) {

        JpaEntity entity = new JpaEntity();


        Map<String, List<String>> map = new HashMap<>();



        for (Map.Entry<String, List<String>> t : map.entrySet()) {


            entity.setInputOne(t.getKey());
            System.out.println(t.getKey().equals("username") ? t.getValue() : "false");

        }


        PasswordStore ps = new PasswordStore(entity);

        entity.addAttributes(a, b);

        ps.createCredential(a, b);

        entity.setCredentials(ps.getCredentials());


        Repesentaion R = dao.create(entity);

        return Response.ok(R).build();
    }

    //
    //@POST
    //@Path("{a}/{b}")
    //@Produces(APPLICATION_JSON)
    //public Response create(@Valid JpaEntity entity, @PathParam("a") String a, @PathParam("b") String b) {
    //
    //
    //    PasswordStore ps = new PasswordStore(entity);
    //
    //    entity.addAttributes(a, b);
    //    ps.createCredential(a, b);
    //
    //    entity.setCredentials(ps.getCredentials());
    //
    //    Repesentaion R = dao.create(entity);
    //
    //    return Response.ok(R).build();
    //}


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

        return Response.ok(R).build();
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

