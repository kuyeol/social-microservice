
package org.acme.account.model;




public interface UserStorageProvider extends Provider {




    /**
     * Callback when a role is removed.  Allows you to do things like remove a user
     * role mapping in your external store if appropriate

     * @param realm
     * @param role
     */
    default
    void preRemove( RoleModel role) {

    }

    /**
     * Optional type that can be used by implementations to
     * describe edit mode of user storage
     *
     */
    enum EditMode {
        /**
         * user storage is read-only
         */
        READ_ONLY,
        /**
         * user storage is writable
         *
         */
        WRITABLE,
        /**
         * updates to user are stored locally and not synced with user storage.
         *
         */
        UNSYNCED
    }
}

