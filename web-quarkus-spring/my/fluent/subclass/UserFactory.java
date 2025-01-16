package fluent.subclass;

import fluent.subclass.spec.SourceType;
import fluent.subclass.spec.SuperType;
import fluent.subclass.spec.TypeFactory;

public class UserFactory implements TypeFactory
{



    @Override
    public SubType newInstance(SourceType type)
    {

        return new SubType(type);
    }




}
