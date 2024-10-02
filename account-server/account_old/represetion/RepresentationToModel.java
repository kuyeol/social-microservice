
package org.account.represetion;


import org.account.credential.PasswordCredentialData;
import org.account.credential.PasswordCredentialModel;
import org.account.exception.ModelException;
import org.account.exception.PasswordPolicyNotMetException;
import org.account.model.CredentialModel;
import org.account.model.UserCredentialModel;
import org.account.represetion.identitymanagement.CredentialRepresentation;
import org.account.represetion.identitymanagement.UserRepresentation;
import org.account.service.UserModel;
import org.account.util.json.JsonSerialization;
import org.jboss.logging.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class RepresentationToModel {

    private static Logger logger = Logger.getLogger(RepresentationToModel.class);
    public static final String OIDC = "openid-connect";






    private static void convertDeprecatedCredentialsFormat( UserRepresentation user) {
        if (user.getCredentials() != null) {
            for ( CredentialRepresentation cred : user.getCredentials()) {
                try {
                    if ((cred.getCredentialData() == null || cred.getSecretData() == null) && cred.getValue() == null) {
                        logger.warnf("Using deprecated 'credentials' format in JSON representation for user '%s'. It will be removed in future versions", user.getUsername());

                        if ( PasswordCredentialModel.TYPE.equals( cred.getType()) || PasswordCredentialModel.PASSWORD_HISTORY.equals( cred.getType())) {
                            PasswordCredentialData credentialData = new PasswordCredentialData( cred.hashCode() , cred.getType());
                            cred.setCredentialData( JsonSerialization.writeValueAsString( credentialData));
                            // Created this manually to avoid conversion from Base64 and back

                            cred.setPriority(10);
                        }
                    }
                } catch (IOException ioe) {
                    throw new RuntimeException(ioe);
                }
            }
        }
    }




    // Basic realm stuff

    // Roles

    //public static UserModel createUser(KeycloakSession session,  UserRepresentation userRep) {
    //    return session.getProvider( DatastoreProvider.class).getExportImportManager().createUser( userRep);
    //}
    //




    public static void createCredentials(UserRepresentation userRep, UserModel user, boolean adminRequest) {
        convertDeprecatedCredentialsFormat(userRep);
        if (userRep.getCredentials() != null) {
            for (CredentialRepresentation cred : userRep.getCredentials()) {
                if (cred.getId() != null && user.credentialManager().getStoredCredentialById(cred.getId()) != null) {
                    continue;
                }
                if (cred.getValue() != null && !cred.getValue().isEmpty()) {

                    try {
                        user.credentialManager().updateCredential( UserCredentialModel.password( cred.getValue(), false));
                    } catch ( ModelException ex) {
                        PasswordPolicyNotMetException passwordPolicyNotMetException = new PasswordPolicyNotMetException( ex.getMessage(), user.getUsername(), ex);
                        passwordPolicyNotMetException.setParameters(ex.getParameters());
                        throw passwordPolicyNotMetException;
                    } finally {
                        System.out.println("finally");
                    }
                } else {
                    System.out.println("  // TODO: not needed for new store? -> no, will be removed without replacement@Deprecated\n" +
                                       "    CredentialModel createCredentialThroughProvider(CredentialModel model)");
                }
            }
        }
    }

    public static CredentialModel toModel(CredentialRepresentation cred) {
        CredentialModel model = new CredentialModel();
        model.setCreatedDate(cred.getCreatedDate());
        model.setType(cred.getType());
        model.setUserLabel(cred.getUserLabel());
        model.setSecretData(cred.getSecretData());
        model.setCredentialData(cred.getCredentialData());
        model.setId(cred.getId());
        return model;
    }

    // Role mappings


    public static Map<String, String> removeEmptyString(Map<String, String> map) {
        if (map == null) {
            return null;
        }

        Map<String, String> m = new HashMap<>(map);
        for ( Iterator< Entry<String, String>> itr = m.entrySet().iterator() ; itr.hasNext(); ) {
            Entry<String, String> e = itr.next();
            if (e.getValue() == null || e.getValue().equals("")) {
                itr.remove();
            }
        }
        return m;
    }

}
