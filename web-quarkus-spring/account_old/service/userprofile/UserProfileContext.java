
package org.account.service.userprofile;


import org.account.service.UserModel;
import org.account.util.StringUtil;

import java.util.Set;
import java.util.function.Predicate;

import static org.account.service.userprofile.UserProfileConstants.ROLE_ADMIN;
import static org.account.service.userprofile.UserProfileConstants.ROLE_USER;


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
