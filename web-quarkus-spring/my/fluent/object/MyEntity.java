package fluent.object;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

@jakarta.persistence.Entity
public class MyEntity extends Entity
{


    private String name;
    private int age;

    @Id
    @Column(name = "ID", nullable = false)
    private String id;


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
