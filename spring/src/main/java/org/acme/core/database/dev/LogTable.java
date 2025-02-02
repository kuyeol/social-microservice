package org.acme.core.database.dev;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.sql.Timestamp;
import org.acme.EntityID;

@Entity
public class LogTable implements EntityID
{

    @Id
    private int       id;

    private Timestamp timestamp;

    private String    name;


    public LogTable(){
        timestamp = new Timestamp(System.currentTimeMillis());
    }

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
