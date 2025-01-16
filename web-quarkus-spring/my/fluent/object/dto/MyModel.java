package fluent.object.dto;

import fluent.object.MyEntity;

public class MyModel extends Model
{

    public  String   id;

    public  String   name;


    public MyModel(MyEntity myEntity)
    {

        super(myEntity);
        this.id   = myEntity.getId();
        this.name = myEntity.getName();
    }


}
