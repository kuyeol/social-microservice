package org.account.rest;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import org.account.exception.ValidationException;
import org.account.model.CredentialModel;
import org.account.represetion.identitymanagement.CredentialRepresentation;
import org.account.represetion.identitymanagement.ErrorRepresentation;
import org.account.represetion.identitymanagement.ErrorResponse;
import org.account.represetion.identitymanagement.UserRepresentation;
import org.account.represetion.identitymanagement.util.ModelToRepresentation;
import org.account.service.Session;
import org.account.service.UserModel;
import org.account.service.userprofile.UserProfile;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.NoCache;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@ApplicationScoped
public class UserResource {

  private static final Logger logger = Logger.getLogger(UserResource.class);


  private final UserModel user;
  protected final Session session;
  protected final HttpHeaders headers;

  public UserResource(Session session, UserModel user) {
    this.session = session;
    this.user = user;
    this.headers = session.getContext().getRequestHeaders();
  }

  public static Response validateUserProfile(UserProfile profile) {

    try {
      profile.validate();
    } catch (ValidationException pve) {
      List<ErrorRepresentation> errors = new ArrayList<>();
      for (ValidationException.Error error : pve.getErrors()) {
        // some messages are managed directly as before
        switch (error.getMessage()) {
          //case Messages.MISSING_USERNAME -> throw ErrorResponse.error( "User name is missing", Status.BAD_REQUEST );
          //case Messages.USERNAME_EXISTS -> throw ErrorResponse.exists( "User exists with same username" );
          //case Messages.EMAIL_EXISTS -> throw ErrorResponse.exists( "User exists with same email" );
          case "ERROR" -> throw ErrorResponse.error("dddd", Status.BAD_REQUEST);
        }
        errors.add(new ErrorRepresentation(error.getAttribute(), error.getMessage(), error.getMessageParameters()));
      }

      throw ErrorResponse.errors(errors, Status.BAD_REQUEST);
    }

    return null;
  }


  public static void updateUserFromRep(UserProfile profile, UserModel user, UserRepresentation rep, boolean isUpdateExistingUser) {

    boolean removeMissingRequiredActions = isUpdateExistingUser;

    if (rep.isEnabled() != null) {
      user.setEnabled(rep.isEnabled());
    }
    if (rep.isEmailVerified() != null) {
      user.setEmailVerified(rep.isEmailVerified());
    }
    if (rep.getCreatedTimestamp() != null && !isUpdateExistingUser) {
      user.setCreatedTimestamp(rep.getCreatedTimestamp());
    }

    List<String> reqActions = rep.getRequiredActions();

    if (reqActions != null) {
      System.out.println("List< String > reqActions = rep.getRequiredActions();");
    }

    List<CredentialRepresentation> credentials = rep.getCredentials();
    if (credentials != null) {
      for (CredentialRepresentation credential : credentials) {
        if (CredentialRepresentation.PASSWORD.equals(credential.getType()) && credential.isTemporary() != null && credential.isTemporary()) {
          user.addRequiredAction(UserModel.RequiredAction.UPDATE_PASSWORD);
        }
      }
    }
  }


  @Path("logout")
  @POST
  @Operation(summary = "Remove all user sessions associated with the user Also send notification to all clients that have an admin URL to invalidate the sessions for the particular user.")
  @APIResponse(responseCode = "204", description = "No Content")
  public void logout() {
//
//    session.sessions()
//           .getUserSessionsStream( user )
//           .collect( Collectors.toList() );

  }


  /**
   * Delete the user
   */


//  /**
//   * Set up a new password for the user.
//   *
//   * @param cred The representation must contain a rawPassword with the plain-text password
//   */
//  @Path("reset-password")
//  @PUT
//  @Consumes(MediaType.APPLICATION_JSON)
//  @Operation(summary = "Set up a new password for the user.")
//  public void resetPassword(
//      @Parameter(description = "The representation must contain a rawPassword with the plain-text password") CredentialRepresentation cred )
//  {
//
//
//    if ( cred == null || cred.getValue() == null ) {
//      throw new BadRequestException( "No password provided" );
//    }
//    if ( Validation.isBlank( cred.getValue() ) ) {
//      throw new BadRequestException( "Empty password not allowed" );
//    }
//
//    try {
//      user.credentialManager()
//          .updateCredential( UserCredentialModel.password( cred.getValue(), false ) );
//    } catch ( IllegalStateException ise ) {
//      throw new BadRequestException( "Resetting to N old passwords is not allowed." );
//    } catch ( ReadOnlyException mre ) {
//      throw new BadRequestException( "Can't reset password as account is read only" );
//    } catch ( PasswordPolicyNotMetException e ) {
//      logger.warn( "Password policy not met for user " + e.getUsername(), e );
//      Properties messages = new Properties();
//      messages.setProperty( "admin exception", e.getMessage() );
//      throw new ErrorResponseException( e.getMessage(), MessageFormat.format( messages.getProperty( e.getMessage(), e.getMessage() ), e.getParameters() ),
//                                        Status.BAD_REQUEST );
//    } catch ( ModelIllegalStateException e ) {
//      logger.error( e.getMessage(), e );
//      throw ErrorResponse.error( e.getMessage(), Status.INTERNAL_SERVER_ERROR );
//    } catch ( ModelException e ) {
//      logger.warn( "Could not update user password.", e );
//      Properties messages = new Properties();
//      messages.setProperty( "admin exception", e.getMessage() );
//      throw new ErrorResponseException( e.getMessage(), MessageFormat.format( messages.getProperty( e.getMessage(), e.getMessage() ), e.getParameters() ),
//                                        Status.BAD_REQUEST );
//    }
//    if ( cred.isTemporary() != null && cred.isTemporary() ) {
//      user.addRequiredAction( UserModel.RequiredAction.UPDATE_PASSWORD );
//    } else {
//      // Remove a potentially existing UPDATE_PASSWORD action when explicitly assigning a non-temporary password.
//      user.removeRequiredAction( UserModel.RequiredAction.UPDATE_PASSWORD );
//    }
//
//  }
  @GET
  @Path("credentials")
  @NoCache
  @Produces(MediaType.APPLICATION_JSON)
  @Operation()
  public Stream<CredentialRepresentation> credentials() {


    return user.credentialManager().getStoredCredentialsStream().map(ModelToRepresentation::toRepresentation).peek(credentialRepresentation -> credentialRepresentation.setSecretData(null));
  }


  @Path("credentials/{credentialId}")
  @DELETE
  @NoCache
  @Operation(summary = "Remove a credential for a user")
  public void removeCredential(final @PathParam("credentialId") String credentialId) {

    CredentialModel credential = user.credentialManager().getStoredCredentialById(credentialId);
    if (credential == null) {
      // we do this to make sure somebody can't phish ids
      if (credentialId == null) {
        throw new NotFoundException("Credential not found");
      } else {
        throw new ForbiddenException();
      }
    }
    user.credentialManager().removeStoredCredentialById(credentialId);

  }


  private static class SendEmailParams {

    final String redirectUri;

    final String clientId;

    final int lifespan;


    public SendEmailParams(String redirectUri, String clientId, Integer lifespan) {

      this.redirectUri = redirectUri;
      this.clientId = clientId;
      this.lifespan = lifespan;
    }


  }


}
