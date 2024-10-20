


```java
interface  session

    <T extends Provider> T getProvider(Class<T> clazz);



```


```java

class JpaUserProviderFactory

@Override
    public UserProvider create(KeycloakSession session) {
        EntityManager em = session.getProvider(JpaConnectionProvider.class).getEntityManager();
        return new JpaUserProvider(session, em);
    }
    
    
```




```java
interface JpaConnectionProvider

EntityManager getEntityManager();

```


