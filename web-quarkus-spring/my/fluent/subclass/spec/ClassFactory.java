package fluent.subclass.spec;

import fluent.subclass.ClassSource;

public interface ClassFactory<T extends SourceType> extends TypeFactory
{

    SuperType newInstance(ClassSource type);



}
