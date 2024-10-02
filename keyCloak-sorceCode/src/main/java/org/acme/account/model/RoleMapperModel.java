

package org.acme.account.model;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public interface RoleMapperModel {

    /**
     * Returns stream of realm roles that are directly set to this object.
     * @return Stream of {@link RoleModel}. Never returns {@code null}.
     */
    Stream<RoleModel> getRealmRoleMappingsStream();



    /**
     * Returns {@code true}, if this object is directly assigned the given role.
     * 
     * @param role the role
     * @return see description
     * @see #hasRole(RoleModel) if you want to check whether this object is directly or indirectly assigned to a role
     */
    default boolean hasDirectRole(RoleModel role) {
        return getRoleMappingsStream().anyMatch(r -> Objects.equals(r, role));
    }

    /**
     * Returns {@code true} if this object is directly or indirectly assigned the given role, {@code false} otherwise.
     * <p>
     * For example, {@code true} is returned for hasRole(R) if:
     * <ul>
     *  <li>R is directly assigned to this object</li>
     *  <li>R is indirectly assigned to this object via composites</li>
     *  <li>R is not assigned to this object but this object belongs to a group G which is assigned the role R</li>
     *  <li>R is not assigned to this object but this object belongs to a group G, and G belongs to group H which is assigned the role R</li>
     * </ul>
     * @param role
     * @return see description
     * @see #hasDirectRole(RoleModel) if you want to check if this object is directly assigned to a role
     */
    boolean hasRole(RoleModel role);

    /**
     * Grants the given role to this object.
     * @param role
     */
    void grantRole(RoleModel role);

    /**
     * Returns stream of all role (both realm all client) that are directly set to this object.
     * @return Stream of {@link RoleModel}. Never returns {@code null}.
     */
    Stream<RoleModel> getRoleMappingsStream();

    /**
     * Removes the given role mapping from this object.
     * @param role Role to remove
     */
    void deleteRoleMapping(RoleModel role);
}
