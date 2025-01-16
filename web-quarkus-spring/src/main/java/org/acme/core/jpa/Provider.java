package org.acme.core.jpa;

import org.acme.core.examplejpa.EntityTransfer;

public interface Provider<Transfer, EntityTyper> extends ReadOnly<Transfer, EntityTyper>
{

    EntityTransfer save(EntityTyper entityTyper);

    EntityTransfer remove(EntityTyper entityTyper);

    Transfer getEntity(org.acme.core.model.impl.EntityTyper entityTyper);

    boolean isPresent(org.acme.core.model.impl.EntityTyper entityTyper);

    Transfer exists(org.acme.core.model.impl.EntityTyper entityTyper);


}
