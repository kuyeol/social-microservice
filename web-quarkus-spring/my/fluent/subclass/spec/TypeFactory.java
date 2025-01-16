package fluent.subclass.spec;

import fluent.subclass.ClassSource;
import fluent.subclass.SubType;

public interface TypeFactory<T extends SubType>
{

   SubType newInstance(SourceType type);

   SuperType newInstance(ClassSource type);


}
