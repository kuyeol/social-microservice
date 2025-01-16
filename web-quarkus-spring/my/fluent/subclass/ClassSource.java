package fluent.subclass;

import fluent.subclass.spec.SourceType;

public class ClassSource implements SourceType
{

    private String name;

    private String id;


    public int getAge()
    {

        return age;
    }


    public void setAge(int age)
    {

        this.age = age;
    }


    private int age;


    public String getName()
    {

        return name;
    }


    public void setName(String name)
    {

        this.name = name;
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
