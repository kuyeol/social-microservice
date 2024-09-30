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
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.jboss.logging.Logger;




@Path("/users")
public class UsersResource
{

  private static final Logger logger              = Logger.getLogger( UsersResource.class );

  private static final String SEARCH_ID_PARAMETER = "id:";

 //protected final AdminPermissionEvaluator auth;


//protected final   KeycloakSession        session;





  public UsersResource( )
  {

    //this.session = session;
   //this.auth    = auth;

  }



  @GET
  public String AA(){
    return "dddddddddd";
  }


  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Operation(summary = "Create a new user Username must be unique.")
  public Response createUser( final UserRepresentation rep )
  {
    // first check if user has manage rights

    String username = rep.getUsername();

    UserProfileProvider profileProvider = session.getProvider( UserProfileProvider.class );

    //   USER_API(true, false, false)

    UserProfile profile = profileProvider.create( USER_API, rep.getRawAttributes() );

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
      throw ErrorResponse.exists( "User exists with same username or email" );
    } catch ( PasswordPolicyNotMetException e ) {
      logger.warn( "Password policy not met for user " + e.getUsername(), e );

      throw new ErrorResponseException( e.getMessage(), MessageFormat.format( e.getMessage(), e.getMessage(), e.getParameters() ),
                                        Response.Status.BAD_REQUEST );
    } catch ( ModelIllegalStateException e ) {
      logger.error( e.getMessage(), e );
      throw ErrorResponse.error( e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR );
    } catch ( ModelException me ) {
      logger.warn( "Could not create user", me );
      throw ErrorResponse.error( "Could not create user", Response.Status.BAD_REQUEST );
    }
  }


  //@Path("{user-id}")
  //public UserResource user( final @PathParam("user-id") String id )
  //{
  //
  //  UserModel user = null;
  //
  //  if ( user == null ) {
  //    // we do this to make sure somebody can't phish ids
  //    if ( auth.users()
  //             .canQuery() ) {
  //      throw new NotFoundException( "User not found" );
  //    } else {
  //      throw new ForbiddenException();
  //    }
  //  }
  //
  //  return new UserResource( session, user, auth );
  //}


    //@Path("profile")
    //public UserProfileResource userProfile()
    //{
    //
    //  return new UserProfileResource( session, auth );
    //}


  //private Stream< UserRepresentation > toRepresentation( UserPermissionEvaluator usersEvaluator, Boolean briefRepresentation, Stream< UserModel > userModels )
  //{
  //
  //  boolean briefRepresentationB = briefRepresentation != null && briefRepresentation;
  //  boolean canViewGlobal        = usersEvaluator.canView();
  //
  //  usersEvaluator.grantIfNoPermission( session.getAttribute( UserModel.GROUPS ) != null );
  //  return userModels.filter( user -> canViewGlobal || usersEvaluator.canView( user ) )
  //                   .map( user ->
  //                           {
  //                             UserRepresentation userRep = briefRepresentationB
  //                                                          ? ModelToRepresentation.toBriefRepresentation( user )
  //                                                          : ModelToRepresentation.toRepresentation( session, user );
  //                             userRep.setAccess( usersEvaluator.getAccess( user ) );
  //                             return userRep;
  //                           } );
  //}





}
