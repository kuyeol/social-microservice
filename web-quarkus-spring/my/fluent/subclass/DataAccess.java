package fluent.subclass;

import fluent.subclass.spec.SourceType;

public class DataAccess
{

    UserFactory userFactory = new UserFactory();



    public static void main(String[] args)
    {

        UserFactory userFactory = new UserFactory();


        ClassSource classSource = new ClassSource();
        classSource.setAge(12);
        classSource.setId("id");
        classSource.setName("name");

       SubType newsub = userFactory.newInstance(classSource);



    }
}
