
package org.account.service;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public interface KeycloakTransactionManager extends KeycloakTransaction {

    enum JTAPolicy {
        /**
         * Do not interact with JTA at all
         *
         */
        NOT_SUPPORTED,
        /**
         * A new JTA Transaction will be created when Keycloak TM begin() is called.  If an existing JTA transaction
         * exists, it is suspended and resumed after the Keycloak transaction finishes.
         */
        REQUIRES_NEW,
    }

    JTAPolicy getJTAPolicy();
    void setJTAPolicy(JTAPolicy policy);
    void enlist(KeycloakTransaction transaction);
    void enlistAfterCompletion(KeycloakTransaction transaction);

    void enlistPrepare(KeycloakTransaction transaction);
}
