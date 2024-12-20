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

package org.acme.client.customer.model;


import java.util.HashMap;
import java.util.Map;
import org.acme.core.model.CredentialModel;
import org.acme.core.model.PasswordCredentialModel;
import org.acme.core.security.hash.CredentialInput;
import org.acme.core.utils.SecretGenerator;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public class CustomerCredentialModel implements CredentialInput {


    public static final String SECRET = CredentialModel.SECRET;
    public static final String KERBEROS = CredentialModel.KERBEROS;
    public static final String CLIENT_CERT = CredentialModel.CLIENT_CERT;

    private String credentialId;
    private String type;
    private String challengeResponse;
    private String device;
    private String algorithm;
    private boolean adminRequest;


    protected Map<String, Object> notes = new HashMap<>();

    public CustomerCredentialModel() {
    }

    public CustomerCredentialModel(String credentialId, String type, String challengeResponse) {
        this.credentialId = credentialId;
        this.type = type;
        this.challengeResponse = challengeResponse;
        this.adminRequest = false;
    }

    public CustomerCredentialModel(String credentialId, String type, String challengeResponse, boolean adminRequest) {
        this.credentialId = credentialId;
        this.type = type;
        this.challengeResponse = challengeResponse;
        this.adminRequest = adminRequest;
    }

    public static PasswordCustomerCredentialModel password(String password) {
        return password(password, false);
    }

    public static PasswordCustomerCredentialModel password(String password, boolean adminRequest) {
        // It uses PasswordCustomerCredentialModel for backwards compatibility. Some UserStorage providers can check for that type
        return new PasswordCustomerCredentialModel("", PasswordCredentialModel.TYPE, password, adminRequest);
    }


    public static CustomerCredentialModel secret(String password) {
        return new CustomerCredentialModel("", SECRET, password);
    }

    public static CustomerCredentialModel kerberos(String token) {
        return new CustomerCredentialModel("", KERBEROS, token);
    }

    public static CustomerCredentialModel generateSecret() {
        return new CustomerCredentialModel("", SECRET, SecretGenerator.getInstance().randomString());
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
     * This method exists only because of the backwards compatibility
     */


    /**
     * This method exists only because of the backwards compatibility. It is recommended to use
     * {@link #getChallengeResponse()} instead
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


