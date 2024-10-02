
package org.acme.account.model;



/**
 * @author <a href="mailto:mposolda@redhat.com">Marek Posolda</a>
 *
 * @deprecated Recommended to use {@link UserCredentialModel} as it contains all the functionality required by this class
 */
public class PasswordUserCredentialModel extends UserCredentialModel {

    public PasswordUserCredentialModel(String credentialId, String type, String challengeResponse, boolean adminRequest) {
        super(credentialId, type, challengeResponse, adminRequest);
    }
}