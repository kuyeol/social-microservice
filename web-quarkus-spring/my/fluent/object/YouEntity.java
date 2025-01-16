package fluent.object;


@jakarta.persistence.Entity
public class YouEntity extends Entity
{
    private String name;


    public void setName(String name)
    {

        this.name = name;
    }


    public String getName()
    {

        return name;
    }


}
