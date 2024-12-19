

package org.account.represetion.identitymanagement.util;

import jakarta.ws.rs.core.UriInfo;
import org.account.service.Provider;
import org.account.service.Session;
import org.account.service.UserModel;


/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public interface BruteForceProtector extends Provider {
    String DISABLED_BY_PERMANENT_LOCKOUT = "permanentLockout";

    void failedLogin(UserModel user, UriInfo uriInfo);

    void successfulLogin(UserModel user, UriInfo uriInfo);

    boolean isTemporarilyDisabled(Session session, UserModel user);

    boolean isPermanentlyLockedOut(Session session, UserModel user);


    void cleanUpPermanentLockout(Session session, UserModel user);
}
