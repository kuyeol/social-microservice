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

package org.keycloak.models.cache.infinispan.authorization.events;

import org.keycloak.models.cache.infinispan.events.InvalidationEvent;

abstract class BaseResourceServerEvent extends InvalidationEvent implements AuthorizationCacheInvalidationEvent {

    BaseResourceServerEvent(String id) {
        super(id);
    }

    @Override
    public String toString() {
        return String.format("%s [ id=%s ]", getClass().getSimpleName(), getId());
    }

}
