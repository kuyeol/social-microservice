
1. Quarkus 버전

1.1. 프로젝트 설정:

Quarkus CLI 또는 Maven/Gradle을 사용하여 프로젝트를 생성합니다.

pom.xml (Maven) 또는 build.gradle (Gradle)에 다음 의존성을 추가합니다.

quarkus-hibernate-orm: JPA 및 Hibernate ORM

quarkus-jdbc-postgresql: PostgreSQL JDBC 드라이버

quarkus-security: Spring Security 대안

quarkus-security-jpa: JPA 기반 인증
```
<!-- Maven 예시 -->
<dependencies>
    <dependency>
        <groupId>io.quarkus</groupId>
        <artifactId>quarkus-hibernate-orm</artifactId>
    </dependency>
    <dependency>
        <groupId>io.quarkus</groupId>
        <artifactId>quarkus-jdbc-postgresql</artifactId>
    </dependency>
    <dependency>
        <groupId>io.quarkus</groupId>
        <artifactId>quarkus-security</artifactId>
    </dependency>
    <dependency>
        <groupId>io.quarkus</groupId>
        <artifactId>quarkus-security-jpa</artifactId>
    </dependency>
        <dependency>
            <groupId>io.quarkus</groupId>
            <artifactId>quarkus-resteasy-reactive</artifactId>
        </dependency>
    </dependencies>
```
Use code with caution.
Xml
1.2. 엔티티 클래스 (User):
```
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;

import javax.persistence.*;
import java.util.List;
`
@Entity
@Table(name = "users")
@UserDefinition
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Username
    @Column(nullable = false, unique = true)
    private String username;

    @Password
    @Column(nullable = false)
    private String password;

    @Roles
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private List<String> roles;


    public User() {
    }

    public User(String username, String password, List<String> roles) {
      this.username = username;
      this.password = password;
      this.roles = roles;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

public List<String> getRoles() {
return roles;
}

public void setRoles(List<String> roles) {
this.roles = roles;
}
}
```

Use code with caution.
Java
@UserDefinition: Quarkus Security에 의해 인식되는 사용자 엔티티임을 나타냅니다.

@Username, @Password, @Roles: 사용자 이름, 비밀번호, 권한 필드를 지정합니다.

1.3. 리포지토리 인터페이스:
```
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {
public Optional<User> findByUsername(String username) {
return find("username", username).firstResultOptional();
}
}
```
Use code with caution.
Java
PanacheRepository 인터페이스를 구현하여 간단하게 엔티티 매니저를 사용할 수 있습니다.

1.4. 보안 설정:
```
Quarkus는 application.properties 또는 application.yml에서 보안 설정을 구성합니다.

quarkus.datasource.db-kind=postgresql
quarkus.datasource.jdbc.url=jdbc:postgresql://localhost:5432/your_database_name
quarkus.datasource.username=your_username
quarkus.datasource.password=your_password

quarkus.hibernate-orm.dialect=org.hibernate.dialect.PostgreSQLDialect
quarkus.hibernate-orm.sql-load-script=import.sql

quarkus.security.jpa.enabled=true
quarkus.security.jpa.authenticator.user-query=SELECT u FROM User u WHERE u.username=?1
quarkus.security.jpa.authenticator.hash-password=true
Use code with caution.
Properties
quarkus.security.jpa.enabled=true: JPA 기반 인증을 활성화합니다.

quarkus.security.jpa.authenticator.user-query: 사용자 정보를 조회하는 JPQL 쿼리를 지정합니다.

quarkus.security.jpa.authenticator.hash-password=true: 비밀번호를 해시하여 저장합니다.
```

import.sql 파일을 사용하여 초기 데이터를 추가할 수 있습니다.

-- import.sql

-- 초기 사용자 생성
INSERT INTO users (id, username, password) VALUES (1,'user1', '{bcrypt}$2a$10$g12e7a89B703Vj1G2qU6j.1q3v0l8r117o5eG3Z9rR6q1r18z02q');
INSERT INTO user_roles (user_id, role) VALUES (1, 'USER');
Use code with caution.
SQL
1.5. 컨트롤러:
```
import io.quarkus.security.identity.SecurityIdentity;
import org.jboss.resteasy.reactive.RestPath;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthController {

@Inject
UserRepository userRepository;

@Inject
SecurityIdentity securityIdentity;

@POST
@Path("/signup")
@Transactional
public Response signup(@QueryParam("username") String username, @QueryParam("password") String password) {
// 비밀번호 암호화
String encodedPassword = org.mindrot.jbcrypt.BCrypt.hashpw(password, org.mindrot.jbcrypt.BCrypt.gensalt());
User user = new User(username,encodedPassword,List.of("USER"));
userRepository.persist(user);
return Response.ok().build();
}
@GET
@Path("/login")
public Response login() {
return Response.ok().build();
}

@GET
@Path("/success")
@RolesAllowed("USER")
public Response success() {
return Response.ok("success"+ securityIdentity.getPrincipal().getName()).build();
}
}
```

Use code with caution.
Java
@Path 와 @GET과 같은 JAX-RS 어노테이션을 사용하여 HTTP 엔드포인트를 설정합니다.

@RolesAllowed 어노테이션으로 엔드포인트 접근 권한을 제어합니다.

회원가입시 bcrypt를 사용하여 패스워드를 암호화합니다.

2. Java EE (Jakarta EE) 버전

2.1. 프로젝트 설정:

Java EE 서버 (예: WildFly, Payara)를 사용합니다.

pom.xml (Maven) 또는 build.gradle (Gradle)에 다음 의존성을 추가합니다.

jakarta.persistence: JPA API

org.hibernate:hibernate-entitymanager: Hibernate JPA 구현체

org.postgresql:postgresql: PostgreSQL JDBC 드라이버

jakarta.security.enterprise: Jakarta Security API

<!-- Maven 예시 -->
<dependencies>
    <dependency>
        <groupId>jakarta.persistence</groupId>
        <artifactId>jakarta.persistence-api</artifactId>
        <version>3.1.0</version>
    </dependency>
    <dependency>
        <groupId>org.hibernate</groupId>
        <artifactId>hibernate-entitymanager</artifactId>
        <version>5.6.15.Final</version>
    </dependency>
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <version>42.5.4</version>
    </dependency>
    <dependency>
        <groupId>jakarta.security.enterprise</groupId>
        <artifactId>jakarta.security.enterprise-api</artifactId>
        <version>3.0.0</version>
    </dependency>
  <dependency>
    <groupId>jakarta.platform</groupId>
    <artifactId>jakarta.jakartaee-api</artifactId>
    <version>10.0.0</version>
    <scope>provided</scope>
  </dependency>
</dependencies>
Use code with caution.
Xml
2.2. 엔티티 클래스 (User):

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private List<String> roles;


    public User() {
    }

    public User(String username, String password, List<String> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
      return roles;
    }

    public void setRoles(List<String> roles) {
      this.roles = roles;
    }
}
Use code with caution.
Java
2.3. 리포지토리 클래스:

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Stateless
public class UserRepository {

    @PersistenceContext
    private EntityManager em;

    public Optional<User> findByUsername(String username) {
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class);
        query.setParameter("username", username);
        List<User> result = query.getResultList();
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    public void save(User user) {
        em.persist(user);
    }
}
Use code with caution.
Java
@Stateless: EJB를 선언하여 트랜잭션 관리를 수행할 수 있도록 합니다.

@PersistenceContext: 엔티티 매니저를 주입합니다.

2.4. 사용자 인증 클래스:
```
import jakarta.annotation.PostConstruct;
import jakarta.annotation.security.DeclareRoles;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.LoginToContinue;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import jakarta.security.enterprise.identitystore.IdentityStoreHandler;
import jakarta.security.enterprise.identitystore.PasswordCompare;
import jakarta.security.enterprise.identitystore.PasswordHash;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import java.util.HashSet;
import java.util.Set;

import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import java.util.stream.Collectors;

@LoginToContinue
@ApplicationScoped
@DeclareRoles({"USER","ADMIN"})
public class CustomIdentityStore implements IdentityStore {

    @Inject
    UserRepository userRepository;

    @Inject
    private PasswordHash passwordHash;

    @PostConstruct
    public void init(){
        Pbkdf2PasswordHash.Algorithm algorithm = Pbkdf2PasswordHash.Algorithm.PBKDF2WithHmacSHA512;
    }

    @Override
    public int priority() {
        return 10;
    }

    @Override
    public CredentialValidationResult validate(UsernamePasswordCredential usernamePasswordCredential) {
        String username = usernamePasswordCredential.getCaller();
        String password = usernamePasswordCredential.getPasswordAsString();

        return userRepository.findByUsername(username).map(user -> {
            if (passwordHash.verify(password.toCharArray(),user.getPassword())) {
                Set<String> roles = user.getRoles().stream().collect(Collectors.toSet());
                return new CredentialValidationResult(user.getUsername(), roles);
            } else {
                return CredentialValidationResult.INVALID_RESULT;
            }
        }).orElse(CredentialValidationResult.INVALID_RESULT);
    }
}
```
Use code with caution.
Java
IdentityStore 인터페이스를 구현하여 사용자 인증 로직을 정의합니다.

validate 메서드에서 사용자 이름과 비밀번호를 확인하고, 권한 정보를 반환합니다.

2.5. 보안 설정:

Java EE는 web.xml 또는 glassfish-web.xml (WildFly의 경우 jboss-web.xml)과 같은 deployment descriptor 파일을 사용하여 보안 설정을 구성합니다.

<!-- web.xml -->
<security-constraint>
    <web-resource-collection>
        <web-resource-name>Secured</web-resource-name>
        <url-pattern>/success</url-pattern>
    </web-resource-collection>
    <auth-constraint>
        <role-name>USER</role-name>
    </auth-constraint>
</security-constraint>

<login-config>
    <auth-method>FORM</auth-method>
    <form-login-config>
        <form-login-page>/login.html</form-login-page>
        <form-error-page>/error.html</form-error-page>
    </form-login-config>
</login-config>

<security-role>
    <role-name>USER</role-name>
</security-role>
Use code with caution.
Xml
2.6. 컨트롤러:

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.security.enterprise.identitystore.PasswordHash;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthController {

@Inject
UserRepository userRepository;

    @Inject
    private PasswordHash passwordHash;

    @Inject
    SecurityContext securityContext;

@POST
@Path("/signup")
@Transactional
public Response signup(@QueryParam("username") String username, @QueryParam("password") String password) {
// 비밀번호 암호화
String hashedPassword = passwordHash.generate(password.toCharArray());
User user = new User(username, hashedPassword, List.of("USER"));
userRepository.save(user);
return Response.ok().build();
}

    @GET
    @Path("/login")
    public Response login() {
        return Response.ok().build();
    }
    @GET
    @Path("/success")
    @RolesAllowed("USER")
    public Response success() {
        return Response.ok("success"+ securityContext.getCallerPrincipal().getName()).build();
    }
}
Use code with caution.
Java
3. 주요 차이점:

Quarkus:

개발 편의성에 중점을 둔 빠른 개발 프레임워크입니다.

Panache를 사용하여 코드를 간소화할 수 있습니다.

빌드 시에 필요한 컴포넌트만 선택하여 실행 파일을 생성합니다.

보안 설정은 application.properties 또는 application.yml에서 설정합니다.

보안 관련 편의 기능을 제공합니다.

Java EE:

오래된 기술로, 안정성이 높습니다.

표준 스펙에 따라 제공되는 기능을 사용합니다.

별도의 애플리케이션 서버가 필요합니다.

보안 설정은 deployment descriptor 파일 (web.xml)에서 합니다.

보안 기능을 사용하기 위해서는 추가 설정이 필요합니다.

4. 추가 고려 사항:

패스워드 해싱: 비밀번호를 안전하게 저장하기 위해 암호화해야 합니다. Quarkus는 기본적으로 제공하는 반면, Java EE는 PasswordHash와 같은 인터페이스를 사용해야 합니다.

역할 관리: 사용자 역할 및 권한 관리를 설계해야 합니다.

예외 처리: 각 환경에 맞는 예외 처리 로직을 구현해야 합니다.

테스트: 단위 테스트 및 통합 테스트를 통해 사용자 인증 기능을 검증해야 합니다.

결론:

두 환경 모두 엔티티 매니저를 사용하여 데이터베이스 접근을 처리하지만, 설정 및 개발 방식에 차이가 있습니다. Quarkus는 개발 편의성과 성능에 초점을 맞춘 반면, Java EE는 표준 스펙을 따르면서 안정성에 중점을 두고 있습니다. 프로젝트 요구 사항과 개발 팀의 선호도에 따라 적절한 환경을 선택할 수 있습니다.


----

import redis.clients.jedis.UnifiedJedis;
import redis.clients.jedis.DefaultJedisClientConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisClientConfig;

public class ConnectBasicExample {
public void run() {
JedisClientConfig config = DefaultJedisClientConfig.builder()
.user("default")
.password("*******")
.build();

        UnifiedJedis jedis = new UnifiedJedis(
            new HostAndPort("memcached-11075.c340.ap-northeast-2-1.ec2.redns.redis-cloud.com", 11075),
            config
        );

        String res1 = jedis.set("foo", "bar");
        System.out.println(res1); // >>> OK

        String res2 = jedis.get("foo");
        System.out.println(res2); // >>> bar

        jedis.close();
    }
}



```java
@Inject
EventBus bus;

@GET
@Path("/hello")
public Uni<String> hello(@QueryParam("name") String name) {     
return bus.<String>request("greetings", name)               
.onItem().transform(response -> response.body());   
}

```





bus.<반환타입>request( @ConsumeEvent("호출문자열"),String name)


> curl "http://localhost:8080/vertx/hello?queryt=bob"
> 
> @QueryParam("query") = name
>  
> String name = bob
> 
> 
> 
```java

package org.acme;

import io.quarkus.vertx.ConsumeEvent;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GreetingService {

    @ConsumeEvent("greetings")
    public String hello(String name) {
        return "Hello " + name;
    }
}

```










# code-with-quarkus

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: <https://quarkus.io/>.

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.

## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/code-with-quarkus-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult <https://quarkus.io/guides/maven-tooling>.

## Provided Code

### REST

Easily start your REST Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)
