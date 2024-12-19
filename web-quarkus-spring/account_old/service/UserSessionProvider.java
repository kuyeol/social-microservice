

package org.account.service;

import org.account.model.UserSessionModel;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Stream;


/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public interface UserSessionProvider extends Provider {


    Session getKeycloakSession();





    UserSessionModel createUserSession(String id, UserModel user, String loginUsername, String ipAddress,
                                       String authMethod, boolean rememberMe, String brokerSessionId, String brokerUserId, UserSessionModel.SessionPersistenceState persistenceState);

    UserSessionModel getUserSession( String id);


    Stream<UserSessionModel> getUserSessionsStream( UserModel user);





    Stream<UserSessionModel> getUserSessionsStream( Integer firstResult, Integer maxResults);


    Stream<UserSessionModel> getUserSessionByBrokerUserIdStream( String brokerUserId);

    UserSessionModel getUserSessionByBrokerSessionId( String brokerSessionId);

    UserSessionModel getUserSessionWithPredicate(String id, boolean offline, Predicate<UserSessionModel> predicate);




    Map<String, Long> getActiveClientSessionStats( boolean offline);

    void removeUserSession(UserSessionModel session);
    void removeUserSessions( UserModel user);


    void removeAllExpired();


    UserSessionModel createOfflineUserSession(UserSessionModel userSession);
    UserSessionModel getOfflineUserSession( String userSessionId);

    void removeOfflineUserSession(UserSessionModel userSession);


    Stream<UserSessionModel> getOfflineUserSessionsStream(UserModel user);

    Stream<UserSessionModel> getOfflineUserSessionByBrokerUserIdStream( String brokerUserId);



    Stream<UserSessionModel> getOfflineUserSessionsStream(Integer firstResult, Integer maxResults);



    void close();


    default void migrate(String modelVersion) {
    }


}
