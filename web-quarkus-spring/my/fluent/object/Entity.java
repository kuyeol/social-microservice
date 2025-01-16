package fluent.object;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

@jakarta.persistence.Entity
public abstract class Entity
{

    @Id
    @Column(name = "id", nullable = false)
    private String id;


    public String getId()
    {

        return id;
    }


    public void setId(String id)
    {

        this.id = id;
    }


}
