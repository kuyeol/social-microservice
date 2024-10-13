/*
 * Copyright 2024 Red Hat, Inc. and/or its affiliates
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

package org.keycloak.models.sessions.infinispan.remote;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.keycloak.cluster.ClusterProvider;
import org.keycloak.common.util.Time;
import org.keycloak.models.ClientModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.cache.infinispan.events.AuthenticationSessionAuthNoteUpdateEvent;
import org.keycloak.models.sessions.infinispan.InfinispanAuthenticationSessionProviderFactory;
import org.keycloak.models.sessions.infinispan.RootAuthenticationSessionAdapter;
import org.keycloak.models.sessions.infinispan.SessionEntityUpdater;
import org.keycloak.models.sessions.infinispan.entities.RootAuthenticationSessionEntity;
import org.keycloak.models.sessions.infinispan.remote.transaction.AuthenticationSessionTransaction;
import org.keycloak.models.utils.KeycloakModelUtils;
import org.keycloak.models.utils.SessionExpiration;
import org.keycloak.sessions.AuthenticationSessionCompoundId;
import org.keycloak.sessions.AuthenticationSessionProvider;
import org.keycloak.sessions.RootAuthenticationSessionModel;

public class RemoteInfinispanAuthenticationSessionProvider implements AuthenticationSessionProvider {

    private final KeycloakSession session;
    private final AuthenticationSessionTransaction transaction;
    private final int authSessionsLimit;

    public RemoteInfinispanAuthenticationSessionProvider(KeycloakSession session, int authSessionsLimit, AuthenticationSessionTransaction transaction) {
        this.session = Objects.requireNonNull(session);
        this.authSessionsLimit = authSessionsLimit;
        this.transaction = Objects.requireNonNull(transaction);
    }

    @Override
    public void close() {

    }

    @Override
    public RootAuthenticationSessionModel createRootAuthenticationSession(RealmModel realm) {
        return createRootAuthenticationSession(realm, KeycloakModelUtils.generateId());
    }

    @Override
    public RootAuthenticationSessionModel createRootAuthenticationSession(RealmModel realm, String id) {
        RootAuthenticationSessionEntity entity = new RootAuthenticationSessionEntity(id);
        entity.setRealmId(realm.getId());
        entity.setTimestamp(Time.currentTime());

        int expirationSeconds = SessionExpiration.getAuthSessionLifespan(realm);
        transaction.put(id, entity, expirationSeconds, TimeUnit.SECONDS);

        return wrap(realm, entity);
    }

    @Override
    public RootAuthenticationSessionModel getRootAuthenticationSession(RealmModel realm, String authenticationSessionId) {
        return wrap(realm, transaction.get(authenticationSessionId));
    }

    @Override
    public void removeRootAuthenticationSession(RealmModel realm, RootAuthenticationSessionModel authenticationSession) {
        transaction.remove(authenticationSession.getId());
    }

    @Override
    public void removeAllExpired() {
        // Rely on expiration of cache entries provided by infinispan. Nothing needed here.
    }

    @Override
    public void removeExpired(RealmModel realm) {
        // Rely on expiration of cache entries provided by infinispan. Nothing needed here.
    }

    @Override
    public void onRealmRemoved(RealmModel realm) {
        transaction.removeByRealmId(realm.getId());
    }

    @Override
    public void onClientRemoved(RealmModel realm, ClientModel client) {
        // No update anything on clientRemove for now. AuthenticationSessions of removed client will be handled at runtime if needed.
    }

    @Override
    public void updateNonlocalSessionAuthNotes(AuthenticationSessionCompoundId compoundId, Map<String, String> authNotesFragment) {
        if (compoundId == null) {
            return;
        }

        session.getProvider(ClusterProvider.class).notify(
                InfinispanAuthenticationSessionProviderFactory.AUTHENTICATION_SESSION_EVENTS,
                AuthenticationSessionAuthNoteUpdateEvent.create(compoundId.getRootSessionId(), compoundId.getTabId(), authNotesFragment),
                true,
                ClusterProvider.DCNotify.ALL_BUT_LOCAL_DC
        );
    }

    private RootAuthenticationSessionAdapter wrap(RealmModel realm, RootAuthenticationSessionEntity entity) {
        return entity == null ? null : new RootAuthenticationSessionAdapter(session, new RootAuthenticationSessionUpdater(realm, entity, transaction), realm, authSessionsLimit);
    }

    private record RootAuthenticationSessionUpdater(RealmModel realm, RootAuthenticationSessionEntity entity,
                                                    AuthenticationSessionTransaction transaction
    ) implements SessionEntityUpdater<RootAuthenticationSessionEntity> {

        @Override
        public RootAuthenticationSessionEntity getEntity() {
            return entity;
        }

        @Override
        public void onEntityUpdated() {
            int expirationSeconds = entity.getTimestamp() - Time.currentTime() + SessionExpiration.getAuthSessionLifespan(realm);
            transaction.replace(entity.getId(), entity, expirationSeconds, TimeUnit.SECONDS);
        }

        @Override
        public void onEntityRemoved() {
            transaction.remove(entity.getId());
        }
    }
}
