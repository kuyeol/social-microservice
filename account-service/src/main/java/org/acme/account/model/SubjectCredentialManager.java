/*
 * Copyright 2022. Red Hat, Inc. and/or its affiliates
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

package org.acme.account.model;



import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.acme.account.util.CredentialInput;


/**
 * Validates and manages the credentials of a known entity (for example, a user).
 */
public interface SubjectCredentialManager {

    /**
     * Validate a list of credentials.
     *
     * @return <code>true</code> if inputs are valid
     */
    boolean isValid(List< CredentialInput > inputs);

    /**
     * Validate a list of credentials.
     *
     * @return <code>true</code> if inputs are valid
     */
    default boolean isValid(CredentialInput... inputs) {
        return isValid(Arrays.asList(inputs));
    }


    boolean updateCredential(CredentialInput input);

    void updateStoredCredential(CredentialModel cred);

    CredentialModel createStoredCredential(CredentialModel cred);


    boolean removeStoredCredentialById(String id);

    /**
     * Read a stored credential.
     */
    CredentialModel getStoredCredentialById(String id);


    Stream<CredentialModel> getStoredCredentialsStream();


    Stream<CredentialModel> getStoredCredentialsByTypeStream(String type);

    CredentialModel getStoredCredentialByNameAndType(String name, String type);


    boolean moveStoredCredentialTo(String id, String newPreviousCredentialId);


    void updateCredentialLabel(String credentialId, String credentialLabel);


    void disableCredentialType(String credentialType);


    Stream<String> getDisableableCredentialTypesStream();


    boolean isConfiguredFor(String type);




}
