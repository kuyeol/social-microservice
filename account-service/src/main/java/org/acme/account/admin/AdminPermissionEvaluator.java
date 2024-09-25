
package org.acme.account.admin;



/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public interface AdminPermissionEvaluator {

    void requireAnyAdminRole();

    AdminAuth adminAuth();

    RolePermissionEvaluator roles();
    UserPermissionEvaluator users();



    /**
     * Useful as a function pointer, i.e. RoleMapperResource is reused bewteen GroupResource and UserResource to manage role mappings.
     * We don't know what type of resource we're managing here (user or group), so we don't know how to query the policy engine to determine
     * if an action is allowed.
     *
     */
    interface PermissionCheck {
        boolean evaluate();
    }
    /**
     * Useful as a function pointer, i.e. RoleMapperResource is reused bewteen GroupResource and UserResource to manage role mappings.
     * We don't know what type of resource we're managing here (user or group), so we don't know how to query the policy engine to determine
     * if an action is allowed.
     *
     * throws appropriate exception if permission is deny
     *
     */
    interface RequirePermissionCheck {
        void require();
    }
}
