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

import org.jboss.logging.Logger;

import org.acme.account.json.JsonSerialization;
import org.acme.account.model.CredentialModel;
import org.acme.account.model.KeycloakSession;
import org.acme.account.model.PasswordCredentialData;
import org.acme.account.model.PasswordCredentialModel;
import org.acme.account.model.RoleModel;
import org.acme.account.model.UserModel;
import org.acme.account.represetion.identitymanagement.CredentialRepresentation;
import org.acme.account.represetion.identitymanagement.UserRepresentation;


public class RepresentationToModel {

    private static Logger logger = Logger.getLogger(RepresentationToModel.class);
    public static final String OIDC = "openid-connect";


    public static void importRealm( KeycloakSession session, RealmRepresentation rep, RealmModel newRealm, boolean skipUserDependent) {
        session.getProvider(DatastoreProvider.class).getExportImportManager().importRealm(rep, newRealm, skipUserDependent);
    }

    public static void importRoles(RolesRepresentation realmRoles, RealmModel realm) {
        if (realmRoles == null) return;

        if (realmRoles.getRealm() != null) { // realm roles
            for (RoleRepresentation roleRep : realmRoles.getRealm()) {
                if (! realm.getDefaultRole().getName().equals(roleRep.getName())) { // default role was already imported
                    createRole(realm, roleRep);
                }
            }
        }
        if (realmRoles.getClient() != null) {
            for ( Entry<String, List<RoleRepresentation>> entry : realmRoles.getClient().entrySet()) {
                ClientModel client = realm.getClientByClientId(entry.getKey());
                if (client == null) {
                    throw new RuntimeException("App doesn't exist in role definitions: " + entry.getKey());
                }
                for (RoleRepresentation roleRep : entry.getValue()) {
                    // Application role may already exists (for example if it is defaultRole)
                    RoleModel role = roleRep.getId() != null ? client.addRole(roleRep.getId(), roleRep.getName()) : client.addRole(roleRep.getName());
                    role.setDescription(roleRep.getDescription());
                    if (roleRep.getAttributes() != null) {
                        roleRep.getAttributes().forEach((key, value) -> role.setAttribute(key, value));
                    }
                }
            }
        }
        // now that all roles are created, re-iterate and set up composites
        if (realmRoles.getRealm() != null) { // realm roles
            for (RoleRepresentation roleRep : realmRoles.getRealm()) {
                RoleModel role = realm.getRole(roleRep.getName());
                addComposites(role, roleRep, realm);
            }
        }
        if (realmRoles.getClient() != null) {
            for ( Entry<String, List<RoleRepresentation>> entry : realmRoles.getClient().entrySet()) {
                ClientModel client = realm.getClientByClientId(entry.getKey());
                if (client == null) {
                    throw new RuntimeException("App doesn't exist in role definitions: " + entry.getKey());
                }
                for (RoleRepresentation roleRep : entry.getValue()) {
                    RoleModel role = client.getRole( roleRep.getName());
                    addComposites(role, roleRep, realm);
                }
            }
        }
    }





    private static void convertDeprecatedCredentialsFormat( UserRepresentation user) {
        if (user.getCredentials() != null) {
            for ( CredentialRepresentation cred : user.getCredentials()) {
                try {
                    if ((cred.getCredentialData() == null || cred.getSecretData() == null) && cred.getValue() == null) {
                        logger.warnf("Using deprecated 'credentials' format in JSON representation for user '%s'. It will be removed in future versions", user.getUsername());

                        if ( PasswordCredentialModel.TYPE.equals( cred.getType()) || PasswordCredentialModel.PASSWORD_HISTORY.equals( cred.getType())) {
                            PasswordCredentialData credentialData = new PasswordCredentialData( cred.getHashIterations(), cred.getAlgorithm());
                            cred.setCredentialData( JsonSerialization.writeValueAsString( credentialData));
                            // Created this manually to avoid conversion from Base64 and back
                            cred.setSecretData("{\"value\":\"" + cred.getHashedSaltedValue() + "\",\"salt\":\"" + cred.getSalt() + "\"}");
                            cred.setPriority(10);
                        }
                    }
                } catch (IOException ioe) {
                    throw new RuntimeException(ioe);
                }
            }
        }
    }




    public static void updateRealm(RealmRepresentation rep, RealmModel realm, KeycloakSession session) {
        session.getProvider(DatastoreProvider.class).getExportImportManager().updateRealm(rep, realm);

    }

    // Basic realm stuff

    // Roles

    public static RoleModel createRole(RealmModel newRealm, RoleRepresentation roleRep) {
        RoleModel role = roleRep.getId() != null ? newRealm.addRole(roleRep.getId(), roleRep.getName()) : newRealm.addRole(roleRep.getName());
        if (roleRep.getDescription() != null) role.setDescription(roleRep.getDescription());
        if (roleRep.getAttributes() != null) {
            for ( Entry<String, List<String>> attribute : roleRep.getAttributes().entrySet()) {
                role.setAttribute(attribute.getKey(), attribute.getValue());
            }
        }
        return role;
    }

    private static void addComposites(RoleModel role, RoleRepresentation roleRep, RealmModel realm) {
        if (roleRep.getComposites() == null) return;
        if (roleRep.getComposites().getRealm() != null) {
            for (String roleStr : roleRep.getComposites().getRealm()) {
                RoleModel realmRole = realm.getRole(roleStr);
                if (realmRole == null) throw new RuntimeException("Unable to find composite realm role: " + roleStr);
                role.addCompositeRole(realmRole);
            }
        }
        if (roleRep.getComposites().getClient() != null) {
            for ( Entry<String, List<String>> entry : roleRep.getComposites().getClient().entrySet()) {
                ClientModel client = realm.getClientByClientId(entry.getKey());
                if (client == null) {
                    throw new RuntimeException("App doesn't exist in role definitions: " + roleRep.getName());
                }
                for (String roleStr : entry.getValue()) {
                    RoleModel clientRole = client.getRole(roleStr);
                    if (clientRole == null)
                        throw new RuntimeException("Unable to find composite client role: " + roleStr);
                    role.addCompositeRole(clientRole);
                }
            }

        }

    }

    // CLIENTS

    /**
     * Does not create scope or role mappings!
     *
     * @param realm
     * @param resourceRep
     * @return
     */

    /**
     * Create an update property action, passing the first non-null value supplied to the setter, in argument order.
     *
     * @param modelSetter {@link Consumer<T>} The setter to call.
     * @param getters {@link Supplier<T>} to query for the first non-null value.
     * @return {@link Supplier} with results of operation.
     * @param <T> Type of property.
     */

    public static UserModel createUser(KeycloakSession session,  UserRepresentation userRep) {
        return session.getProvider(DatastoreProvider.class).getExportImportManager().createUser(newRealm, userRep);
    }





    public static void createCredentials(UserRepresentation userRep, KeycloakSession session,  UserModel user, boolean adminRequest) {
        convertDeprecatedCredentialsFormat(userRep);
        if (userRep.getCredentials() != null) {
            for (CredentialRepresentation cred : userRep.getCredentials()) {
                if (cred.getId() != null && user.credentialManager().getStoredCredentialById(cred.getId()) != null) {
                    continue;
                }
                if (cred.getValue() != null && !cred.getValue().isEmpty()) {

                    try {
                        user.credentialManager().updateCredential(UserCredentialModel.password(cred.getValue(), false));
                    } catch (ModelException ex) {
                        PasswordPolicyNotMetException passwordPolicyNotMetException = new PasswordPolicyNotMetException(ex.getMessage(), user.getUsername(), ex);
                        passwordPolicyNotMetException.setParameters(ex.getParameters());
                        throw passwordPolicyNotMetException;
                    } finally {
                        session.getContext().setRealm(origRealm);
                    }
                } else {
                    user.credentialManager().createCredentialThroughProvider(toModel(cred));
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


    public static IdentityProviderModel toModel(RealmModel realm, IdentityProviderRepresentation representation, KeycloakSession session) {
        IdentityProviderFactory providerFactory = (IdentityProviderFactory) session.getKeycloakSessionFactory().getProviderFactory(
                IdentityProvider.class, representation.getProviderId());

        if (providerFactory == null) {
            providerFactory = (IdentityProviderFactory) session.getKeycloakSessionFactory().getProviderFactory(
                    SocialIdentityProvider.class, representation.getProviderId());
        }

        if (providerFactory == null) {
            throw new IllegalArgumentException("Invalid identity provider id [" + representation.getProviderId() + "]");
        }

        IdentityProviderModel identityProviderModel = providerFactory.createConfig();

        identityProviderModel.setInternalId(representation.getInternalId());
        identityProviderModel.setAlias(representation.getAlias());
        identityProviderModel.setDisplayName(representation.getDisplayName());
        identityProviderModel.setProviderId(representation.getProviderId());
        identityProviderModel.setEnabled(representation.isEnabled());
        identityProviderModel.setLinkOnly(representation.isLinkOnly());
        identityProviderModel.setHideOnLogin(representation.isHideOnLogin());
        // remove the legacy hide on login attribute if present.
        String hideOnLoginAttr = representation.getConfig().remove(IdentityProviderModel.LEGACY_HIDE_ON_LOGIN_ATTR);
        if (hideOnLoginAttr != null) identityProviderModel.setHideOnLogin(Boolean.parseBoolean(hideOnLoginAttr));
        identityProviderModel.setTrustEmail(representation.isTrustEmail());
        identityProviderModel.setAuthenticateByDefault(representation.isAuthenticateByDefault());
        identityProviderModel.setStoreToken(representation.isStoreToken());
        identityProviderModel.setAddReadTokenRoleOnCreate(representation.isAddReadTokenRoleOnCreate());
        updateOrganizationBroker(representation, session);
        identityProviderModel.setOrganizationId(representation.getOrganizationId());
        identityProviderModel.setConfig(removeEmptyString(representation.getConfig()));

        String flowAlias = representation.getFirstBrokerLoginFlowAlias();
        if (flowAlias == null || flowAlias.trim().isEmpty()) {
            identityProviderModel.setFirstBrokerLoginFlowId(null);
        } else {
            AuthenticationFlowModel flowModel = realm.getFlowByAlias(flowAlias);
            if (flowModel == null) {
                throw new ModelException("No available authentication flow with alias: " + flowAlias);
            }
            identityProviderModel.setFirstBrokerLoginFlowId(flowModel.getId());
        }

        flowAlias = representation.getPostBrokerLoginFlowAlias();
        if (flowAlias == null || flowAlias.trim().isEmpty()) {
            identityProviderModel.setPostBrokerLoginFlowId(null);
        } else {
            AuthenticationFlowModel flowModel = realm.getFlowByAlias(flowAlias);
            if (flowModel == null) {
                throw new ModelException("No available authentication flow with alias: " + flowAlias);
            }
            identityProviderModel.setPostBrokerLoginFlowId(flowModel.getId());
        }

        identityProviderModel.validate(realm);

        return identityProviderModel;
    }

    public static ProtocolMapperModel toModel(ProtocolMapperRepresentation rep) {
        ProtocolMapperModel model = new ProtocolMapperModel();
        model.setId(rep.getId());
        model.setName(rep.getName());
        model.setProtocol(rep.getProtocol());
        model.setProtocolMapper(rep.getProtocolMapper());
        model.setConfig(removeEmptyString(rep.getConfig()));
        return model;
    }

    public static IdentityProviderMapperModel toModel(IdentityProviderMapperRepresentation rep) {
        IdentityProviderMapperModel model = new IdentityProviderMapperModel();
        model.setId(rep.getId());
        model.setName(rep.getName());
        model.setIdentityProviderAlias(rep.getIdentityProviderAlias());
        model.setIdentityProviderMapper(rep.getIdentityProviderMapper());
        model.setConfig(removeEmptyString(rep.getConfig()));
        return model;
    }



    public static AuthenticationFlowModel toModel(AuthenticationFlowRepresentation rep) {
        AuthenticationFlowModel model = new AuthenticationFlowModel();
        model.setId(rep.getId());
        model.setBuiltIn(rep.isBuiltIn());
        model.setTopLevel(rep.isTopLevel());
        model.setProviderId(rep.getProviderId());
        model.setAlias(rep.getAlias());
        model.setDescription(rep.getDescription());
        return model;

    }


    public static AuthenticationExecutionModel toModel(KeycloakSession session, RealmModel realm, AuthenticationExecutionRepresentation rep) {
        AuthenticationExecutionModel model = new AuthenticationExecutionModel();
        model.setId(rep.getId());
        model.setFlowId(rep.getFlowId());

        model.setAuthenticator(rep.getAuthenticator());
        if (rep.getPriority() != null) {
            model.setPriority(rep.getPriority());
        }
        model.setParentFlow(rep.getParentFlow());
        model.setAuthenticatorFlow(rep.isAuthenticatorFlow());
        model.setRequirement(AuthenticationExecutionModel.Requirement.valueOf(rep.getRequirement()));

        if (rep.getAuthenticatorConfig() != null) {
            AuthenticatorConfigModel cfg = new DeployedConfigurationsManager(session).getAuthenticatorConfigByAlias(realm, rep.getAuthenticatorConfig());
            model.setAuthenticatorConfig(cfg.getId());
        }
        return model;
    }

    public static AuthenticatorConfigModel toModel(AuthenticatorConfigRepresentation rep) {
        AuthenticatorConfigModel model = new AuthenticatorConfigModel();
        model.setId(rep.getId());
        model.setAlias(rep.getAlias());
        model.setConfig(removeEmptyString(rep.getConfig()));
        return model;
    }





    public static void importAuthorizationSettings(ClientRepresentation clientRepresentation, ClientModel client, KeycloakSession session) {
        if (Profile.isFeatureEnabled(Profile.Feature.AUTHORIZATION) && Boolean.TRUE.equals(clientRepresentation.getAuthorizationServicesEnabled())) {
            AuthorizationProviderFactory authorizationFactory = (AuthorizationProviderFactory) session.getKeycloakSessionFactory().getProviderFactory(AuthorizationProvider.class);
            AuthorizationProvider authorization = authorizationFactory.create(session, client.getRealm());

            client.setServiceAccountsEnabled(true);
            client.setBearerOnly(false);
            client.setPublicClient(false);

            ResourceServerRepresentation rep = clientRepresentation.getAuthorizationSettings();

            if (rep == null) {
                rep = new ResourceServerRepresentation();
            }

            rep.setClientId(client.getId());

            toModel(rep, authorization, client);
        }
    }

    public static ResourceServer toModel(ResourceServerRepresentation rep, AuthorizationProvider authorization, ClientModel client) {
        ResourceServerStore resourceServerStore = authorization.getStoreFactory().getResourceServerStore();
        ResourceServer resourceServer;
        ResourceServer existing = resourceServerStore.findByClient(client);

        if (existing == null) {
            resourceServer = resourceServerStore.create(client);
            resourceServer.setAllowRemoteResourceManagement(true);
            resourceServer.setPolicyEnforcementMode(PolicyEnforcementMode.ENFORCING);
        } else {
            resourceServer = existing;
        }

        resourceServer.setPolicyEnforcementMode(rep.getPolicyEnforcementMode());
        resourceServer.setAllowRemoteResourceManagement(rep.isAllowRemoteResourceManagement());

        DecisionStrategy decisionStrategy = rep.getDecisionStrategy();

        if (decisionStrategy == null) {
            decisionStrategy = DecisionStrategy.UNANIMOUS;
        }

        resourceServer.setDecisionStrategy(decisionStrategy);

        for (ScopeRepresentation scope : rep.getScopes()) {
            toModel(scope, resourceServer, authorization);
        }

        KeycloakSession session = authorization.getKeycloakSession();
        RealmModel realm = authorization.getRealm();

        for (ResourceRepresentation resource : rep.getResources()) {
            ResourceOwnerRepresentation owner = resource.getOwner();

            if (owner == null) {
                owner = new ResourceOwnerRepresentation();
                owner.setId(resourceServer.getClientId());
                resource.setOwner(owner);
            } else if (owner.getName() != null) {
                UserModel user = session.users().getUserByUsername(realm, owner.getName());

                if (user != null) {
                    owner.setId(user.getId());
                }
            }

            toModel(resource, resourceServer, authorization);
        }

        importPolicies(authorization, resourceServer, rep.getPolicies(), null);

        return resourceServer;
    }

    private static Policy importPolicies(AuthorizationProvider authorization, ResourceServer resourceServer, List<PolicyRepresentation> policiesToImport, String parentPolicyName) {
        StoreFactory storeFactory = authorization.getStoreFactory();

        for (PolicyRepresentation policyRepresentation : policiesToImport) {
            if (parentPolicyName != null && !parentPolicyName.equals(policyRepresentation.getName())) {
                continue;
            }

            Map<String, String> config = policyRepresentation.getConfig();
            String applyPolicies = config.get("applyPolicies");

            if (applyPolicies != null && !applyPolicies.isEmpty()) {
                PolicyStore policyStore = storeFactory.getPolicyStore();
                try {
                    List<String> policies = (List<String>) JsonSerialization.readValue(applyPolicies, List.class);
                    Set<String> policyIds = new HashSet<>();

                    for (String policyName : policies) {
                        Policy policy = policyStore.findByName(resourceServer, policyName);

                        if (policy == null) {
                            policy = policyStore.findById(resourceServer, policyName);
                        }

                        if (policy == null) {
                            policy = importPolicies(authorization, resourceServer, policiesToImport, policyName);
                            if (policy == null) {
                                throw new RuntimeException("Policy with name [" + policyName + "] not defined.");
                            }
                        }

                        policyIds.add(policy.getId());
                    }

                    config.put("applyPolicies", JsonSerialization.writeValueAsString(policyIds));
                } catch (Exception e) {
                    throw new RuntimeException("Error while importing policy [" + policyRepresentation.getName() + "].", e);
                }
            }

            PolicyStore policyStore = storeFactory.getPolicyStore();
            Policy policy = policyStore.findById(resourceServer, policyRepresentation.getId());

            if (policy == null) {
                policy = policyStore.findByName(resourceServer, policyRepresentation.getName());
            }

            if (policy == null) {
                policy = policyStore.create(resourceServer, policyRepresentation);
            } else {
                policy = toModel(policyRepresentation, authorization, policy);
            }

            if (parentPolicyName != null && parentPolicyName.equals(policyRepresentation.getName())) {
                return policy;
            }
        }

        return null;
    }

    public static Policy toModel(AbstractPolicyRepresentation representation, AuthorizationProvider authorization, Policy model) {
        model.setName(representation.getName());
        model.setDescription(representation.getDescription());
        model.setDecisionStrategy(representation.getDecisionStrategy());
        model.setLogic(representation.getLogic());

        Set resources = representation.getResources();
        Set scopes = representation.getScopes();
        Set policies = representation.getPolicies();

        if (representation instanceof PolicyRepresentation) {
            PolicyRepresentation policy = PolicyRepresentation.class.cast(representation);

            if (resources == null) {
                String resourcesConfig = policy.getConfig().get("resources");

                if (resourcesConfig != null) {
                    try {
                        resources = JsonSerialization.readValue(resourcesConfig, Set.class);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            if (scopes == null) {
                String scopesConfig = policy.getConfig().get("scopes");

                if (scopesConfig != null) {
                    try {
                        scopes = JsonSerialization.readValue(scopesConfig, Set.class);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            if (policies == null) {
                String policiesConfig = policy.getConfig().get("applyPolicies");

                if (policiesConfig != null) {
                    try {
                        policies = JsonSerialization.readValue(policiesConfig, Set.class);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            model.setConfig(policy.getConfig());
        }

        StoreFactory storeFactory = authorization.getStoreFactory();

        updateResources(resources, model, storeFactory);
        updateScopes(scopes, model, storeFactory);
        updateAssociatedPolicies(policies, model, storeFactory);

        PolicyProviderFactory provider = authorization.getProviderFactory(model.getType());

        if (provider == null) {
            throw new RuntimeException("Couldn't find policy provider with type [" + model.getType() + "]");
        }

        if (representation instanceof PolicyRepresentation) {
            provider.onImport(model, PolicyRepresentation.class.cast(representation), authorization);
        } else if (representation.getId() == null) {
            provider.onCreate(model, representation, authorization);
        } else {
            provider.onUpdate(model, representation, authorization);
        }


        representation.setId(model.getId());

        return model;
    }

    private static void updateScopes(Set<String> scopeIds, Policy policy, StoreFactory storeFactory) {
        if (scopeIds != null) {
            if (scopeIds.isEmpty()) {
                for (Scope scope : new HashSet<Scope>(policy.getScopes())) {
                    policy.removeScope(scope);
                }
                return;
            }
            ResourceServer resourceServer = policy.getResourceServer();
            for (String scopeId : scopeIds) {
                boolean hasScope = false;

                for (Scope scopeModel : new HashSet<Scope>(policy.getScopes())) {
                    if (scopeModel.getId().equals(scopeId) || scopeModel.getName().equals(scopeId)) {
                        hasScope = true;
                    }
                }
                if (!hasScope) {
                    Scope scope = storeFactory.getScopeStore().findById(resourceServer, scopeId);

                    if (scope == null) {
                        scope = storeFactory.getScopeStore().findByName(resourceServer, scopeId);
                        if (scope == null) {
                            throw new RuntimeException("Scope with id or name [" + scopeId + "] does not exist");
                        }
                    }

                    policy.addScope(scope);
                }
            }

            for (Scope scopeModel : new HashSet<Scope>(policy.getScopes())) {
                boolean hasScope = false;

                for (String scopeId : scopeIds) {
                    if (scopeModel.getId().equals(scopeId) || scopeModel.getName().equals(scopeId)) {
                        hasScope = true;
                    }
                }
                if (!hasScope) {
                    policy.removeScope(scopeModel);
                }
            }
        }

        policy.removeConfig("scopes");
    }

    private static void updateAssociatedPolicies(Set<String> policyIds, Policy policy, StoreFactory storeFactory) {
        ResourceServer resourceServer = policy.getResourceServer();

        if (policyIds != null) {
            if (policyIds.isEmpty()) {
                for (Policy associated: new HashSet<Policy>(policy.getAssociatedPolicies())) {
                    policy.removeAssociatedPolicy(associated);
                }
                return;
            }

            PolicyStore policyStore = storeFactory.getPolicyStore();

            for (String policyId : policyIds) {
                boolean hasPolicy = false;

                for (Policy policyModel : new HashSet<Policy>(policy.getAssociatedPolicies())) {
                    if (policyModel.getId().equals(policyId) || policyModel.getName().equals(policyId)) {
                        hasPolicy = true;
                    }
                }

                if (!hasPolicy) {
                    Policy associatedPolicy = policyStore.findById(resourceServer, policyId);

                    if (associatedPolicy == null) {
                        associatedPolicy = policyStore.findByName(resourceServer, policyId);
                        if (associatedPolicy == null) {
                            throw new RuntimeException("Policy with id or name [" + policyId + "] does not exist");
                        }
                    }

                    policy.addAssociatedPolicy(associatedPolicy);
                }
            }

            for (Policy policyModel : new HashSet<Policy>(policy.getAssociatedPolicies())) {
                boolean hasPolicy = false;

                for (String policyId : policyIds) {
                    if (policyModel.getId().equals(policyId) || policyModel.getName().equals(policyId)) {
                        hasPolicy = true;
                    }
                }
                if (!hasPolicy) {
                    policy.removeAssociatedPolicy(policyModel);
                }
            }
        }

        policy.removeConfig("applyPolicies");
    }

    private static void updateResources(Set<String> resourceIds, Policy policy, StoreFactory storeFactory) {
        if (resourceIds != null) {
            if (resourceIds.isEmpty()) {
                for (Resource resource : new HashSet<>(policy.getResources())) {
                    policy.removeResource(resource);
                }
            }
            ResourceServer resourceServer = policy.getResourceServer();

            for (String resourceId : resourceIds) {
                boolean hasResource = false;
                for (Resource resourceModel : new HashSet<>(policy.getResources())) {
                    if (resourceModel.getId().equals(resourceId) || resourceModel.getName().equals(resourceId)) {
                        hasResource = true;
                    }
                }
                if (!hasResource && !"".equals(resourceId)) {
                    Resource resource = storeFactory.getResourceStore().findById(resourceServer, resourceId);

                    if (resource == null) {
                        resource = storeFactory.getResourceStore().findByName(resourceServer, resourceId);
                        if (resource == null) {
                            throw new RuntimeException("Resource with id or name [" + resourceId + "] does not exist or is not owned by the resource server");
                        }
                    }

                    policy.addResource(resource);
                }
            }

            for (Resource resourceModel : new HashSet<>(policy.getResources())) {
                boolean hasResource = false;

                for (String resourceId : resourceIds) {
                    if (resourceModel.getId().equals(resourceId) || resourceModel.getName().equals(resourceId)) {
                        hasResource = true;
                    }
                }

                if (!hasResource) {
                    policy.removeResource(resourceModel);
                }
            }
        }

        policy.removeConfig("resources");
    }

    public static Resource toModel(ResourceRepresentation resource, ResourceServer resourceServer, AuthorizationProvider authorization) {
        ResourceStore resourceStore = authorization.getStoreFactory().getResourceStore();
        RealmModel realm = authorization.getRealm();
        ResourceOwnerRepresentation owner = resource.getOwner();

        if (owner == null) {
            owner = new ResourceOwnerRepresentation();
            owner.setId(resourceServer.getClientId());
        }

        String ownerId = owner.getId();

        if (ownerId == null) {
            ownerId = resourceServer.getClientId();
        }

        if (!resourceServer.getClientId().equals(ownerId)) {
            KeycloakSession keycloakSession = authorization.getKeycloakSession();
            UserProvider users = keycloakSession.users();
            UserModel ownerModel = users.getUserById(realm, ownerId);

            if (ownerModel == null) {
                ownerModel = users.getUserByUsername(realm, ownerId);
            }

            if (ownerModel == null) {
                throw new RuntimeException("Owner must be a valid username or user identifier. If the resource server, the client id or null.");
            }

            ownerId = ownerModel.getId();
        }

        Resource existing;

        if (resource.getId() != null) {
            existing = resourceStore.findById(resourceServer, resource.getId());
        } else {
            existing = resourceStore.findByName(resourceServer, resource.getName(), ownerId);
        }

        if (existing != null) {
            existing.setName(resource.getName());
            existing.setDisplayName(resource.getDisplayName());
            existing.setType(resource.getType());
            existing.updateUris(resource.getUris());
            existing.setIconUri(resource.getIconUri());
            existing.setOwnerManagedAccess(Boolean.TRUE.equals(resource.getOwnerManagedAccess()));
            existing.updateScopes(resource.getScopes().stream()
                    .map((ScopeRepresentation scope) -> toModel(scope, resourceServer, authorization, false))
                    .collect(Collectors.toSet()));
            Map<String, List<String>> attributes = resource.getAttributes();

            if (attributes != null) {
                Set<String> existingAttrNames = existing.getAttributes().keySet();

                for (String name : existingAttrNames) {
                    if (attributes.containsKey(name)) {
                        existing.setAttribute(name, attributes.get(name));
                        attributes.remove(name);
                    } else {
                        existing.removeAttribute(name);
                    }
                }

                for (String name : attributes.keySet()) {
                    existing.setAttribute(name, attributes.get(name));
                }
            }

            return existing;
        }

        Resource model = resourceStore.create(resourceServer, resource.getId(), resource.getName(), ownerId);

        model.setDisplayName(resource.getDisplayName());
        model.setType(resource.getType());
        model.updateUris(resource.getUris());
        model.setIconUri(resource.getIconUri());
        model.setOwnerManagedAccess(Boolean.TRUE.equals(resource.getOwnerManagedAccess()));

        Set<ScopeRepresentation> scopes = resource.getScopes();

        if (scopes != null) {
            model.updateScopes(scopes.stream().map(scope -> toModel(scope, resourceServer, authorization, false)).collect(Collectors.toSet()));
        }

        Map<String, List<String>> attributes = resource.getAttributes();

        if (attributes != null) {
            for (Entry<String, List<String>> entry : attributes.entrySet()) {
                model.setAttribute(entry.getKey(), entry.getValue());
            }
        }

        resource.setId(model.getId());

        return model;
    }

    public static Scope toModel(ScopeRepresentation scope, ResourceServer resourceServer, AuthorizationProvider authorization) {
        return toModel(scope, resourceServer, authorization, true);
    }

    public static Scope toModel(ScopeRepresentation scope, ResourceServer resourceServer, AuthorizationProvider authorization, boolean updateIfExists) {
        StoreFactory storeFactory = authorization.getStoreFactory();
        ScopeStore scopeStore = storeFactory.getScopeStore();
        Scope existing;

        if (scope.getId() != null) {
            existing = scopeStore.findById(resourceServer, scope.getId());
        } else {
            existing = scopeStore.findByName(resourceServer, scope.getName());
        }

        if (existing != null) {
            if (updateIfExists) {
                existing.setName(scope.getName());
                existing.setDisplayName(scope.getDisplayName());
                existing.setIconUri(scope.getIconUri());
            }
            return existing;
        }

        Scope model = scopeStore.create(resourceServer, scope.getId(), scope.getName());

        model.setDisplayName(scope.getDisplayName());
        model.setIconUri(scope.getIconUri());

        scope.setId(model.getId());

        return model;
    }

    public static PermissionTicket toModel(PermissionTicketRepresentation representation, ResourceServer resourceServer, AuthorizationProvider authorization) {
        PermissionTicketStore ticketStore = authorization.getStoreFactory().getPermissionTicketStore();
        PermissionTicket ticket = ticketStore.findById(resourceServer, representation.getId());
        boolean granted = representation.isGranted();

        if (granted && !ticket.isGranted()) {
            ticket.setGrantedTimestamp(System.currentTimeMillis());
        } else if (!granted) {
            ticketStore.delete(ticket.getId());
        }

        return ticket;
    }

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

    public static ResourceServer createResourceServer(ClientModel client, KeycloakSession session, boolean addDefaultRoles) {
        if ((client.isBearerOnly() || client.isPublicClient())
                && !(client.getClientId().equals(Config.getAdminRealm() + "-realm") || client.getClientId().equals(Constants.REALM_MANAGEMENT_CLIENT_ID))) {
            throw new RuntimeException("Only confidential clients are allowed to set authorization settings");
        }
        AuthorizationProvider authorization = session.getProvider(AuthorizationProvider.class);
        UserModel serviceAccount = session.users().getServiceAccount(client);

        if (serviceAccount == null) {
            client.setServiceAccountsEnabled(true);
        }

        if (addDefaultRoles) {
            RoleModel umaProtectionRole = client.getRole(Constants.AUTHZ_UMA_PROTECTION);

            if (umaProtectionRole == null) {
                umaProtectionRole = client.addRole(Constants.AUTHZ_UMA_PROTECTION);
            }

            if (serviceAccount != null) {
                serviceAccount.grantRole(umaProtectionRole);
            }
        }

        ResourceServerRepresentation representation = new ResourceServerRepresentation();

        representation.setAllowRemoteResourceManagement(true);
        representation.setClientId(client.getId());

        return toModel(representation, authorization, client);
    }

    private static void updateOrganizationBroker(IdentityProviderRepresentation representation, KeycloakSession session) {
        if (!Profile.isFeatureEnabled(Feature.ORGANIZATION)) {
            return;
        }

        IdentityProviderModel existing = Optional.ofNullable(session.identityProviders().getByAlias(representation.getAlias()))
                        .orElse(session.identityProviders().getById(representation.getInternalId()));
        String repOrgId = representation.getOrganizationId() != null ? representation.getOrganizationId() :
                representation.getConfig().remove(OrganizationModel.ORGANIZATION_ATTRIBUTE);
        String orgId = existing != null ? existing.getOrganizationId() : repOrgId;

        if (orgId != null) {
            OrganizationProvider provider = session.getProvider(OrganizationProvider.class);
            OrganizationModel org = provider.getById(orgId);

            if (org == null || (repOrgId != null && provider.getById(repOrgId) == null)) {
                throw new IllegalArgumentException("Organization associated with broker does not exist");
            }

            String domain = representation.getConfig().get(OrganizationModel.ORGANIZATION_DOMAIN_ATTRIBUTE);

            if (StringUtil.isBlank(domain)) {
                representation.getConfig().remove(OrganizationModel.ORGANIZATION_DOMAIN_ATTRIBUTE);
            } else if (org.getDomains().map(OrganizationDomainModel::getName).noneMatch(domain::equals)) {
                throw new IllegalArgumentException("Domain does not match any domain from the organization");
            }

            // make sure the link to an organization does not change
            representation.setOrganizationId(orgId);
        }
    }
}
