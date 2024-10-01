
package org.account.credential;


import org.account.model.UserCredentialModel;


public class PasswordUserCredentialModel extends UserCredentialModel {

    public PasswordUserCredentialModel(String credentialId, String type, String challengeResponse, boolean adminRequest) {
        super(credentialId, type, challengeResponse, adminRequest);
    }
}
