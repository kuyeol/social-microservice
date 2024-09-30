/*
 * Copyright 2022 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.acme.account.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.quarkus.security.identity.IdentityProvider;
import org.jboss.logging.Logger;

import org.acme.account.exception.ModelException;
import org.acme.account.exception.PasswordPolicyNotMetException;
import org.acme.account.json.JsonSerialization;
import org.acme.account.model.CredentialModel;
import org.acme.account.model.DatastoreProvider;
import org.acme.account.model.KeycloakSession;
import org.acme.account.model.PasswordCredentialData;
import org.acme.account.model.PasswordCredentialModel;
import org.acme.account.model.RoleModel;
import org.acme.account.model.UserCredentialModel;
import org.acme.account.model.UserModel;
import org.acme.account.model.UserProvider;
import org.acme.account.represetion.identitymanagement.CredentialRepresentation;
import org.acme.account.represetion.identitymanagement.UserRepresentation;

import static java.util.Optional.ofNullable;

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




    public static void createCredentials(UserRepresentation userRep,  UserModel user, boolean adminRequest) {
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
