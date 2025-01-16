package org.acme.core.jpa;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.List;
import org.acme.core.examplejpa.EntityTransfer;
import org.acme.core.examplejpa.JpaCustomer;
import org.acme.core.model.Typer;
import org.acme.core.model.impl.EntityTyper;

public abstract class JpaProvider implements Provider<EntityTransfer, Typer>
{

    @Inject
    EntityManager em;


    public EntityTransfer add()
    {

        return null;
    }


    public EntityTransfer update()
    {

        return null;
    }


    @Override
    public EntityTransfer getEntity(EntityTyper entityTyper)
    {

        return exists(entityTyper);
    }


    @Override
    public boolean isPresent(EntityTyper entityTyper)
    {

        return em.getReference(entityTyper.getClass(), entityTyper.getId()) != null;
    }


    @Override
    public EntityTransfer exists(EntityTyper entityTyper)
    {

        try {
            entityTyper = em.find(entityTyper.getClass(), entityTyper.getId());
        } catch (EntityNotFoundException e) {

            entityTyper.setId(e.getMessage());
        }

        return toEntity(entityTyper);
    }


    public EntityTransfer toEntity(EntityTyper entityTyper)
    {

        return new EntityTransfer(entityTyper.getId());
    }


    public abstract boolean exists(JpaCustomer jpaCustomer);


    @Override
    public Collection<EntityTransfer> getAllEntities()
    {

        return List.of();
    }


}
