package org.acme.entity;

import org.acme.EntityID;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Barracks implements EntityID
{

    @Id
    @Access(value = AccessType.PROPERTY)
    private int id;

    private String name;


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


    @Override
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
        return "Barracks{" + "id=" + id + ", name='" + name + '\'' + '}';
    }


}
