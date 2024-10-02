/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates
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

package org.account.model;




import org.account.credential.PasswordCredentialModel;
import org.account.credential.PasswordUserCredentialModel;
import org.account.util.CredentialInput;
import org.account.util.SecretGenerator;

import java.util.HashMap;
import java.util.Map;



/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public class UserCredentialModel implements CredentialInput
{



    public static final String SECRET = CredentialModel.SECRET;
    public static final String KERBEROS = CredentialModel.KERBEROS;
    public static final String CLIENT_CERT = CredentialModel.CLIENT_CERT;

    private String credentialId;
    private String type;
    private String challengeResponse;
    private String device;
    private String algorithm;
    private boolean adminRequest;

    // Additional context informations
    protected Map<String, Object> notes = new HashMap<>();

    public UserCredentialModel() {
    }

    public UserCredentialModel(String credentialId, String type, String challengeResponse) {
        this.credentialId = credentialId;
        this.type = type;
        this.challengeResponse = challengeResponse;
        this.adminRequest = false;
    }

    public UserCredentialModel(String credentialId, String type, String challengeResponse, boolean adminRequest) {
        this.credentialId = credentialId;
        this.type = type;
        this.challengeResponse = challengeResponse;
        this.adminRequest = adminRequest;
    }

    public static PasswordUserCredentialModel password(String password) {
        return password(password, false);
    }

    public static PasswordUserCredentialModel password(String password, boolean adminRequest) {
        // It uses PasswordUserCredentialModel for backwards compatibility. Some UserStorage providers can check for that type
        return new PasswordUserCredentialModel("", PasswordCredentialModel.TYPE, password, adminRequest);
    }









    public static UserCredentialModel secret(String password) {
        return new UserCredentialModel("", SECRET, password);
    }

    public static UserCredentialModel kerberos(String token) {
        return new UserCredentialModel("", KERBEROS, token);
    }

    public static UserCredentialModel generateSecret() {
        return new UserCredentialModel( "", SECRET, SecretGenerator.getInstance().randomString());
    }



    @Override
    public String getCredentialId() {
        return credentialId;
    }

    @Override
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getChallengeResponse() {
        return challengeResponse;
    }

    public boolean isAdminRequest() {
        return adminRequest;
    }



    /**
     * This method exists only because of the backwards compatibility. It is recommended to use {@link #getChallengeResponse()} instead
     */
    public String getValue() {
        return getChallengeResponse();
    }

    public void setValue(String value) {
        this.challengeResponse = value;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public void setNote(String key, Object value) {
        this.notes.put(key, value);
    }

    public void removeNote(String key) {
        this.notes.remove(key);
    }

    public Object getNote(String key) {
        return this.notes.get(key);
    }

}


