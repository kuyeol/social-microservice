import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import org.acme.core.utils.ModelUtils;


public class User extends AbstractBuilder implements AutoCloseable {

    private String userName;

    private String email;

    private final String name;


    public static class Builder extends AbstractBuilder.Builder<Builder> {

        private final String name = ModelUtils.generateId();

        private String userName;
        private String username;


        public Builder() {

        }

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }


        @Override
        public User create() {

            return new User(this);
        }


        @Override
        protected Builder self() {

            return this;
        }


        public Builder username(String name) {

            this.userName = name;

            return this;
        }

    }


    private User(Builder builder) {

        super(builder);
        this.name = ModelUtils.generateId();
    }


    public String getEmail() {

        return email;
    }


    public void setUsername2(String username2) {

        this.email = username2;
    }


    public String getUsername() {

        return userName;
    }


    public void setUsername(String username) {

        this.userName = username;
    }


    @Override
    public String toString() {

        return "User {" + " id: " + name + " , name: " + userName + "}";
    }


    Field[] field = User.class.getFields();


    public Field[] getField() {

        field = User.class.getDeclaredFields();
        Method[]    method = User.class.getDeclaredMethods();
        Class<User> cls    = User.class;
        for (Method m : method) {

            if (isGetterMethod(m)) {  // getter 메서드인지 확인
                // getter 메서드에서 필드 이름 추출
                Test test = new Test();

                try {
                    String fieldName = getFieldNameFromGetter(m);
                    Field  fieldt    = cls.getDeclaredField(fieldName);

                    System.out.println("Getter Method: " + m.getName() + ", Matched Field: " + fieldt.getName());

                    fieldt.setAccessible(true);
                    Object fieldValue = fieldt.get(test);
                    System.out.println("field value: " + fieldValue);

                    Object getterValue = m.invoke(test);
                    System.out.println("getter value: " + getterValue);

                } catch (Exception e) {
                    System.out.println("No matching field found for getter method: " + m.getName());
                }

                System.out.println("--------------------");
            }
        }

        for (Field f : field) {
            f.setAccessible(true);

            try {
                String modifier = Modifier.toString(f.getModifiers());
                System.out.println(modifier);
                System.out.println(f.getType()
                                    .getSimpleName() + " " + f.getName());

                fields.add(f.getName());

            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println();

        }

        return field;
    }


    private List<String> fields = new ArrayList<>();


    public List<String> getFieldName() throws NoSuchFieldException {

        getField();
        this.fields.add("start");

        return this.fields;

    }


    private static boolean isGetterMethod(Method method) {
        //get으로 시작하고 인자가 없고 boolean 타입이 아니면서 리턴 타입이 void가 아니어야 한다.
        return method.getName()
                     .startsWith("get") && method.getParameterCount() == 0 && method.getReturnType() != void.class &&
               !method.getReturnType()
                      .equals(boolean.class);

    }


    private static String getFieldNameFromGetter(Method method) {

        String methodName = method.getName()
                                  .substring(3);

        System.out.println(methodName);
        return methodName.substring(0, 1)
                         .toLowerCase() + methodName.substring(1);
    }


    @Override
    public void close() throws Exception {

    }


    public static class Test {

        public int publicInt = 1;

        private String privateString = "private";

        private String publicString = "public";

        protected double protectedDouble = 3.14;

        public boolean isBoolean = true;


        public void getVoid() {

        }


        public int getInt() {

            return this.publicInt;
        }


        public String getPublicString() {

            return publicString;
        }


        public String getPrivateString() {

            return privateString;
        }


        public double getProtectedDouble() {

            return protectedDouble;
        }


    }


}

