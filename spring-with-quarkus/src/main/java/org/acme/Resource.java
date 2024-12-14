package org.acme;


import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

@Path("/")
public class Resource {

    @GET
    public String hello() {
        return "Hello World";
    }


    @GET
    @Path("{uri}")
    public Response serviceEntry(@PathParam( "uri" ) String uri){


        return Response.ok().build();
    }


}
