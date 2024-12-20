

package org.account.authentication;

import jakarta.ws.rs.core.UriInfo;
import org.account.http.HttpRequest;
import org.account.model.AuthenticationExecutionModel;
import org.account.service.UserModel;


/**
 * Interface that encapsulates the current state of the current form being executed
 *
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public interface FormContext {
    /**
     * Current event builder being used
     *
     * @return
     */
  //  EventBuilder getEvent();

    /**
     * Create a refresh new EventBuilder to use within this context
     *
     * @return
     */
   // EventBuilder newEvent();

    /**
     * The current execution in the flow
     *
     * @return
     */
    AuthenticationExecutionModel getExecution();

    /**
     * Current customer attached to this flow.  It can return null if no customer has been identified yet
     *
     * @return
     */
    UserModel getUser();

    /**
     * Attach a specific customer to this flow.
     *
     * @param user
     */
    void setUser(UserModel user);

    /**
     * Current realm
     *
     * @return
     */


    /**
     * AuthenticationSessionModel attached to this flow
     *
     * @return
     */
   // AuthenticationSessionModel getAuthenticationSession();

    /**
     * Information about the IP address from the connecting HTTP client.
     *
     * @return
     */
 //   ClientConnection getConnection();

    /**
     * UriInfo of the current request
     *
     * @return
     */
    UriInfo getUriInfo();

    /**
     * Current session
     *
     * @return
     */
   // KeycloakSession getSession();

   HttpRequest getHttpRequest();

    /**
     * Get any configuration associated with the current execution
     *
     * @return
     */
  //  AuthenticatorConfigModel getAuthenticatorConfig();
}
