package org.acme.core.model;

public abstract class Typer
{

    private String id;



    public Typer(){

    }

    public Typer(String id)
    {

        this.id = id;
    }


    public String getId()
    {

        return this.id;
    }


    public void setId(String id)
    {

        this.id = id;
    }


    public abstract Typer exists();


}
