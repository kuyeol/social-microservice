
# rename
    - username to customername
    - remove first, last name

# refact 
    - rename after method decleration fixed

# repository -> Dao 
    - class name fixed and theory



# Generic Pattern Ex


> 패턴 추상화 

```JAVA

public interface Entity{

}


public interface <Dao T extends Entity>{

}



public abstract class AbstractDao<T extends Entity> implements Dao<T>{

}

```

> 추상화 -> 구체화 


```java


public class UserEntity implements Entity{

}


public interface UserDao extends Dao<UserEntity>{

}


public class JpaUserDao extends AbstractDao<UserEntity> imlements UserDao{

}


```


> API 호출



```java

@Transactional
public class UserService{

private final UserDao uDao;


// Implements Methods..





}


```
