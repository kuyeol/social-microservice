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

package org.account.represetion.identitymanagement.util;

import static java.util.Optional.ofNullable;


import org.account.exception.ModelIllegalStateException;
import org.account.model.CredentialModel;
import org.account.model.UserCredentialModel;
import org.account.represetion.identitymanagement.CredentialRepresentation;
import org.account.represetion.identitymanagement.RoleRepresentation;
import org.account.represetion.identitymanagement.UserRepresentation;
import org.account.service.RoleModel;
import org.account.service.UserModel;
import org.jboss.logging.Logger;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;



/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public class ModelToRepresentation {

    public static Set<String> REALM_EXCLUDED_ATTRIBUTES = new HashSet<>();
    static {
        REALM_EXCLUDED_ATTRIBUTES.add("displayName");
        REALM_EXCLUDED_ATTRIBUTES.add("displayNameHtml");
        REALM_EXCLUDED_ATTRIBUTES.add("defaultSignatureAlgorithm");
        REALM_EXCLUDED_ATTRIBUTES.add("bruteForceProtected");
        REALM_EXCLUDED_ATTRIBUTES.add("permanentLockout");
        REALM_EXCLUDED_ATTRIBUTES.add("maxTemporaryLockouts");
        REALM_EXCLUDED_ATTRIBUTES.add("maxFailureWaitSeconds");
        REALM_EXCLUDED_ATTRIBUTES.add("waitIncrementSeconds");
        REALM_EXCLUDED_ATTRIBUTES.add("quickLoginCheckMilliSeconds");
        REALM_EXCLUDED_ATTRIBUTES.add("minimumQuickLoginWaitSeconds");
        REALM_EXCLUDED_ATTRIBUTES.add("maxDeltaTimeSeconds");
        REALM_EXCLUDED_ATTRIBUTES.add("failureFactor");
        REALM_EXCLUDED_ATTRIBUTES.add("actionTokenGeneratedByAdminLifespan");
        REALM_EXCLUDED_ATTRIBUTES.add("actionTokenGeneratedByUserLifespan");
        REALM_EXCLUDED_ATTRIBUTES.add("offlineSessionMaxLifespanEnabled");
        REALM_EXCLUDED_ATTRIBUTES.add("offlineSessionMaxLifespan");

        REALM_EXCLUDED_ATTRIBUTES.add("webAuthnPolicyRpEntityName");
        REALM_EXCLUDED_ATTRIBUTES.add("webAuthnPolicySignatureAlgorithms");
        REALM_EXCLUDED_ATTRIBUTES.add("webAuthnPolicyRpId");
        REALM_EXCLUDED_ATTRIBUTES.add("webAuthnPolicyAttestationConveyancePreference");
        REALM_EXCLUDED_ATTRIBUTES.add("webAuthnPolicyAuthenticatorAttachment");
        REALM_EXCLUDED_ATTRIBUTES.add("webAuthnPolicyRequireResidentKey");
        REALM_EXCLUDED_ATTRIBUTES.add("webAuthnPolicyUserVerificationRequirement");
        REALM_EXCLUDED_ATTRIBUTES.add("webAuthnPolicyCreateTimeout");
        REALM_EXCLUDED_ATTRIBUTES.add("webAuthnPolicyAvoidSameAuthenticatorRegister");
        REALM_EXCLUDED_ATTRIBUTES.add("webAuthnPolicyAcceptableAaguids");

        REALM_EXCLUDED_ATTRIBUTES.add("webAuthnPolicyRpEntityNamePasswordless");
        REALM_EXCLUDED_ATTRIBUTES.add("webAuthnPolicySignatureAlgorithmsPasswordless");
        REALM_EXCLUDED_ATTRIBUTES.add("webAuthnPolicyRpIdPasswordless");
        REALM_EXCLUDED_ATTRIBUTES.add("webAuthnPolicyAttestationConveyancePreferencePasswordless");
        REALM_EXCLUDED_ATTRIBUTES.add("webAuthnPolicyAuthenticatorAttachmentPasswordless");
        REALM_EXCLUDED_ATTRIBUTES.add("webAuthnPolicyRequireResidentKeyPasswordless");
        REALM_EXCLUDED_ATTRIBUTES.add("webAuthnPolicyUserVerificationRequirementPasswordless");
        REALM_EXCLUDED_ATTRIBUTES.add("webAuthnPolicyCreateTimeoutPasswordless");
        REALM_EXCLUDED_ATTRIBUTES.add("webAuthnPolicyAvoidSameAuthenticatorRegisterPasswordless");
        REALM_EXCLUDED_ATTRIBUTES.add("webAuthnPolicyAcceptableAaguidsPasswordless");



        REALM_EXCLUDED_ATTRIBUTES.add("firstBrokerLoginFlowId");
    }


    private static final Logger LOG = Logger.getLogger(ModelToRepresentation.class);







    public static UserRepresentation toRepresentation( UserModel user) {
        UserRepresentation rep = new UserRepresentation();
        rep.setId(user.getId());
        rep.setUsername(user.getUsername());
        rep.setCreatedTimestamp(user.getCreatedTimestamp());
        rep.setLastName(user.getLastName());
        rep.setFirstName(user.getFirstName());
        rep.setEmail(user.getEmail());
        rep.setEnabled(user.isEnabled());
        rep.setEmailVerified(user.isEmailVerified());

        rep.setRequiredActions(user.getRequiredActionsStream().collect(Collectors.toList()));

        Map<String, List<String>> attributes = user.getAttributes();
        Map<String, List<String>> copy = null;

        if (attributes != null) {
            copy = new HashMap<>(attributes);
            copy.remove(UserModel.LAST_NAME);
            copy.remove(UserModel.FIRST_NAME);
            copy.remove(UserModel.EMAIL);
            copy.remove(UserModel.USERNAME);
        }
        if (attributes != null && !copy.isEmpty()) {
            Map<String, List<String>> attrs = new HashMap<>(copy);
            rep.setAttributes(attrs);
        }

        return rep;
    }

    public static UserRepresentation toBriefRepresentation(UserModel user) {
        UserRepresentation rep = new UserRepresentation();
        rep.setId(user.getId());
        rep.setUsername(user.getUsername());
        rep.setCreatedTimestamp(user.getCreatedTimestamp());
        rep.setLastName(user.getLastName());
        rep.setFirstName(user.getFirstName());
        rep.setEmail(user.getEmail());
        rep.setEnabled(user.isEnabled());
        rep.setEmailVerified(user.isEmailVerified());

        return rep;
    }

    private static void addAttributeToBriefRep(UserModel user, UserRepresentation userRep, String attributeName) {
        String userAttributeValue = user.getFirstAttribute(attributeName);
        if (userAttributeValue != null) {
            if (userRep.getAttributes() == null) {
                userRep.setAttributes(new HashMap<>());
            }
            userRep.getAttributes().put(attributeName, Collections.singletonList(userAttributeValue));
        }
    }


    public static RoleRepresentation toRepresentation(RoleModel role) {
        RoleRepresentation rep = new RoleRepresentation();
        rep.setId(role.getId());
        rep.setName(role.getName());
        rep.setDescription(role.getDescription());
        rep.setComposite(role.isComposite());
        rep.setClientRole(role.isClientRole());
        rep.setContainerId(role.getContainerId());
        rep.setAttributes(role.getAttributes());
        return rep;
    }

    public static RoleRepresentation toBriefRepresentation(RoleModel role) {
        RoleRepresentation rep = new RoleRepresentation();
        rep.setId(role.getId());
        rep.setName(role.getName());
        rep.setDescription(role.getDescription());
        rep.setComposite(role.isComposite());
        rep.setClientRole(role.isClientRole());
        rep.setContainerId(role.getContainerId());
        return rep;
    }



    public static Map<String, String> stripRealmAttributesIncludedAsFields(Map<String, String> attributes) {
        Map<String, String> a = new HashMap<>();

        for (Map.Entry<String, String> e : attributes.entrySet()) {
            if (REALM_EXCLUDED_ATTRIBUTES.contains(e.getKey())) {
                continue;
            }

            if (e.getKey().startsWith("_browser_header")) {
                continue;
            }

            a.put(e.getKey(), e.getValue());
        }

        return a;
    }



    /**
     * Handles exceptions that occur when transforming the model to a representation and will remove
     * all null objects from the stream.
     *
     * Entities that have been removed from the store or where a lazy loading exception occurs will not show up
     * in the output stream.
     */
    public static <M, R> Stream<R> filterValidRepresentations(Stream<M> models, Function<M, R> transformer) {
        return models.map(m -> {
                    try {
                        return transformer.apply(m);
                    } catch ( ModelIllegalStateException e) {
                        LOG.warn("unable to retrieve model information, skipping entity", e);
                        return null;
                    }
                })
                .filter(Objects::nonNull);
    }

    public static CredentialRepresentation toRepresentation(UserCredentialModel cred) {
        CredentialRepresentation rep = new CredentialRepresentation();
        rep.setType(CredentialRepresentation.SECRET);
        rep.setValue(cred.getChallengeResponse());
        return rep;
    }

    public static CredentialRepresentation toRepresentation( CredentialModel cred) {
        CredentialRepresentation rep = new CredentialRepresentation();
        rep.setId(cred.getId());
        rep.setType(cred.getType());
        rep.setUserLabel(cred.getUserLabel());
        rep.setCreatedDate(cred.getCreatedDate());
        rep.setSecretData(cred.getSecretData());
        rep.setCredentialData(cred.getCredentialData());
        return rep;
    }

    //public static CredentialMetadataRepresentation toRepresentation(CredentialMetadata credentialMetadata) {
    //    CredentialMetadataRepresentation rep = new CredentialMetadataRepresentation();
    //
    //    rep.setCredential(ModelToRepresentation.toRepresentation(credentialMetadata.getCredentialModel()));
    //    try {
    //        rep.setInfoMessage(credentialMetadata.getInfoMessage() == null ? null : JsonSerialization.writeValueAsString(credentialMetadata.getInfoMessage()));
    //    } catch (IOException e) {
    //        LOG.warn("unable to serialize model information, skipping info message", e);
    //    }
    //    try {
    //        rep.setWarningMessageDescription(credentialMetadata.getWarningMessageDescription() == null ? null : JsonSerialization.writeValueAsString(credentialMetadata.getWarningMessageDescription()));
    //    } catch (IOException e) {
    //        LOG.warn("unable to serialize model information, skipping warning message desc", e);
    //    }
    //    try {
    //        rep.setWarningMessageTitle(credentialMetadata.getWarningMessageTitle() == null ? null : JsonSerialization.writeValueAsString(credentialMetadata.getWarningMessageTitle()));
    //    } catch (IOException e) {
    //        LOG.warn("unable to serialize model information, skipping warning message title", e);
    //    }
    //    return rep;
    //}



//    public static UserSessionRepresentation toRepresentation(UserSessionModel session) {
//        UserSessionRepresentation rep = new UserSessionRepresentation();
//        rep.setId(session.getId());
//        rep.setStart( Time.toMillis( session.getStarted()));
//        rep.setLastAccess(Time.toMillis(session.getLastSessionRefresh()));
//        rep.setCustomerName(session.getUser().getCustomerName());
//        rep.setUserId(session.getUser().getId());
//        rep.setIpAddress(session.getIpAddress());
//        rep.setRememberMe(session.isRememberMe());
//
//        return rep;
//    }










}
