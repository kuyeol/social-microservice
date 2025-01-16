package org.acme.object;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@Entity
public class BaseEntity
{



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public String id;


    public String getId()
    {

        return id;
    }


    public void setId(String id)
    {

        this.id = id;
    }


}
