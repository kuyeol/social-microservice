package org.acme.object;



public record dd(BaseEntity m)
{

    private static String name;




    public void MyEntity(String s)
    {

    }

    public void Entity(String s)
    {

    }

    public MyEntity create(String s)
    {

        return new MyEntity();
    }


    public void YouEntity(String s)
    {

        name = s;
    }




}
