package org.acme.account.rest;


import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.acme.account.entity.User;


@Path("account")
public class Resource
{

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  public Uni< Void > create( User user )
  {

    //  Set<Authority> authorities = new HashSet<>();
    //   Authority.<Authority>findByIdOptional(AuthoritiesConstants.USER).ifPresent(authorities::add);
    //  newUser.setAuthorities(authorities);

    return User.persist( user );

  }


  @POST
  @Path("email")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Uni< User > findByIdForUpdate( final String name )
  {

    return null;
  }




}
