package org.acme.core.model;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.acme.core.utils.JsonSerialization;
import org.acme.core.utils.PasswordCredentialData;
import org.acme.core.utils.PasswordSecretData;

public class PasswordCredentialModel extends CredentialModel {

    public static final String TYPE = "password";
    public static final String PASSWORD_HISTORY = "password-history";

    private final PasswordCredentialData credentialData;
    private final PasswordSecretData secretData;

    private PasswordCredentialModel(PasswordCredentialData credentialData, PasswordSecretData secretData) {
        this.credentialData = credentialData;
        this.secretData = secretData;
    }

    public static PasswordCredentialModel createFromValues(PasswordCredentialData credentialData, PasswordSecretData secretData) {
        return new PasswordCredentialModel(credentialData, secretData);
    }

    public static PasswordCredentialModel createFromValues(String algorithm, byte[] salt, int hashIterations, String encodedPassword){
        return createFromValues(algorithm, salt, hashIterations, null, encodedPassword);
    }

    public static PasswordCredentialModel createFromValues(String algorithm, byte[] salt, int hashIterations, Map<String, List<String>> additionalParameters, String encodedPassword)
    {
        PasswordCredentialData credentialData = new PasswordCredentialData(hashIterations, algorithm, additionalParameters);
        PasswordSecretData secretData = new PasswordSecretData(encodedPassword, salt);

        PasswordCredentialModel passwordCredentialModel = new PasswordCredentialModel(credentialData, secretData);

        try {
            passwordCredentialModel.setCredentialData(JsonSerialization.writeValueAsString(credentialData));
            passwordCredentialModel.setSecretData(JsonSerialization.writeValueAsString(secretData));
            passwordCredentialModel.setType(TYPE);
            return passwordCredentialModel;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static PasswordCredentialModel createFromCredentialModel(CredentialModel credentialModel) {
        try {
            PasswordCredentialData credentialData = JsonSerialization.readValue(credentialModel.getCredentialData(),
                PasswordCredentialData.class);
            PasswordSecretData secretData = JsonSerialization.readValue(credentialModel.getSecretData(), PasswordSecretData.class);
            PasswordCredentialModel passwordCredentialModel = new PasswordCredentialModel(credentialData, secretData);
            passwordCredentialModel.setCreatedDate(credentialModel.getCreatedDate());
            passwordCredentialModel.setCredentialData(credentialModel.getCredentialData());
            passwordCredentialModel.setId(credentialModel.getId());
            passwordCredentialModel.setSecretData(credentialModel.getSecretData());
            passwordCredentialModel.setType(credentialModel.getType());

            return passwordCredentialModel;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public PasswordCredentialData getPasswordCredentialData() {
        return credentialData;
    }

    public PasswordSecretData getPasswordSecretData() {
        return secretData;
    }


}
