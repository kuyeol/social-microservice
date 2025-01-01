

```java
import io.smallrye.mutiny.Uni;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.identity.request.Credential;

import java.security.Principal;
import java.security.Permission;
import java.util.Map;
import java.util.Set;

public class PostgresUserAuth implements SecurityIdentity {

    private final String username;
    private final Set<String> roles;
    private final Map<String, Object> attributes;

    public PostgresUserAuth(String username, Set<String> roles, Map<String, Object> attributes) {
        this.username = username;
        this.roles = roles;
        this.attributes = attributes;
    }

    @Override
    public Principal getPrincipal() {
        return () -> username;
    }

    @Override
    public <T extends Principal> T getPrincipal(Class<T> clazz) {
        if (clazz.isInstance(getPrincipal())) {
            return clazz.cast(getPrincipal());
        }
        return null;
    }

    @Override
    public boolean isAnonymous() {
        return username == null || username.isEmpty();
    }

    @Override
    public Set<String> getRoles() {
        return roles;
    }

    @Override
    public boolean hasRole(String role) {
        return roles.contains(role);
    }

    @Override
    public <T extends Credential> T getCredential(Class<T> aClass) {
        return null; // No credentials stored here
    }

    @Override
    public Set<Credential> getCredentials() {
        return Set.of();
    }

    @Override
    public <T> T getAttribute(String key) {
        return (T) attributes.get(key);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Uni<Boolean> checkPermission(Permission permission) {
        boolean isGranted = roles.contains("ADMIN") || roles.contains(permission.getName());
        return Uni.createFrom().item(isGranted);
    }

    @Override
    public boolean checkPermissionBlocking(Permission permission) {
        return roles.contains("ADMIN") || roles.contains(permission.getName());
    }

    public static PostgresUserAuth fromDatabase(String username, Set<String> roles, Map<String, Object> attributes) {
        // The roles and attributes are now parameterized
        return new PostgresUserAuth(username, roles, attributes);
    }
}

```

```java
// 사용자 엔티티
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String username;
    
    @Column(nullable = false)
    private String password;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();
    
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "user_attributes",
        joinColumns = @JoinColumn(name = "user_id")
    )
    @MapKeyColumn(name = "attribute_key")
    @Column(name = "attribute_value")
    private Map<String, String> attributes = new HashMap<>();
    
    // Getters and setters
}

// 역할 엔티티
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String name;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "role_permissions",
        joinColumns = @JoinColumn(name = "role_id"),
        inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions = new HashSet<>();
    
    // Getters and setters
}

// 권한 엔티티
@Entity
@Table(name = "permissions")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String actions;
    
    // Getters and setters
}

// JPA 기반 SecurityIdentity 구현
public class JpaSecurityIdentity implements SecurityIdentity {
    private final User user;
    private final EntityManager entityManager;
    private final Principal principal;
    private final Set<Credential> credentials;

    public JpaSecurityIdentity(User user, EntityManager entityManager) {
        this.user = user;
        this.entityManager = entityManager;
        this.principal = () -> user.getUsername();
        this.credentials = new HashSet<>(); // 필요한 경우 자격증명 추가
    }

    @Override
    public Principal getPrincipal() {
        return principal;
    }

    @Override
    public <T extends Principal> T getPrincipal(Class<T> principalClass) {
        if (principalClass.isInstance(principal)) {
            return principalClass.cast(principal);
        }
        return null;
    }

    @Override
    public boolean isAnonymous() {
        return user == null;
    }

    @Override
    public Set<String> getRoles() {
        return user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean hasRole(String roleName) {
        return user.getRoles().stream()
                .anyMatch(role -> role.getName().equals(roleName));
    }

    @Override
    public Set<Credential> getCredentials() {
        return Collections.unmodifiableSet(credentials);
    }

    @Override
    public <T extends Credential> T getCredential(Class<T> credentialClass) {
        return credentials.stream()
                .filter(credentialClass::isInstance)
                .map(credentialClass::cast)
                .findFirst()
                .orElse(null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getAttribute(String name) {
        return (T) user.getAttributes().get(name);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return new HashMap<>(user.getAttributes());
    }

    @Override
    public Uni<Boolean> checkPermission(java.security.Permission permission) {
        return Uni.createFrom().item(() -> {
            // 트랜잭션 내에서 권한 검증
            return entityManager.createQuery(
                "SELECT COUNT(p) > 0 FROM User u " +
                "JOIN u.roles r " +
                "JOIN r.permissions p " +
                "WHERE u.id = :userId " +
                "AND p.name = :permName " +
                "AND p.actions LIKE :permActions",
                Boolean.class)
                .setParameter("userId", user.getId())
                .setParameter("permName", permission.getName())
                .setParameter("permActions", "%" + permission.getActions() + "%")
                .getSingleResult();
        });
    }

    @Override
    public boolean checkPermissionBlocking(java.security.Permission permission) {
        return checkPermission(permission).await().indefinitely();
    }

    // Repository 클래스
    @ApplicationScoped
    public static class SecurityRepository {
        @Inject
        EntityManager em;

        public JpaSecurityIdentity createSecurityIdentity(String username) {
            User user = em.createQuery(
                "SELECT u FROM User u " +
                "LEFT JOIN FETCH u.roles r " +
                "LEFT JOIN FETCH r.permissions " +
                "WHERE u.username = :username",
                User.class)
                .setParameter("username", username)
                .getSingleResult();
            
            return new JpaSecurityIdentity(user, em);
        }
    }
}
```