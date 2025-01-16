package org.acme.core.jpa;

import java.util.Collection;
import org.acme.core.model.impl.EntityTyper;

public interface ReadOnly<EntityTransfer,Typer>
{

    EntityTransfer getEntity(Typer entityTyper);

    boolean isPresent(EntityTyper entityTyper);

    EntityTransfer exists(EntityTyper entityTyper);

    Collection<EntityTransfer> getAllEntities();


}
