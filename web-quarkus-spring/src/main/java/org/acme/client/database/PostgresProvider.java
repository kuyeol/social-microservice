package org.acme.client.database;

import java.util.Collection;
import org.acme.core.examplejpa.CustomerTransfer;
import org.acme.core.examplejpa.EntityTransfer;
import org.acme.core.examplejpa.JpaCustomer;
import org.acme.core.jpa.JpaProvider;
import org.acme.core.model.Typer;
import org.acme.core.model.impl.EntityTyper;

public class PostgresProvider extends JpaProvider
{

    @Override
    public EntityTransfer save(Typer typer)
    {

        return null;
    }


    @Override
    public EntityTransfer remove(Typer typer)
    {

        return null;
    }


    @Override
    public EntityTransfer add()
    {

        return super.add();
    }


    @Override
    public EntityTransfer update()
    {

        return null;
    }


    @Override
    public EntityTransfer toEntity(EntityTyper entityTyper)
    {

        return super.toEntity(entityTyper);
    }


    @Override
    public boolean exists(JpaCustomer jpaCustomer)
    {

        return false;
    }


    @Override
    public EntityTransfer getEntity(Typer entityTyper)
    {

        return null;
    }


    @Override
    public Collection<EntityTransfer> getAllEntities()
    {

        return super.getAllEntities();
    }





}
