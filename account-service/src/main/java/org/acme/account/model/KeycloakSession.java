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

package org.acme.account.model;



import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public interface KeycloakSession extends AutoCloseable {



        KeycloakContext getContext();

        KeycloakTransactionManager getTransactionManager();


        <T extends Provider> T getProvider(Class<T> clazz);

        <T extends Provider> T getProvider(Class<T> clazz, String id);


        <T extends Provider> T getComponentProvider(Class<T> clazz, String componentId);


        <T extends Provider> T getComponentProvider(Class<T> clazz, String componentId, Function<KeycloakSessionFactory, ComponentModel> modelGetter);


        <T extends Provider> Set<String> listProviderIds(Class<T> clazz);

        <T extends Provider> Set<T> getAllProviders(Class<T> clazz);

        Class<? extends Provider> getProviderClass(String providerClassName);

        Object getAttribute(String attribute);
        <T> T getAttribute(String attribute, Class<T> clazz);
        default <T> T getAttributeOrDefault(String attribute, T defaultValue) {
            T value = (T) getAttribute(attribute);

            if (value == null) {
                return defaultValue;
            }

            return value;
        }

        Object removeAttribute(String attribute);
        void setAttribute(String name, Object value);

        Map<String, Object> getAttributes();

        void invalidate(InvalidableObjectType type, Object... params);

        void enlistForClose(Provider provider);

        KeycloakSessionFactory getKeycloakSessionFactory();


        RealmProvider realms();


        ClientProvider clients();


        ClientScopeProvider clientScopes();


        GroupProvider groups();


        RoleProvider roles();


        UserSessionProvider sessions();


        UserLoginFailureProvider loginFailures();

        AuthenticationSessionProvider authenticationSessions();

        SingleUseObjectProvider singleUseObjects();


        IdentityProviderStorageProvider identityProviders();

        @Override
        void close();


        UserProvider users();


        KeyManager keys();


        ThemeManager theme();

        TokenManager tokens();

        VaultTranscriber vault();


        ClientPolicyManager clientPolicy();

        boolean isClosed();
    }