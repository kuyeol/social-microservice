package org.account.rest;


import java.net.URI;
import java.text.MessageFormat;
import java.util.stream.Stream;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.account.exception.ErrorResponseException;
import org.account.exception.ModelDuplicateException;
import org.account.exception.ModelException;
import org.account.exception.ModelIllegalStateException;
import org.account.exception.PasswordPolicyNotMetException;
import org.account.represetion.RepresentationToModel;
import org.account.represetion.identitymanagement.ErrorResponse;
import org.account.represetion.identitymanagement.UserRepresentation;
import org.account.service.Session;
import org.account.service.UserModel;
import org.account.service.userprofile.UserProfile;
import org.account.service.userprofile.UserProfileProvider;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.jboss.logging.Logger;

import static org.account.service.userprofile.UserProfileContext.USER_API;


@Path("/users")
@ApplicationScoped
public class UsersResource
{

  private static final Logger logger              = Logger.getLogger( UsersResource.class );

  private static final String SEARCH_ID_PARAMETER = "id:";

 //protected final AdminPermissionEvaluator auth;

@Inject
 Session session;

@Inject
 HttpHeaders headers;






  @GET
  public String AA(){
    return "dddddddddd";
  }


  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Operation(summary = "Create a new customer Username must be unique.")
  public Response createUser( final UserRepresentation rep )
  {
    // first check if customer has manage rights

    String username = rep.getUsername();

    UserProfileProvider profileProvider = session.getProvider(UserProfileProvider.class);

    //   USER_API(true, false, false)

    UserProfile profile = profileProvider.create(USER_API, rep.getRawAttributes());

    try {
      Response response = UserResource.validateUserProfile( profile );
      if ( response != null ) {
        return response;
      }

      UserModel user = profile.create();

      RepresentationToModel.createCredentials( rep,  user, true );

      return Response.created( URI.create( user.getId() ) )
                     .build();

      // Exception Process Entry Point
    } catch ( ModelDuplicateException e ) {
      throw ErrorResponse.exists( "Customer exists with same username or email" );
    } catch ( PasswordPolicyNotMetException e ) {
      logger.warn( "Password policy not met for customer " + e.getUsername(), e );

      throw new ErrorResponseException( e.getMessage(), MessageFormat.format( e.getMessage(), e.getMessage(), e.getParameters() ),
                                        Response.Status.BAD_REQUEST );
    } catch ( ModelIllegalStateException e ) {
      logger.error( e.getMessage(), e );
      throw ErrorResponse.error( e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR );
    } catch ( ModelException me ) {
      logger.warn( "Could not create customer", me );
      throw ErrorResponse.error( "Could not create customer", Response.Status.BAD_REQUEST );
    }
  }


  @Path("{customer-id}")
  public UserResource user( final @PathParam("customer-id") String id )
  {

    UserModel user = null;

    if ( user == null ) {
      // we do this to make sure somebody can't phish ids
      if ( id==null ) {
        throw new NotFoundException( "Customer not found" );
      } else {
        throw new ForbiddenException();
      }
    }

    return new UserResource(session, user );
  }


    //@Path("profile")
    //public UserProfileResource userProfile()
    //{
    //
    //  return new UserProfileResource( session, auth );
    //}


  //private Stream< UserRepresentation > toRepresentation( UserPermissionEvaluator usersEvaluator, Boolean briefRepresentation, Stream< CustomerModel > userModels )
  //{
  //
  //  boolean briefRepresentationB = briefRepresentation != null && briefRepresentation;
  //  boolean canViewGlobal        = usersEvaluator.canView();
  //
  //  usersEvaluator.grantIfNoPermission( session.getAttribute( CustomerModel.GROUPS ) != null );
  //  return userModels.filter( customer -> canViewGlobal || usersEvaluator.canView( customer ) )
  //                   .map( customer ->
  //                           {
  //                             UserRepresentation userRep = briefRepresentationB
  //                                                          ? ModelToRepresentation.toBriefRepresentation( customer )
  //                                                          : ModelToRepresentation.toRepresentation( session, customer );
  //                             userRep.setAccess( usersEvaluator.getAccess( customer ) );
  //                             return userRep;
  //                           } );
  //}





}
