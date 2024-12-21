package org.acme.client.customer.rest;

// Suggested code may be subject to a license. Learn more: ~LicenseLog:1181483643.
// Suggested code may be subject to a license. Learn more: ~LicenseLog:410379119.
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;



@ApplicationScoped
@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {



@Inject
CustomerService customerService;



@GET
public String hello() {
    return "hello";
}




    







}
