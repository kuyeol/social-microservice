package org.acme;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.acme.object.MyEntity;
import org.acme.repo.JpaRepository;

@Path("/hello")
@ApplicationScoped
public class ExampleResource
{

    @Inject
    JpaRepository repo;


    @POST
    @Path("/count")
    @Transactional
    public String count(@QueryParam("id") String id)
    {

        MyEntity entity = new MyEntity();
        entity.setId(id);
        entity.setName("John");
        entity.setAge(30);
        repo.persistRepo(entity);

        return entity.getId();
    }


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello()
    {

        return "Hello from Quarkus REST";
    }


}
