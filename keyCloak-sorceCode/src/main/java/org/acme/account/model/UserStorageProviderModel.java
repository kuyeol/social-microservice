
package org.acme.account.model;

import org.infinispan.protostream.annotations.ProtoTypeId;

/**
 * Stored configuration of a User Storage provider instance.
 *
 * @author <a href="mailto:mposolda@redhat.com">Marek Posolda</a>
 * @author <a href="mailto:bburke@redhat.com">Bill Burke</a>
 */
@ProtoTypeId(65539) //see org.keycloak.Marshalling
public class UserStorageProviderModel  {

    public static final String IMPORT_ENABLED = "importEnabled";
    public static final String FULL_SYNC_PERIOD = "fullSyncPeriod";
    public static final String CHANGED_SYNC_PERIOD = "changedSyncPeriod";
    public static final String LAST_SYNC = "lastSync";


    private transient Integer fullSyncPeriod;
    private transient Integer changedSyncPeriod;
    private transient Integer lastSync;
    private transient Boolean importEnabled;

}
