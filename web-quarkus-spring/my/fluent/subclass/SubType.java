package fluent.subclass;

import fluent.subclass.spec.SourceType;
import fluent.subclass.spec.SuperType;

public class SubType implements SuperType
{

    private String name;

    private String id;


    public SubType()
    {

        this.id = "null";
    }


    public SubType(ClassSource source)
    {
        this.id   = source.getId();
        this.name = source.getName();
    }




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
