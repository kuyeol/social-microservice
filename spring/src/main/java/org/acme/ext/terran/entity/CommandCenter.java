package org.acme.ext.terran.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.acme.EntityID;


@Entity
public class CommandCenter implements EntityID
{

    @Id
    private int id;

    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
