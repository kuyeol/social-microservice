package org.acme.core.examplejpa;

import org.acme.core.model.Transfer;
import org.acme.core.model.Typer;

public class EntityTransfer<EntityTyper> extends Transfer
{

    EntityTyper    entity;
    String         id;





    public EntityTransfer(String id)
    {
        super(id);
        this.id             = id;
    }


    @Override
    public Transfer transferEntiry(Typer typer)
    {

        return null;
    }


    @Override
    public Transfer transferEntiry(Object entityTyper)
    {

        return null;
    }


    public void readEntity(EntityTyper entity){
        this.entity = entity;

    }


}