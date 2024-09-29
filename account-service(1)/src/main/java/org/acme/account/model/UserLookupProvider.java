
package org.acme.account.model;


import org.acme.account.util.CredentialInput;


/**
 * This is an optional capability interface that is intended to be implemented by any
 * <code>UserStorageProvider</code> that supports basic user querying. You must
 * implement this interface if you want to be able to log in to keycloak using users from your storage.
 * <p/>
 * Note that all methods in this interface should limit search only to data available within the storage that is
 * represented by this provider. They should not lookup other storage providers for additional information.
 * Optional capability interface implemented by UserStorageProviders.
 *
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public interface UserLookupProvider {


    UserModel getUserById(String id);


    UserModel getUserByUsername( String username);

    default CredentialValidationOutput getUserByCredential( CredentialInput input) {
        return null;
    }


    UserModel getUserByEmail( String email);
}
