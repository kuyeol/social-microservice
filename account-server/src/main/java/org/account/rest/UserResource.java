package org.account.rest;


import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import org.account.model.UserModel;
import org.account.represetion.identitymanagement.ErrorRepresentation;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.NoCache;




public class UserResource
{

  private static final Logger                   logger = Logger.getLogger( UserResource.class );

  private final        AdminPermissionEvaluator auth;

  private final UserModel user;

  protected final      KeycloakSession          session;


  public UserResource( KeycloakSession session, UserModel user, AdminPermissionEvaluator auth )
  {

    this.session = session;
    this.auth    = auth;
    this.user    = user;

  }
  public static Response validateUserProfile( String profile)
  {

    try {
      profile.validate();
    } catch ( ValidationException pve ) {
      List< ErrorRepresentation > errors = new ArrayList<>();
      for ( ValidationException.Error error : pve.getErrors() ) {
        // some messages are managed directly as before
        switch ( error.getMessage() ) {
          //case Messages.MISSING_USERNAME -> throw ErrorResponse.error( "User name is missing", Status.BAD_REQUEST );
          //case Messages.USERNAME_EXISTS -> throw ErrorResponse.exists( "User exists with same username" );
          //case Messages.EMAIL_EXISTS -> throw ErrorResponse.exists( "User exists with same email" );
          case "ERROR" -> throw ErrorResponse.error( "dddd", Status.BAD_REQUEST );
        }
        errors.add( new ErrorRepresentation( error.getAttribute(), error.getMessage(), error.getMessageParameters() ) );
      }

      throw ErrorResponse.errors( errors, Status.BAD_REQUEST );
    }

    return null;
  }


  public static Response validateUserProfile( UserProfile profile)
  {

    try {
      profile.validate();
    } catch ( ValidationException pve ) {
      List< ErrorRepresentation > errors = new ArrayList<>();
      for ( ValidationException.Error error : pve.getErrors() ) {
        // some messages are managed directly as before
        switch ( error.getMessage() ) {
          //case Messages.MISSING_USERNAME -> throw ErrorResponse.error( "User name is missing", Status.BAD_REQUEST );
          //case Messages.USERNAME_EXISTS -> throw ErrorResponse.exists( "User exists with same username" );
          //case Messages.EMAIL_EXISTS -> throw ErrorResponse.exists( "User exists with same email" );
          case "ERROR" -> throw ErrorResponse.error( "dddd", Status.BAD_REQUEST );
        }
        errors.add( new ErrorRepresentation( error.getAttribute(), error.getMessage(), error.getMessageParameters() ) );
      }

      throw ErrorResponse.errors( errors, Status.BAD_REQUEST );
    }

    return null;
  }


  public static void updateUserFromRep( UserProfile profile, UserModel user, UserRepresentation rep, KeycloakSession session, boolean isUpdateExistingUser )
  {

    boolean removeMissingRequiredActions = isUpdateExistingUser;

    if ( rep.isEnabled() != null ) {
      user.setEnabled( rep.isEnabled() );
    }
    if ( rep.isEmailVerified() != null ) {
      user.setEmailVerified( rep.isEmailVerified() );
    }
    if ( rep.getCreatedTimestamp() != null && ! isUpdateExistingUser ) {
      user.setCreatedTimestamp( rep.getCreatedTimestamp() );
    }

    List< String > reqActions = rep.getRequiredActions();

    if ( reqActions != null ) {
      System.out.println( "List< String > reqActions = rep.getRequiredActions();" );
    }

    List< CredentialRepresentation > credentials = rep.getCredentials();
    if ( credentials != null ) {
      for ( CredentialRepresentation credential : credentials ) {
        if ( CredentialRepresentation.PASSWORD.equals( credential.getType() ) && credential.isTemporary() != null && credential.isTemporary() ) {
          user.addRequiredAction( UserModel.RequiredAction.UPDATE_PASSWORD );
        }
      }
    }
  }


  @Path("sessions")
  @GET
  @NoCache
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(summary = "Get sessions associated with the user")
  public Stream< UserSessionRepresentation > getSessions()
  {

    auth.users()
        .requireView( user );
    return session.sessions()
                  .getUserSessionsStream( user )
                  .map( ModelToRepresentation::toRepresentation );
  }


  //@Path("logout")
  //@POST
  //@Operation(summary = "Remove all user sessions associated with the user Also send notification to all clients that have an admin URL to invalidate the sessions for the particular user.")
  //@APIResponse(responseCode = "204", description = "No Content")
  //public void logout()
  //{
  //
  //  session.sessions()
  //         .getUserSessionsStream( user )
  //         .collect( Collectors.toList() );
  //
  //}


  /**
   * Delete the user
   */
  @DELETE
  @NoCache
  @Operation(summary = "Delete the user")
  public Response deleteUser()
  {

    auth.users()
        .requireManage( user );

    boolean removed = new UserManager( session ).removeUser( user );
    if ( removed ) {
      return Response.noContent()
                     .build();
    } else {
      throw ErrorResponse.error( "User couldn't be deleted", Status.BAD_REQUEST );
    }
  }


  /**
   * Disable all credentials for a user of a specific type
   *
   * @param credentialTypes
   */
  @Path("disable-credential-types")
  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  @Operation(summary = "Disable all credentials for a user of a specific type")
  public void disableCredentialType( List< String > credentialTypes )
  {

    auth.users()
        .requireManage( user );
    if ( credentialTypes == null ) {
      return;
    }
    for ( String type : credentialTypes ) {
      user.credentialManager()
          .disableCredentialType( type );

    }
  }


  /**
   * Set up a new password for the user.
   *
   * @param cred The representation must contain a rawPassword with the plain-text password
   */
  @Path("reset-password")
  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  @Operation(summary = "Set up a new password for the user.")
  public void resetPassword(
      @Parameter(description = "The representation must contain a rawPassword with the plain-text password") CredentialRepresentation cred )
  {

    auth.users()
        .requireManage( user );
    if ( cred == null || cred.getValue() == null ) {
      throw new BadRequestException( "No password provided" );
    }
    if ( Validation.isBlank( cred.getValue() ) ) {
      throw new BadRequestException( "Empty password not allowed" );
    }

    try {
      user.credentialManager()
          .updateCredential( UserCredentialModel.password( cred.getValue(), false ) );
    } catch ( IllegalStateException ise ) {
      throw new BadRequestException( "Resetting to N old passwords is not allowed." );
    } catch ( ReadOnlyException mre ) {
      throw new BadRequestException( "Can't reset password as account is read only" );
    } catch ( PasswordPolicyNotMetException e ) {
      logger.warn( "Password policy not met for user " + e.getUsername(), e );
      Properties messages = new Properties();
      messages.setProperty( "admin exception", e.getMessage() );
      throw new ErrorResponseException( e.getMessage(), MessageFormat.format( messages.getProperty( e.getMessage(), e.getMessage() ), e.getParameters() ),
                                        Status.BAD_REQUEST );
    } catch ( ModelIllegalStateException e ) {
      logger.error( e.getMessage(), e );
      throw ErrorResponse.error( e.getMessage(), Status.INTERNAL_SERVER_ERROR );
    } catch ( ModelException e ) {
      logger.warn( "Could not update user password.", e );
      Properties messages = new Properties();
      messages.setProperty( "admin exception", e.getMessage() );
      throw new ErrorResponseException( e.getMessage(), MessageFormat.format( messages.getProperty( e.getMessage(), e.getMessage() ), e.getParameters() ),
                                        Status.BAD_REQUEST );
    }
    if ( cred.isTemporary() != null && cred.isTemporary() ) {
      user.addRequiredAction( UserModel.RequiredAction.UPDATE_PASSWORD );
    } else {
      // Remove a potentially existing UPDATE_PASSWORD action when explicitly assigning a non-temporary password.
      user.removeRequiredAction( UserModel.RequiredAction.UPDATE_PASSWORD );
    }

  }


  @GET
  @Path("credentials")
  @NoCache
  @Produces(MediaType.APPLICATION_JSON)
  @Operation()
  public Stream< CredentialRepresentation > credentials()
  {

    auth.users()
        .requireView( user );
    return user.credentialManager()
               .getStoredCredentialsStream()
               .map( ModelToRepresentation::toRepresentation )
               .peek( credentialRepresentation -> credentialRepresentation.setSecretData( null ) );
  }


  @Path("credentials/{credentialId}")
  @DELETE
  @NoCache
  @Operation(summary = "Remove a credential for a user")
  public void removeCredential( final @PathParam("credentialId") String credentialId )
  {

    auth.users()
        .requireManage( user );
    CredentialModel credential = user.credentialManager()
                                     .getStoredCredentialById( credentialId );
    if ( credential == null ) {
      // we do this to make sure somebody can't phish ids
      if ( auth.users()
               .canQuery() ) {
        throw new NotFoundException( "Credential not found" );
      } else {
        throw new ForbiddenException();
      }
    }
    user.credentialManager()
        .removeStoredCredentialById( credentialId );

  }


  @PUT
  @Consumes(MediaType.TEXT_PLAIN)
  @Path("credentials/{credentialId}/userLabel")
  @Operation(summary = "Update a credential label for a user")
  public void setCredentialUserLabel( final @PathParam("credentialId") String credentialId, String userLabel )
  {

    auth.users()
        .requireManage( user );
    CredentialModel credential = user.credentialManager()
                                     .getStoredCredentialById( credentialId );
    if ( credential == null ) {
      // we do this to make sure somebody can't phish ids
      if ( auth.users()
               .canQuery() ) {
        throw new NotFoundException( "Credential not found" );
      } else {
        throw new ForbiddenException();
      }
    }
    user.credentialManager()
        .updateCredentialLabel( credentialId, userLabel );
  }


  @Path("credentials/{credentialId}/moveToFirst")
  @POST
  @Operation(summary = "Move a credential to a first position in the credentials list of the user")
  @APIResponse(responseCode = "204", description = "No Content")
  public void moveCredentialToFirst( final @Parameter(description = "The credential to move") @PathParam("credentialId") String credentialId )
  {

    moveCredentialAfter( credentialId, null );
  }


  @Path("credentials/{credentialId}/moveAfter/{newPreviousCredentialId}")
  @POST
  @Operation(summary = "Move a credential to a position behind another credential")
  @APIResponse(responseCode = "204", description = "No Content")
  public void moveCredentialAfter( final @Parameter(description = "The credential to move") @PathParam("credentialId") String credentialId,
      final @Parameter(description = "The credential that will be the previous element in the list. If set to null, the moved credential will be the first element in the list.") @PathParam("newPreviousCredentialId") String newPreviousCredentialId )
  {

    auth.users()
        .requireManage( user );
    CredentialModel credential = user.credentialManager()
                                     .getStoredCredentialById( credentialId );
    if ( credential == null ) {
      // we do this to make sure somebody can't phish ids
      if ( auth.users()
               .canQuery() ) {
        throw new NotFoundException( "Credential not found" );
      } else {
        throw new ForbiddenException();
      }
    }
    user.credentialManager()
        .moveStoredCredentialTo( credentialId, newPreviousCredentialId );
  }


  private static class SendEmailParams
  {

    final String redirectUri;

    final String clientId;

    final int    lifespan;


    public SendEmailParams( String redirectUri, String clientId, Integer lifespan )
    {

      this.redirectUri = redirectUri;
      this.clientId    = clientId;
      this.lifespan    = lifespan;
    }





  }





}
