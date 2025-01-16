package org.acme.object;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity(name = "MY_ENTITY")
public class MyEntity
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private String id;


    private String name;
    private int    age;



    public String getId()
    {



        return id;
    }


    public void setId(String id)
    {

        this.id = id;
    }


    public void setName(String name)
    {

        this.name = name;
    }


    public void setAge(int age)
    {

        this.age = age;
    }


    public String getName()
    {

        return name;
    }


    public int getAge()
    {

        return age;
    }


}
