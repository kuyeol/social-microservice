
package org.acme.account.rest;


import java.net.URI;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.UriBuilder;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.NoCache;

import org.acme.account.admin.AdminPermissionEvaluator;
import org.acme.account.model.KeycloakSession;
import org.acme.account.model.UserLoginFailureModel;
import org.acme.account.model.UserModel;
import org.acme.account.represetion.identitymanagement.CredentialRepresentation;
import org.acme.account.represetion.identitymanagement.UserRepresentation;
import org.acme.account.represetion.identitymanagement.util.BruteForceProtector;
import org.acme.account.userprofile.UserProfile;
import org.acme.account.userprofile.UserProfileProvider;
import org.acme.account.util.RepresentationToModel;


import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static org.acme.account.userprofile.UserProfileContext.USER_API;


public class UserResource
{

  private static final Logger                   logger = Logger.getLogger( UserResource.class );

  private final        AdminPermissionEvaluator auth;

  private final        UserModel                user;

  protected final      KeycloakSession          session;


  public UserResource( KeycloakSession session, UserModel user, AdminPermissionEvaluator auth )
  {

    this.session = session;
    this.auth    = auth;
    this.user    = user;

  }


  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  @Operation(summary = "Update the user")
  public Response updateUser( final UserRepresentation rep )
  {

    auth.users()
        .requireManage( user );
    try {

      boolean wasPermanentlyLockedOut = false;
      if ( rep.isEnabled() != null && rep.isEnabled() ) {
        if ( ! user.isEnabled() || session.getProvider( BruteForceProtector.class )
                                          .isTemporarilyDisabled( session, user ) ) {
          UserLoginFailureModel failureModel = session.loginFailures()
                                                      .getUserLoginFailure(  user.getId() );
          if ( failureModel != null ) {
            session.loginFailures()
                   .removeUserLoginFailure(  user.getId() );


          }
        }
        wasPermanentlyLockedOut = session.getProvider( BruteForceProtector.class )
                                         .isPermanentlyLockedOut( session, user );
      }

      Map< String, List< String > > attributes = new HashMap<>( rep.getRawAttributes() );

      if ( rep.getAttributes() == null ) {
        // include existing attributes in case no attributes are set so that validation takes into account the existing
        // attributes associated with the user
        for ( Entry< String, List< String > > entry : user.getAttributes()
                                                          .entrySet() ) {
          attributes.putIfAbsent( entry.getKey(), entry.getValue() );
        }
      }

      UserProfile profile = session.getProvider( UserProfileProvider.class )
                                   .create( USER_API, attributes, user );

      Response response = validateUserProfile( profile, session, auth.adminAuth() );
      if ( response != null ) {
        return response;
      }
      profile.update( rep.getAttributes() != null );
      updateUserFromRep( profile, user, rep, session, true );
      RepresentationToModel.createCredentials( rep, session,  user, true );

      // we need to do it here as the attributes would be overwritten by what is in the rep
      if ( wasPermanentlyLockedOut ) {
        session.getProvider( BruteForceProtector.class )
               .cleanUpPermanentLockout( session, realm, user );
      }

      adminEvent.operation( OperationType.UPDATE )
                .resourcePath( session.getContext()
                                      .getUri() )
                .representation( rep )
                .success();

      if ( session.getTransactionManager()
                  .isActive() ) {
        session.getTransactionManager()
               .commit();
      }
      return Response.noContent()
                     .build();
    } catch ( ModelDuplicateException e ) {
      session.getTransactionManager()
             .setRollbackOnly();
      throw ErrorResponse.exists( "User exists with same username or email" );
    } catch ( ReadOnlyException re ) {
      session.getTransactionManager()
             .setRollbackOnly();
      throw ErrorResponse.error( "User is read only!", Status.BAD_REQUEST );
    } catch ( PasswordPolicyNotMetException e ) {
      logger.warn( "Password policy not met for user " + e.getUsername(), e );
      session.getTransactionManager()
             .setRollbackOnly();
      Properties messages = AdminRoot.getMessages( session, realm, auth.adminAuth()
                                                                       .getToken()
                                                                       .getLocale() );
      throw new ErrorResponseException( e.getMessage(), MessageFormat.format( messages.getProperty( e.getMessage(), e.getMessage() ), e.getParameters() ),
                                        Status.BAD_REQUEST );
    } catch ( ModelIllegalStateException e ) {
      logger.error( e.getMessage(), e );
      throw ErrorResponse.error( e.getMessage(), Status.INTERNAL_SERVER_ERROR );
    } catch ( ModelException me ) {
      logger.warn( "Could not update user!", me );
      session.getTransactionManager()
             .setRollbackOnly();
      throw ErrorResponse.error( "Could not update user!", Status.BAD_REQUEST );
    } catch ( ForbiddenException | ErrorResponseException e ) {
      session.getTransactionManager()
             .setRollbackOnly();
      throw e;
    } catch ( Exception me ) { // JPA
      session.getTransactionManager()
             .setRollbackOnly();
      logger.warn( "Could not update user!", me );// may be committed by JTA which can't
      throw ErrorResponse.error( "Could not update user!", Status.BAD_REQUEST );
    }
  }


  public static Response validateUserProfile( UserProfile profile, KeycloakSession session, AdminAuth adminAuth )
  {

    try {
      profile.validate();
    } catch ( ValidationException pve ) {
      List< ErrorRepresentation > errors = new ArrayList<>();
      for ( ValidationException.Error error : pve.getErrors() ) {
        // some messages are managed directly as before
        switch ( error.getMessage() ) {
          case Messages.MISSING_USERNAME -> throw ErrorResponse.error( "User name is missing", Status.BAD_REQUEST );
          case Messages.USERNAME_EXISTS -> throw ErrorResponse.exists( "User exists with same username" );
          case Messages.EMAIL_EXISTS -> throw ErrorResponse.exists( "User exists with same email" );
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

    if ( rep.getFederationLink() != null ) {
      user.setFederationLink( rep.getFederationLink() );
    }

    List< String > reqActions = rep.getRequiredActions();

    if ( reqActions != null ) {
      session.getKeycloakSessionFactory()
             .getProviderFactoriesStream( RequiredActionProvider.class )
             .map( ProviderFactory::getId )
             .distinct()
             .sorted()
             .forEach( action ->
                         {
                           if ( reqActions.contains( action ) ) {
                             user.addRequiredAction( action );
                           } else {
                             if ( removeMissingRequiredActions ) {
                               user.removeRequiredAction( action );
                             }
                           }
                         } );
    }

    List< CredentialRepresentation > credentials = rep.getCredentials();
    if ( credentials != null ) {
      for ( CredentialRepresentation credential : credentials ) {
        if ( CredentialRepresentation.PASSWORD.equals( credential.getType() ) && credential.isTemporary() != null
             && credential.isTemporary() ) {
          user.addRequiredAction( UserModel.RequiredAction.UPDATE_PASSWORD );
        }
      }
    }
  }


  /**
   * Get representation of the user
   *
   * @return
   */
  @GET
  @NoCache
  @Produces(MediaType.APPLICATION_JSON)
  @Tag(name = KeycloakOpenAPI.Admin.Tags.USERS)
  @Operation(summary = "Get representation of the user")
  public UserRepresentation getUser(
      @Parameter(description = "Indicates if the user profile metadata should be added to the response") @QueryParam("userProfileMetadata") boolean userProfileMetadata
  )
  {

    auth.users()
        .requireView( user );

    UserProfileProvider provider = session.getProvider( UserProfileProvider.class );
    UserProfile         profile  = provider.create( USER_API, user );
    UserRepresentation  rep      = profile.toRepresentation();

    if ( realm.isIdentityFederationEnabled() ) {
      List< FederatedIdentityRepresentation > reps = getFederatedIdentities( user ).collect( Collectors.toList() );
      rep.setFederatedIdentities( reps );
    }

    if ( session.getProvider( BruteForceProtector.class )
                .isTemporarilyDisabled( session, realm, user ) ) {
      rep.setEnabled( false );
    }
    rep.setAccess( auth.users()
                       .getAccess( user ) );

    if ( ! userProfileMetadata ) {
      rep.setUserProfileMetadata( null );
    }

    return rep;
  }


  /**
   * Impersonate the user
   *
   * @return
   */
  @Path("impersonation")
  @POST
  @NoCache
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(summary = "Impersonate the user")
  public Map< String, Object > impersonate()
  {

    ProfileHelper.requireFeature( Profile.Feature.IMPERSONATION );

    auth.users()
        .requireImpersonate( user );

    if ( ! user.isEnabled() ) {
      throw ErrorResponse.error( "User is disabled", Status.BAD_REQUEST );
    }
    if ( user.getServiceAccountClientLink() != null ) {
      throw ErrorResponse.error( "Service accounts cannot be impersonated", Status.BAD_REQUEST );
    }

    RealmModel authenticatedRealm = auth.adminAuth()
                                        .getRealm();
    // if same realm logout before impersonation
    boolean sameRealm = false;
    String sessionState = auth.adminAuth()
                              .getToken()
                              .getSessionState();
    if ( authenticatedRealm.getId()
                           .equals( realm.getId() ) && sessionState != null ) {
      sameRealm = true;
      UserSessionModel userSession = session.sessions()
                                            .getUserSession( authenticatedRealm, sessionState );
      AuthenticationManager.expireIdentityCookie( session );
      AuthenticationManager.expireRememberMeCookie( session );
      AuthenticationManager.expireAuthSessionCookie( session );
      AuthenticationManager.backchannelLogout( session, authenticatedRealm, userSession, session.getContext()
                                                                                                .getUri(), clientConnection, headers, true );
    }
    EventBuilder event = new EventBuilder( realm, session, clientConnection );

    UserSessionModel userSession = new UserSessionManager( session ).createUserSession( realm, user, user.getUsername(), clientConnection.getRemoteAddr(),
                                                                                        "impersonate", false, null, null );

    UserModel adminUser = auth.adminAuth()
                              .getUser();
    String impersonatorId = adminUser.getId();
    String impersonator   = adminUser.getUsername();
    userSession.setNote( IMPERSONATOR_ID.toString(), impersonatorId );
    userSession.setNote( IMPERSONATOR_USERNAME.toString(), impersonator );

    AuthenticationManager.createLoginCookie( session, realm, userSession.getUser(), userSession, session.getContext()
                                                                                                        .getUri(), clientConnection );
    URI redirect = Urls.accountBase( session.getContext()
                                            .getUri()
                                            .getBaseUri() )
                       .build( realm.getName() );
    Map< String, Object > result = new HashMap<>();
    result.put( "sameRealm", sameRealm );
    result.put( "redirect", redirect.toString() );
    event.event( EventType.IMPERSONATE )
         .session( userSession )
         .user( user )
         .detail( Details.IMPERSONATOR_REALM, authenticatedRealm.getName() )
         .detail( Details.IMPERSONATOR, impersonator )
         .success();

    return result;
  }


  /**
   * Get sessions associated with the user
   *
   * @return
   */
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
                  .getUserSessionsStream( realm, user )
                  .map( ModelToRepresentation::toRepresentation );
  }


  /**
   * Get offline sessions associated with the user and client
   *
   * @return
   */
  @Path("offline-sessions/{clientUuid}")
  @GET
  @NoCache
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(summary = "Get offline sessions associated with the user and client")
  public Stream< UserSessionRepresentation > getOfflineSessions( final @PathParam("clientUuid") String clientUuid )
  {

    auth.users()
        .requireView( user );
    ClientModel client = realm.getClientById( clientUuid );
    if ( client == null ) {
      throw new NotFoundException( "Client not found" );
    }
    return new UserSessionManager( session ).findOfflineSessionsStream( realm, user )
                                            .map( session -> toUserSessionRepresentation( session, clientUuid ) )
                                            .filter( Objects::nonNull );
  }


  /**
   * Remove all user sessions associated with the user
   * <p>
   * Also send notification to all clients that have an admin URL to invalidate the sessions for the particular user.
   */
  @Path("logout")
  @POST
  @Operation(summary = "Remove all user sessions associated with the user Also send notification to all clients that have an admin URL to invalidate the sessions for the particular user.")
  @APIResponse(responseCode = "204", description = "No Content")
  public void logout()
  {

    session.sessions()
           .getUserSessionsStream( realm, user )
           .collect( Collectors.toList() ) // collect to avoid concurrent modification as backchannelLogout removes the user sessions.
           .forEach( userSession -> AuthenticationManager.backchannelLogout( session, realm, userSession,
                                                                             session.getContext()
                                                                                    .getUri(), clientConnection, headers, true ) );
    adminEvent.operation( OperationType.ACTION )
              .resourcePath( session.getContext()
                                    .getUri() )
              .success();
  }


  /**
   * Delete the user
   */
  @DELETE
  @NoCache
  @Tag(name = KeycloakOpenAPI.Admin.Tags.USERS)
  @Operation(summary = "Delete the user")
  public Response deleteUser()
  {

    auth.users()
        .requireManage( user );

    boolean removed = new UserManager( session ).removeUser( realm, user );
    if ( removed ) {
      adminEvent.operation( OperationType.DELETE )
                .resourcePath( session.getContext()
                                      .getUri() )
                .success();
      return Response.noContent()
                     .build();
    } else {
      throw ErrorResponse.error( "User couldn't be deleted", Status.BAD_REQUEST );
    }
  }


  @Path("role-mappings")
  public RoleMapperResource getRoleMappings()
  {

    AdminPermissionEvaluator.RequirePermissionCheck manageCheck = () -> auth.users()
                                                                            .requireMapRoles( user );
    AdminPermissionEvaluator.RequirePermissionCheck viewCheck = () -> auth.users()
                                                                          .requireView( user );
    return new RoleMapperResource( session, auth, user, adminEvent, manageCheck, viewCheck );
  }


  /**
   * Disable all credentials for a user of a specific type
   *
   * @param credentialTypes
   */
  @Path("disable-credential-types")
  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  @Tag(name = KeycloakOpenAPI.Admin.Tags.USERS)
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
  @Tag(name = KeycloakOpenAPI.Admin.Tags.USERS)
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
      Properties messages = AdminRoot.getMessages( session, realm, auth.adminAuth()
                                                                       .getToken()
                                                                       .getLocale() );
      throw new ErrorResponseException( e.getMessage(), MessageFormat.format( messages.getProperty( e.getMessage(), e.getMessage() ), e.getParameters() ),
                                        Status.BAD_REQUEST );
    } catch ( ModelIllegalStateException e ) {
      logger.error( e.getMessage(), e );
      throw ErrorResponse.error( e.getMessage(), Status.INTERNAL_SERVER_ERROR );
    } catch ( ModelException e ) {
      logger.warn( "Could not update user password.", e );
      Properties messages = AdminRoot.getMessages( session, realm, auth.adminAuth()
                                                                       .getToken()
                                                                       .getLocale() );
      throw new ErrorResponseException( e.getMessage(), MessageFormat.format( messages.getProperty( e.getMessage(), e.getMessage() ), e.getParameters() ),
                                        Status.BAD_REQUEST );
    }
    if ( cred.isTemporary() != null && cred.isTemporary() ) {
      user.addRequiredAction( UserModel.RequiredAction.UPDATE_PASSWORD );
    } else {
      // Remove a potentially existing UPDATE_PASSWORD action when explicitly assigning a non-temporary password.
      user.removeRequiredAction( UserModel.RequiredAction.UPDATE_PASSWORD );
    }

    adminEvent.operation( OperationType.ACTION )
              .resourcePath( session.getContext()
                                    .getUri() )
              .success();
  }


  @GET
  @Path("credentials")
  @NoCache
  @Produces(MediaType.APPLICATION_JSON)
  @Tag(name = KeycloakOpenAPI.Admin.Tags.USERS)
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


  /**
   * Return credential types, which are provided by the user storage where user is stored. Returned values can contain for example "password", "otp" etc.
   * This will always return empty list for "local" users, which are not backed by any user storage
   *
   * @return
   */
  @GET
  @Path("configured-user-storage-credential-types")
  @NoCache
  @Produces(MediaType.APPLICATION_JSON)
  @Tag(name = KeycloakOpenAPI.Admin.Tags.USERS)
  @Operation(summary = "Return credential types, which are provided by the user storage where user is stored.", description = "Returned values can contain for example \"password\", \"otp\" etc. This will always return empty list for \"local\" users, which are not backed by any user storage")
  public Stream< String > getConfiguredUserStorageCredentialTypes()
  {
    // changed to "requireView" as per issue #20783
    auth.users()
        .requireView( user );
    return user.credentialManager()
               .getConfiguredUserStorageCredentialTypesStream();
  }


  /**
   * Remove a credential for a user
   */
  @Path("credentials/{credentialId}")
  @DELETE
  @NoCache
  @Tag(name = KeycloakOpenAPI.Admin.Tags.USERS)
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
    adminEvent.operation( OperationType.ACTION )
              .resourcePath( session.getContext()
                                    .getUri() )
              .success();
  }


  /**
   * Update a credential label for a user
   */
  @PUT
  @Consumes(MediaType.TEXT_PLAIN)
  @Path("credentials/{credentialId}/userLabel")
  @Tag(name = KeycloakOpenAPI.Admin.Tags.USERS)
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


  /**
   * Move a credential to a first position in the credentials list of the user
   *
   * @param credentialId The credential to move
   */
  @Path("credentials/{credentialId}/moveToFirst")
  @POST
  @Tag(name = KeycloakOpenAPI.Admin.Tags.USERS)
  @Operation(summary = "Move a credential to a first position in the credentials list of the user")
  @APIResponse(responseCode = "204", description = "No Content")
  public void moveCredentialToFirst( final @Parameter(description = "The credential to move") @PathParam("credentialId") String credentialId )
  {

    moveCredentialAfter( credentialId, null );
  }


  /**
   * Move a credential to a position behind another credential
   *
   * @param credentialId            The credential to move
   * @param newPreviousCredentialId The credential that will be the previous element in the list. If set to null, the moved credential will be the first element in the list.
   */
  @Path("credentials/{credentialId}/moveAfter/{newPreviousCredentialId}")
  @POST
  @Tag(name = KeycloakOpenAPI.Admin.Tags.USERS)
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


  /**
   * Send an email to the user with a link they can click to reset their password.
   * The redirectUri and clientId parameters are optional. The default for the
   * redirect is the account client.
   * <p>
   * This endpoint has been deprecated.  Please use the execute-actions-email passing a list with
   * UPDATE_PASSWORD within it.
   *
   * @param redirectUri redirect uri
   * @param clientId    client id
   * @return
   */
  @Deprecated
  @Path("reset-password-email")
  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  @Tag(name = KeycloakOpenAPI.Admin.Tags.USERS)
  @Operation(
      summary = "Send an email to the user with a link they can click to reset their password.",
      description = "The redirectUri and clientId parameters are optional. The default for the redirect is the account client. This endpoint has been deprecated.  Please use the execute-actions-email passing a list with UPDATE_PASSWORD within it.",
      deprecated = true)
  public Response resetPasswordEmail( @Parameter(description = "redirect uri") @QueryParam(OIDCLoginProtocol.REDIRECT_URI_PARAM) String redirectUri,
      @Parameter(description = "client id") @QueryParam(OIDCLoginProtocol.CLIENT_ID_PARAM) String clientId )
  {

    List< String > actions = new LinkedList<>();
    actions.add( UserModel.RequiredAction.UPDATE_PASSWORD.name() );
    return executeActionsEmail( redirectUri, clientId, null, actions );
  }


  /**
   * Send an email to the user with a link they can click to execute particular actions.
   * <p>
   * An email contains a link the user can click to perform a set of required actions.
   * The redirectUri and clientId parameters are optional. If no redirect is given, then there will
   * be no link back to click after actions have completed.  Redirect uri must be a valid uri for the
   * particular clientId.
   *
   * @param redirectUri Redirect uri
   * @param clientId    Client id
   * @param lifespan    Number of seconds after which the generated token expires
   * @param actions     Required actions the user needs to complete
   * @return
   */
  @Path("execute-actions-email")
  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  @Tag(name = KeycloakOpenAPI.Admin.Tags.USERS)
  @Operation(
      summary = "Send an email to the user with a link they can click to execute particular actions.",
      description = "An email contains a link the user can click to perform a set of required actions. The redirectUri and clientId parameters are optional. If no redirect is given, then there will be no link back to click after actions have completed. Redirect uri must be a valid uri for the particular clientId."
  )
  public Response executeActionsEmail( @Parameter(description = "Redirect uri") @QueryParam(OIDCLoginProtocol.REDIRECT_URI_PARAM) String redirectUri,
      @Parameter(description = "Client id") @QueryParam(OIDCLoginProtocol.CLIENT_ID_PARAM) String clientId,
      @Parameter(description = "Number of seconds after which the generated token expires") @QueryParam("lifespan") Integer lifespan,
      @Parameter(description = "Required actions the user needs to complete") List< String > actions )
  {

    auth.users()
        .requireManage( user );

    SendEmailParams result = verifySendEmailParams( redirectUri, clientId, lifespan );

    if ( CollectionUtil.isNotEmpty( actions ) && ! RequiredActionsValidator.validRequiredActions( session, actions ) ) {
      throw ErrorResponse.error( "Provided invalid required actions", Status.BAD_REQUEST );
    }

    int expiration = Time.currentTime() + result.lifespan;
    ExecuteActionsActionToken token = new ExecuteActionsActionToken( user.getId(), user.getEmail(), expiration, actions, result.redirectUri,
                                                                     result.clientId );

    try {
      UriBuilder builder = LoginActionsService.actionTokenProcessor( session.getContext()
                                                                            .getUri() );
      builder.queryParam( "key", token.serialize( session, realm, session.getContext()
                                                                         .getUri() ) );

      String link = builder.build( realm.getName() )
                           .toString();

      this.session.getProvider( EmailTemplateProvider.class )
                  .setAttribute( Constants.TEMPLATE_ATTR_REQUIRED_ACTIONS, token.getRequiredActions() )
                  .setRealm( realm )
                  .setUser( user )
                  .sendExecuteActions( link, TimeUnit.SECONDS.toMinutes( result.lifespan ) );

      //audit.user(user).detail(Details.EMAIL, user.getEmail()).detail(Details.CODE_ID, accessCode.getCodeId()).success();

      adminEvent.operation( OperationType.ACTION )
                .resourcePath( session.getContext()
                                      .getUri() )
                .success();

      return Response.noContent()
                     .build();
    } catch ( EmailException e ) {
      ServicesLogger.LOGGER.failedToSendActionsEmail( e );
      throw ErrorResponse.error( "Failed to send execute actions email: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR );
    }
  }


  /**
   * Send an email-verification email to the user
   * <p>
   * An email contains a link the user can click to verify their email address.
   * The redirectUri and clientId parameters are optional. The default for the
   * redirect is the account client.
   *
   * @param redirectUri Redirect uri
   * @param clientId    Client id
   * @param lifespan    Number of seconds after which the generated token expires
   * @return
   */
  @Path("send-verify-email")
  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  @Tag(name = KeycloakOpenAPI.Admin.Tags.USERS)
  @Operation(
      summary = "Send an email-verification email to the user An email contains a link the user can click to verify their email address.",
      description = "The redirectUri, clientId and lifespan parameters are optional. The default for the redirect is the account client. The default for the lifespan is 12 hours"
  )
  public Response sendVerifyEmail(
      @Parameter(description = "Redirect uri") @QueryParam(OIDCLoginProtocol.REDIRECT_URI_PARAM) String redirectUri,
      @Parameter(description = "Client id") @QueryParam(OIDCLoginProtocol.CLIENT_ID_PARAM) String clientId,
      @Parameter(description = "Number of seconds after which the generated token expires") @QueryParam("lifespan") Integer lifespan )
  {

    auth.users()
        .requireManage( user );

    SendEmailParams result = verifySendEmailParams( redirectUri, clientId, lifespan );

    int                    expiration = Time.currentTime() + result.lifespan;
    VerifyEmailActionToken token      = new VerifyEmailActionToken( user.getId(), expiration, null, user.getEmail(), result.clientId );
    token.setRedirectUri( result.redirectUri );

    String link = LoginActionsService.actionTokenProcessor( session.getContext()
                                                                   .getUri() )
                                     .queryParam( "key", token.serialize( session, realm, session.getContext()
                                                                                                 .getUri() ) )
                                     .build( realm.getName() )
                                     .toString();

    try {
      session
          .getProvider( EmailTemplateProvider.class )
          .setRealm( realm )
          .setUser( user )
          .sendVerifyEmail( link, TimeUnit.SECONDS.toMinutes( result.lifespan ) );
    } catch ( EmailException e ) {
      ServicesLogger.LOGGER.failedToSendEmail( e );
      throw ErrorResponse.error( "Failed to send verify email", Status.INTERNAL_SERVER_ERROR );
    }

    adminEvent.operation( OperationType.ACTION )
              .resourcePath( session.getContext()
                                    .getUri() )
              .success();

    return Response.noContent()
                   .build();
  }


  @GET
  @Path("groups")
  @NoCache
  @Produces(MediaType.APPLICATION_JSON)
  @Tag(name = KeycloakOpenAPI.Admin.Tags.USERS)
  @Operation()
  public Stream< GroupRepresentation > groupMembership( @QueryParam("search") String search,
      @QueryParam("first") Integer firstResult,
      @QueryParam("max") Integer maxResults,
      @QueryParam("briefRepresentation") @DefaultValue("true") boolean briefRepresentation )
  {

    auth.users()
        .requireView( user );
    return user.getGroupsStream( search, firstResult, maxResults )
               .map( g -> ModelToRepresentation.toRepresentation( g, ! briefRepresentation ) );
  }


  @GET
  @NoCache
  @Path("groups/count")
  @Produces(MediaType.APPLICATION_JSON)
  @Tag(name = KeycloakOpenAPI.Admin.Tags.USERS)
  @Operation()
  public Map< String, Long > getGroupMembershipCount( @QueryParam("search") String search )
  {

    auth.users()
        .requireView( user );
    Long results;

    if ( Objects.nonNull( search ) ) {
      results = user.getGroupsCountByNameContaining( search );
    } else {
      results = user.getGroupsCount();
    }
    Map< String, Long > map = new HashMap<>();
    map.put( "count", results );
    return map;
  }


  @DELETE
  @Path("groups/{groupId}")
  @NoCache
  @Tag(name = KeycloakOpenAPI.Admin.Tags.USERS)
  @Operation()
  public void removeMembership( @PathParam("groupId") String groupId )
  {

    auth.users()
        .requireManageGroupMembership( user );

    GroupModel group = session.groups()
                              .getGroupById( realm, groupId );
    if ( group == null ) {
      throw new NotFoundException( "Group not found" );
    }
    auth.groups()
        .requireManageMembership( group );

    try {
      if ( user.isMemberOf( group ) ) {
        user.leaveGroup( group );
        adminEvent.operation( OperationType.DELETE )
                  .resource( ResourceType.GROUP_MEMBERSHIP )
                  .representation( ModelToRepresentation.toRepresentation( group, true ) )
                  .resourcePath( session.getContext()
                                        .getUri() )
                  .success();
      }
    } catch ( ModelIllegalStateException e ) {
      logger.error( e.getMessage(), e );
      throw ErrorResponse.error( e.getMessage(), Status.INTERNAL_SERVER_ERROR );
    } catch ( ModelException me ) {
      Properties messages = AdminRoot.getMessages( session, realm, auth.adminAuth()
                                                                       .getToken()
                                                                       .getLocale() );
      throw new ErrorResponseException( me.getMessage(), MessageFormat.format( messages.getProperty( me.getMessage(), me.getMessage() ), me.getParameters() ),
                                        Status.BAD_REQUEST );
    }
  }


  @PUT
  @Path("groups/{groupId}")
  @NoCache
  @Tag(name = KeycloakOpenAPI.Admin.Tags.USERS)
  @Operation()
  public void joinGroup( @PathParam("groupId") String groupId )
  {

    auth.users()
        .requireManageGroupMembership( user );
    GroupModel group = session.groups()
                              .getGroupById( realm, groupId );
    if ( group == null ) {
      throw new NotFoundException( "Group not found" );
    }
    auth.groups()
        .requireManageMembership( group );

    if ( ! RoleUtils.isDirectMember( user.getGroupsStream(), group ) ) {
      user.joinGroup( group );
      adminEvent.operation( OperationType.CREATE )
                .resource( ResourceType.GROUP_MEMBERSHIP )
                .representation( ModelToRepresentation.toRepresentation( group, true ) )
                .resourcePath( session.getContext()
                                      .getUri() )
                .success();
    }
  }


  @GET
  @Path("unmanagedAttributes")
  @NoCache
  @Produces(MediaType.APPLICATION_JSON)
  @Tag(name = KeycloakOpenAPI.Admin.Tags.USERS)
  @Operation()
  public Map< String, List< String > > getUnmanagedAttributes()
  {

    auth.users()
        .requireView( user );
    UserProfileProvider provider = session.getProvider( UserProfileProvider.class );

    UserProfile profile = provider.create( USER_API, user );
    Map< String, List< String > > managedAttributes = profile.getAttributes()
                                                             .getReadable();
    Map< String, List< String > > unmanagedAttributes = profile.getAttributes()
                                                               .getUnmanagedAttributes();
    managedAttributes.entrySet()
                     .removeAll( unmanagedAttributes.entrySet() );
    Map< String, List< String > > attributes = new HashMap<>( user.getAttributes() );
    attributes.entrySet()
              .removeAll( managedAttributes.entrySet() );

    attributes.remove( UserModel.USERNAME );
    attributes.remove( UserModel.EMAIL );

    return attributes.entrySet()
                     .stream()
                     .filter( entry -> ofNullable( entry.getValue() ).orElse( emptyList() )
                                                                     .stream()
                                                                     .anyMatch( StringUtil::isNotBlank ) )
                     .collect( Collectors.toMap( Entry::getKey, Entry::getValue ) );
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
