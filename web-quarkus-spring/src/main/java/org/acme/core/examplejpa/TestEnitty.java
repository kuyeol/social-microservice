package org.acme.core.examplejpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.acme.core.model.impl.EntityTyper;

@Entity
public class TestEnitty extends EntityTyper
{

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    private String name;

    private String age;


    public TestEnitty(String id)
    {

        super(id);
    }


    public TestEnitty()
    {

        super();
    }


    public String getId()
    {

        return id;
    }


    public void setId(String id)
    {

        this.id = id;
    }


}
