package org.acme.ext.zerg.entity;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.acme.EntityID;

@Entity
public class SpawningPool implements EntityID
{

    @Id
    @Access(value = AccessType.PROPERTY)
    private int id;

    private String name;


    public SpawningPool()
    {

    }


    public int getAge()
    {
        return age;
    }


    public void setAge(int age)
    {
        this.age = age;
    }


    private int age;


    public void setName(String name)
    {
        this.name = name;
    }


    @Override
    public int getId()
    {
        return id;
    }


    public String getName()
    {
        return name;
    }


    public void setId(int id)
    {
        this.id = id;
    }


    @Override
    public String toString()
    {
        return "SpawningPool{" + "id=" + id + ", name='" + name + '\'' + '}';
    }


}
