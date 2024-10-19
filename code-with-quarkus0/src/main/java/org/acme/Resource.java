package org.acme;


import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/dd")
public class Resource {

    @GET
    public String hello() {
        return "Hello World";
    }
}
