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

package org.acme.account.represetion.identitymanagement.util;

import jakarta.ws.rs.core.UriInfo;

import org.acme.account.model.KeycloakSession;
import org.acme.account.model.Provider;
import org.acme.account.model.UserModel;


/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public interface BruteForceProtector extends Provider {
    String DISABLED_BY_PERMANENT_LOCKOUT = "permanentLockout";

    void failedLogin(UserModel user, UriInfo uriInfo);

    void successfulLogin(UserModel user, UriInfo uriInfo);

    boolean isTemporarilyDisabled( KeycloakSession session, UserModel user);

    boolean isPermanentlyLockedOut(KeycloakSession session, UserModel user);

    /**
     * Clears any remaining traces of the permanent lockout. Does not enable the user as such!
     * @param session
     * @param realm
     * @param user
     */
    void cleanUpPermanentLockout(KeycloakSession session, UserModel user);
}
