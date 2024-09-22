package org.acme.account.rest;


import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.groups.UniIfNoItem;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.account.entity.User;
import org.acme.account.model.UserDTO;
import org.acme.account.service.RegisterService;


@Path("account")
public class Resource
{


 private final RegisterService registerService;

 public Resource(RegisterService registerService)
 {
   this.registerService = registerService;
 }


  @POST
  @Path( "RE" )
  @Produces(MediaType.APPLICATION_JSON)
  public  Uni<Response> create(String password)
  {
UserDTO user = new UserDTO();
User U = registerService.registerUser(user,password  );
    return Uni.createFrom().item(U  ).map( u -> Response.ok(u).build() );

  }


  @GET
  @Path("email")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Uni< User > findByIdForUpdate( final String name )
  {

    return null;
  }


@GET
  public String home(){
    return "home";
}

}
