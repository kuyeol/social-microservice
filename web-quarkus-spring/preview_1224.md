
// TODO: 엔터티 생설 

```java
package org.keycloak.models.jpa.entities;

import org.hibernate.annotations.Nationalized;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import org.keycloak.storage.jpa.JpaHashUtils;

/**
* @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
* @version $Revision: 1 $
  */
  @NamedQueries({
  @NamedQuery(name="deleteUserAttributesByRealm", query="delete from  UserAttributeEntity attr where attr.user IN (select u from UserEntity u where u.realmId=:realmId)"),
  @NamedQuery(name="deleteUserAttributesByNameAndUser", query="delete from  UserAttributeEntity attr where attr.user.id = :userId and attr.name = :name"),
  @NamedQuery(name="deleteUserAttributesByNameAndUserOtherThan", query="delete from  UserAttributeEntity attr where attr.user.id = :userId and attr.name = :name and attr.id <> :attrId"),
  @NamedQuery(name="deleteUserAttributesByRealmAndLink", query="delete from  UserAttributeEntity attr where attr.user IN (select u from UserEntity u where u.realmId=:realmId and u.federationLink=:link)")
  })
  @Table(name="USER_ATTRIBUTE")
  @Entity
  public class UserAttributeEntity {

  @Id
  @Column(name="ID", length = 36)
  @Access(AccessType.PROPERTY) // we do this because relationships often fetch id, but not entity.  This avoids an extra SQL
  protected String id;

  @ManyToOne(fetch= FetchType.LAZY)
  @JoinColumn(name = "USER_ID")
  protected UserEntity user;

  @Column(name = "NAME")
  protected String name;
  @Nationalized
  @Column(name = "VALUE")
  protected String value;

  @Column(name = "LONG_VALUE_HASH")
  private byte[] longValueHash;
  @Column(name = "LONG_VALUE_HASH_LOWER_CASE")
  private byte[] longValueHashLowerCase;
  @Nationalized
  @Column(name = "LONG_VALUE")
  private String longValue;

  public String getId() {
  return id;
  }

  public void setId(String id) {
  this.id = id;
  }

  public String getName() {
  return name;
  }

  public void setName(String name) {
  this.name = name;
  }

  public String getValue() {
  if (value != null && longValue != null) {
  throw new IllegalStateException(String.format("User with id %s should not have set both `value` and `longValue` for attribute %s.", user.getId(), name));
  }
  return value != null ? value : longValue;
  }

  public void setValue(String value) {
  if (value == null) {
  this.value = null;
  this.longValue = null;
  this.longValueHash = null;
  this.longValueHashLowerCase = null;
  } else if (value.length() > 255) {
  this.value = null;
  this.longValue = value;
  this.longValueHash = JpaHashUtils.hashForAttributeValue(value);
  this.longValueHashLowerCase = JpaHashUtils.hashForAttributeValueLowerCase(value);
  } else {
  this.value = value;
  this.longValue = null;
  this.longValueHash = null;
  this.longValueHashLowerCase = null;
  }
  }

  public UserEntity getUser() {
  return user;
  }

  public void setUser(UserEntity user) {
  this.user = user;
  }

  @Override
  public boolean equals(Object o) {
  if (this == o) return true;
  if (o == null) return false;
  if (!(o instanceof UserAttributeEntity)) return false;

       UserAttributeEntity that = (UserAttributeEntity) o;

       if (!id.equals(that.getId())) return false;

       return true;
  }

  @Override
  public int hashCode() {
  return id.hashCode();
  }


}

```


# Stream list Mapping A to B

A construct

```java

public class A {
    public	String x;
    public	String y;
    public	String z;
 
 	public A(String px, String py, String pz) 
    {
 		this.x=px;
 		this.y=py;
		this.z=pz; 
 	}
    
}


public class B {
 	public String x;
    public String y;
 	
 
 	public B( String px, String py) 
    {
 		this.x=px;
 		this.y=py;
 	}
    
}


ArrayList<A> listA = new ArrayList<>();
//listA.add(new A("xValue" , "yValue" , "zValue") );
//listA.stream().forEach( (a) -> { System.out.println( a.x+ a.y +a.z); } );

Stream<B> listB = listA.stream().map( (a) -> new B(a.x,a.y) );

//listB.stream().forEach( (b) -> {  System.out.print(b.x + b.y ); });


```


## todo: 1225 plan

# rename
    - username to customername
    - remove first, last name

# refact 
    - rename after method decleration fixed

# repository -> Dao 
    - class name fixed and theory





제네릭을 사용한 DAO 패턴의 구현 예시를 보여드리겠습니다:

```java
// 1. 기본 Entity 인터페이스
public interface EntityModel {
    Long getId();
    void setId(Long id);
}

// 2. 기본 DAO 인터페이스
public interface DaoModel<T extends EntityModel> {
    T findById(Long id);
    List<T> findAll();
    void save(T entity);
    void update(T entity);
    void delete(Long id);
}

// 3. 추상 DAO 구현체
@Repository
public abstract class AbstractDao<T extends EntityModel> implements DaoModel<T> {
    
    @PersistenceContext
    protected EntityManager em;
    
    private final Class<T> entityClass;
    
    protected AbstractDao(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
    
    @Override
    public T findById(Long id) {
        return em.find(entityClass, id);
    }
    
    @Override
    public List<T> findAll() {
        return em.createQuery("from " + entityClass.getName(), entityClass)
                .getResultList();
    }
    
    @Override
    public void save(T entity) {
        em.persist(entity);
    }
    
    @Override
    public void update(T entity) {
        em.merge(entity);
    }
    
    @Override
    public void delete(Long id) {
        T entity = findById(id);
        if (entity != null) {
            em.remove(entity);
        }
    }
}

// 4. 구체적인 Entity 구현
@Entity
@Table(name = "users")
public class UserEntity implements EntityModel {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    
    @Override
    public Long getId() {
        return id;
    }
    
    @Override
    public void setId(Long id) {
        this.id = id;
    }
    
    // getter, setter
}

// 5. 구체적인 DAO 인터페이스
public interface UserDaoModel extends DaoModel<UserEntity> {
    List<User> findByName(String name);
    Optional<User> findByEmail(String email);
}

// 6. 구체적인 DAO 구현체
@Repository
public class JpaUserDao extends AbstractDao<UserEntity> implements UserDaoModel {
    
    public UserDaoImpl() {
        super(User.class);
    }
    
    @Override
    public List<User> findByName(String name) {
        return em.createQuery("select u from User u where u.name = :name", User.class)
                .setParameter("name", name)
                .getResultList();
    }
    
    @Override
    public Optional<User> findByEmail(String email) {
        try {
            User user = em.createQuery("select u from User u where u.email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
            return Optional.of(user);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<UserEntity> findByEmail2(String email) {
        return Optional.ofNullable(
            em.createQuery("select u from UserEntity u where u.email = :email", UserEntity.class)
                .setParameter("email", email)
                .getSingleResultOrNull()
        );
    }
    
    
    
    
}

// 7. 서비스 계층에서 사용
@Service
@Transactional(readOnly = true)
public class UserService {
    private final UserDao userDao;
    
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }
    
    @Transactional
    public void createUser(User user) {
        validateUser(user);
        userDao.save(user);
    }
    
    public Optional<User> getUserByEmail(String email) {
        return userDao.findByEmail(email);
    }
    
    private void validateUser(User user) {
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
    }
}

// 8. 다른 Entity에 대한 DAO 구현 예시
@Entity
@Table(name = "products")
public class Product implements BaseEntity {
    @Id @GeneratedValue
    private Long id;
    private String name;
    private BigDecimal price;
    
    // BaseEntity 구현 및 getter, setter
}

@Repository
public class ProductDaoImpl extends AbstractDao<Product> implements ProductDao {
    
    public ProductDaoImpl() {
        super(Product.class);
    }
    
    // Product 특화 메서드 구현
}
```

이 구현의 주요 특징:

1. 타입 안전성:
```java
// 컴파일 타임에 타입 체크
User user = userDao.findById(1L);  // 타입 안전
Product product = productDao.findById(1L);  // 타입 안전
```

2. 코드 재사용:
```java
// 모든 기본 CRUD 작업은 AbstractDao에서 상속
public void someBusinessLogic() {
    // UserDao와 ProductDao 모두 동일한 기본 메서드 사용 가능
    userDao.findAll();
    productDao.findAll();
}
```

3. 확장성:
```java
// 새로운 Entity 추가 시
public interface OrderDao extends BaseDao<Order> {
    List<Order> findByCustomerId(Long customerId);
}

@Repository
public class OrderDaoImpl extends AbstractDao<Order> implements OrderDao {
    public OrderDaoImpl() {
        super(Order.class);
    }
    
    // Order 특화 메서드 구현
}
```

이 패턴은 코드 중복을 줄이고, 타입 안전성을 보장하며, 새로운 엔티티 추가 시 확장이 용이한 구조를 제공합니다.