
package org.account.represetion.identitymanagement.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Configuration of the User Profile for one realm.
 *
 * @author Vlastimil Elias <velias@redhat.com>
 *
 */
public class UPConfig implements Cloneable {

    public enum UnmanagedAttributePolicy {

        /**
         * Unmanaged attributes are enabled and available from any context.
         */
        ENABLED,

        /**
         * Unmanaged attributes are only available as read-only and only through the management interfaces.
         */
        ADMIN_VIEW,

        /**
         * Unmanaged attributes are only available as read-write and only through the management interfaces.
         */
        ADMIN_EDIT
    }


    private UnmanagedAttributePolicy unmanagedAttributePolicy;
















    public UnmanagedAttributePolicy getUnmanagedAttributePolicy() {
        return unmanagedAttributePolicy;
    }

    public void setUnmanagedAttributePolicy(UnmanagedAttributePolicy unmanagedAttributePolicy) {
        this.unmanagedAttributePolicy = unmanagedAttributePolicy;
    }

    @Override
    public String toString() {
        return "UPConfig [attribute ]";
    }



    @Override
    public int hashCode() {
        return Objects.hash( unmanagedAttributePolicy);
    }


}
