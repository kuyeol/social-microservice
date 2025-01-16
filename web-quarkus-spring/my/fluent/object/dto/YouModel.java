package fluent.object.dto;

import fluent.object.YouEntity;

public class YouModel extends Model
{

    public String id;

    public String name;


    public YouModel(YouEntity myEntity)
    {

        super(myEntity);
        this.id   = myEntity.getId();
        this.name = myEntity.getName();
    }


}
