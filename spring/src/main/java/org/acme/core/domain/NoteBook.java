package org.acme.core.domain;

public abstract class NoteBook<Maker>
{

    protected Maker m;


    protected NoteBook()
    {
    }


    protected NoteBook(Maker brand)
    {
        this.m = brand;
    }


    public String getName()
    {
        return name;
    }


    protected abstract NoteBook<Maker> makeProduct();

    protected abstract NoteBook orderBook1();
    public void setName(String name)
    {
        this.name = name;
    }


    protected String name;


    protected abstract String setSeri(String s);


    @Override
    public String toString()
    {
        return getClass().getSimpleName()+"\t{" + "name='" + name +getClass().getSimpleName()+ '\'' + '}';
    }


}
