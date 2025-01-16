package org.acme.entity;

import org.acme.EntityID;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class AtomicTable implements EntityID
{


    @Id
    private int id;

    private String name;

@Override
    public int getId()
    {
        return id;
    }


    public void setId(int id)
    {
        this.id = id;
    }


    public String getName()
    {
        return name;
    }


    public void setName(String name)
    {
        this.name = name;
    }


}
