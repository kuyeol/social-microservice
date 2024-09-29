package org.acme.account.rest;


import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.RequestScoped;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionStage;
import org.acme.account.entity.User;
import org.acme.account.exception.EmailAlreadyUsedException;
import org.acme.account.exception.InvalidPasswordWebException;
import org.acme.account.exception.LoginAlreadyUsedException;
import org.acme.account.exception.UsernameAlreadyUsedException;
import org.acme.account.model.UserDTO;
import org.acme.account.service.RegisterService;


@Path("account")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class Resource
{


 private final RegisterService userService;

 public Resource(RegisterService registerService)
 {
   this.userService = registerService;
 }


  @POST
  @Path( "RE" )
  @Produces(MediaType.APPLICATION_JSON)
  public  Uni<Response> create(String password)
  {
UserDTO user = new UserDTO();

return null;

  }


  @GET
  @Path("email")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Uni< User > findByIdForUpdate( final String name )
  {

    return null;
  }



  @Size(min = 1, max = 111)
  public String password;


  //@POST
  //@Path("/register")
  //@PermitAll
  //public CompletionStage<Response> registerAccount(@Valid UserDTO userDTO) {
  //  if (!checkPasswordLength(password)) {
  //    throw new InvalidPasswordWebException();
  //  }
  //  try {
  //    var user = userService.registerUser(userDTO, password);
  //    return (CompletionStage< Response >) userService.registerUser( userDTO, password);
  //  } catch ( UsernameAlreadyUsedException e) {
  //    throw new LoginAlreadyUsedException();
  //  } catch (EmailAlreadyUsedException e) {
  //    throw new EmailAlreadyUsedException();
  //  }
  //}




  private static boolean checkPasswordLength(String password) {
    return (
        !password.isEmpty() &&
        password.length() >=1 &&
        password.length() <= 111
    );
  }



  //@POST
  //@Consumes(MediaType.APPLICATION_JSON)
  //@Produces(MediaType.APPLICATION_JSON)
  //@Transactional
  //public Response create(User category) throws Exception {
  //  if (category.getId() != null) {
  //    return Response
  //        .status(Response.Status.CONFLICT)
  //        .entity("Unable to create Category, userId was already set.")
  //        .build();
  //  }
  //
  //  Category parent;
  //  if ((parent = category.getParent()) != null && parent.getId() != null) {
  //    category.setParent(get(parent.getId()));
  //  }
  //
  //  try {
  //    em.persist(category);
  //    em.flush();
  //  } catch (ConstraintViolationException cve) {
  //    return Response
  //        .status(Response.Status.BAD_REQUEST)
  //        .entity(cve.getMessage())
  //        .build();
  //  } catch (Exception e) {
  //    return Response
  //        .serverError()
  //        .entity(e.getMessage())
  //        .build();
  //  }
  //  return Response
  //      .created(new URI("category/" + category.getId().toString()))
  //      .build();
  //}
  //











}
