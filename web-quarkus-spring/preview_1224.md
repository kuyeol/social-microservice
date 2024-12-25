


# Stream list Mapping A to B

A construct

```java
class A 
{
 	String x;
 	String y;
 	string z;
 
 	public A( String px, String py, String pz) 
    {
 		this.x=px;
 		this.y=py;
		this.z=pz; 
 	}
    
}
```
B construct
B[] = A[]-z= x+y

```java
class B 
{
 	String x;
 	String y;
 	
 
 	public B( String px, String py) 
    {
 		this.x=px;
 		this.y=py;
 	}
    
}
```

List Mapping A -> B

[//]: # ()
[//]: # (> A class List initialize)

[//]: # ()
[//]: # (```java)

[//]: # (ArrayList<A> listA = new ArrayList<>&#40;&#41;;)

[//]: # (```)

[//]: # ()
[//]: # (> listA new instant add)

[//]: # (```java)

[//]: # (listA.add&#40;new A&#40;"xValue","yValue","zValue"&#41;&#41;;)

[//]: # (```)

[//]: # ()
[//]: # (> print Stream list)

[//]: # (```java)

[//]: # (listA.stream&#40;&#41;.forEach&#40; &#40;a&#41; -> { )

[//]: # (                                	System.out.println&#40; a.x+ a.y +a.z&#41;; )

[//]: # (                                })

[//]: # (						&#41;;)

[//]: # (```)

[//]: # ()
[//]: # (> new B intance And mappedBy Reference listA)

[//]: # (```java)

[//]: # (Stream<B> listB = listA.stream&#40;&#41;.map&#40; &#40;a&#41; -> new B&#40;a.x,a.y&#41; &#41;;)

[//]: # (```)

[//]: # ()
[//]: # (> print listB)

[//]: # ()
[//]: # (```java)

[//]: # (listB.stream&#40;&#41;)

[//]: # (	 .forEach&#40; &#40;b&#41; -> { )

[//]: # (     					System.out.print&#40;b.x + b.y &#41;; )

[//]: # (                      } )

[//]: # (              &#41;;)

[//]: # (```)








# Stream list Mapping A to B

A construct

```java
class A 
{
 	String x;
 	String y;
 	string z;
 
 	public A( String px, String py, String pz) 
    {
 		this.x=px;
 		this.y=py;
		this.z=pz; 
 	}
    
}
```
B construct
B[] = A[]-z= x+y

```java
class B 
{
 	String x;
 	String y;
 	
 
 	public B( String px, String py) 
    {
 		this.x=px;
 		this.y=py;
 	}
    
}
```

List Mapping A -> B


> A class List initialize

```java
ArrayList<A> listA = new ArrayList<>();

```

> listA new instant add
```java

listA.add(new A("xValue","yValue","zValue"));

```

> print Stream list

> 
```java

listA.stream().forEach( (a) -> { 
                                	System.out.println( a.x+ a.y +a.z); 
                                }
						);

```

> new B intance And mappedBy Reference listA
```java
Stream<B> listB = listA.stream().map( (a) -> new B(a.x,a.y) );
```

> print listB

```java
listB.stream()
	 .forEach( (b) -> { 
     					System.out.print(b.x + b.y ); 
                      } 
              );
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