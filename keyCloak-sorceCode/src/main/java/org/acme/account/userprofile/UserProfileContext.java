/*
 *
 *  * Copyright 2021  Red Hat, Inc. and/or its affiliates
 *  * and other contributors as indicated by the @author tags.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package org.acme.account.userprofile;

import java.util.Set;
import java.util.function.Predicate;

import org.acme.account.model.UserModel;
import org.acme.account.util.StringUtil;


import static org.acme.account.constants.UserProfileConstants.ROLE_ADMIN;
import static org.acme.account.constants.UserProfileConstants.ROLE_USER;


/**
 * <p>This interface represents the different contexts from where user profiles are managed. The core contexts are already
 * available here representing the different areas in Keycloak where user profiles are managed.
 *
 * <p>The context is crucial to drive the conditions that should be respected when managing user profiles. It might be possible
 * to include in the future metadata about contexts. As well as support custom contexts.
 *
 * @author <a href="mailto:markus.till@bosch.io">Markus Till</a>
 */
public enum UserProfileContext {

    /**
     * In this context, a user profile is managed by themselves during an authentication flow such as when updating the user profile.
     */
    UPDATE_PROFILE(false, true, true),

    /**
     * In this context, a user profile is managed through the management interface such as the Admin API.
     */
    USER_API(true, false, false),

    /**
     * In this context, a user profile is managed by themselves through the account console.
     */
    ACCOUNT(false, false, true),

    /**
     * In this context, a user profile is managed by themselves when authenticating through a broker.
     */
    IDP_REVIEW(false, true, false),

    /**
     * In this context, a user profile is managed by themselves when registering to a realm.
     */
    REGISTRATION(false, true, false),


    UPDATE_EMAIL(false, true, false, Set.of( UserModel.EMAIL)::contains);

    private final boolean resetEmailVerified;
    private final Predicate<String> attributeSelector;
    private final boolean adminContext;
    private final boolean authFlowContext;
    
    UserProfileContext(boolean adminContext, boolean authFlowContext, boolean resetEmailVerified, Predicate<String> attributeSelector){
        this.adminContext = adminContext;
        this.authFlowContext = authFlowContext;
        this.resetEmailVerified = resetEmailVerified;
        this.attributeSelector = attributeSelector;
    }

    UserProfileContext(boolean adminContext, boolean authFlowContext, boolean resetEmailVerified){
        this( adminContext, authFlowContext, resetEmailVerified, StringUtil::isNotBlank);
    }

    public boolean isAdminContext() {
        return adminContext;
    }

    /**
     * @return true if context CAN BE part of the authentication flow
     */
    public boolean canBeAuthFlowContext() {
        return authFlowContext;
    }

    public boolean isResetEmailVerified() {
        return resetEmailVerified;
    }

    public boolean isRoleForContext(Set<String> roles) {
        if (roles == null)
            return false;
        return roles.contains(getContextRole());
    }

    private String getContextRole() {
        return isAdminContext() ? ROLE_ADMIN : ROLE_USER;
    }

    public boolean isAttributeSupported(String name) {
        return attributeSelector.test(name);
    }
}
