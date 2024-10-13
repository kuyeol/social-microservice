JPA에서 `protected` 접근 제어자를 사용하는 방법을 구체적인 예제를 통해 설명하겠습니다. 이를 통해 JPA 엔터티의 설계에 있어 `protected`가 사용되는 이유와 그 효과를 더 잘 이해할 수 있습니다.

### 1. **`protected` 생성자**
JPA는 엔터티 클래스를 인스턴스화할 때 기본 생성자를 필요로 합니다. 하지만 이 기본 생성자는 외부에서 직접 호출되지 않도록 `protected`로 제한할 수 있습니다.

#### 예제: `User` 엔터티 클래스
```java
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {

    @Id
    private Long id;

    private String username;

    // JPA에서 사용하기 위한 기본 생성자, 외부에서는 직접 사용하지 않도록 protected로 선언
    protected User() {
    }

    // 외부에서 사용할 수 있는 생성자
    public User(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
```

- **`protected User()`**: JPA에서 엔터티를 조회하거나 저장할 때 기본 생성자를 통해 객체를 생성하지만, 이 기본 생성자는 외부에서 직접 호출될 필요가 없습니다. 따라서 이를 `protected`로 선언하여 외부 접근을 제한합니다.
- **`public User(String username)`**: 이 생성자는 외부에서 인스턴스를 만들 때 사용합니다.

### 2. **`protected` Setter**
엔터티의 일부 필드는 외부에서 직접 수정되면 안 되는 경우가 있습니다. 하지만 JPA에서는 데이터를 로드하거나 저장할 때 필드 값을 설정해야 하므로 `protected`나 `package-private` 수준의 setter 메서드가 유용할 수 있습니다.

#### 예제: `Product` 엔터티 클래스
```java
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Product {

    @Id
    private Long id;

    private String name;

    protected Product() {
        // JPA에서 사용되는 기본 생성자
    }

    public Product(String name) {
        this.name = name;
    }

    // 외부에서 직접 호출되지 않도록 protected로 선언
    protected void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
```

- **`protected void setName(String name)`**: 이 메서드는 외부에서 직접 호출되지 않으며, JPA에서 엔터티의 값을 설정할 때만 사용될 수 있습니다. 이를 통해 잘못된 값 설정을 방지할 수 있습니다.

### 3. **`protected` 라이프사이클 콜백 메서드**
JPA는 엔터티가 특정 상태에 도달했을 때 자동으로 호출되는 콜백 메서드를 지원합니다. 이때 이러한 메서드들이 외부에 공개될 필요는 없으므로 `protected`로 선언할 수 있습니다.

#### 예제: `Order` 엔터티 클래스
```java
import javax.persistence.*;
import java.time.Instant;

@Entity
public class Order {

    @Id
    private Long id;

    private Instant orderDate;

    protected Order() {
    }

    public Order(Instant orderDate) {
        this.orderDate = orderDate;
    }

    public Long getId() {
        return id;
    }

    public Instant getOrderDate() {
        return orderDate;
    }

    // JPA에서 엔터티가 저장되기 전에 호출되는 메서드
    @PrePersist
    protected void onPrePersist() {
        this.orderDate = Instant.now();
    }

    // 외부에서 호출할 수 있는 메서드
    public void placeOrder() {
        this.orderDate = Instant.now();
    }
}
```

- **`@PrePersist`**: `@PrePersist`는 엔터티가 데이터베이스에 저장되기 전에 JPA에 의해 자동으로 호출됩니다. `onPrePersist()` 메서드는 `protected`로 선언하여 JPA에서만 호출되도록 하고, 외부에서는 직접 호출할 수 없도록 합니다.
- **`placeOrder()`**: 사용자가 직접 주문을 배치할 때는 이 메서드를 사용할 수 있습니다.

### 4. **모든 예제를 포함한 전체 코드**

```java
import javax.persistence.*;
import java.time.Instant;

@Entity
public class User {

    @Id
    private Long id;
    private String username;

    // JPA에서 사용되는 기본 생성자
    protected User() {
    }

    // 외부에서 인스턴스를 만들 때 사용할 수 있는 생성자
    public User(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

@Entity
public class Product {

    @Id
    private Long id;
    private String name;

    // JPA 기본 생성자
    protected Product() {
    }

    // 외부에서 사용할 수 있는 생성자
    public Product(String name) {
        this.name = name;
    }

    // JPA가 필드 값을 설정할 수 있도록 protected로 선언
    protected void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

@Entity
public class Order {

    @Id
    private Long id;
    private Instant orderDate;

    // JPA에서 사용할 기본 생성자
    protected Order() {
    }

    public Order(Instant orderDate) {
        this.orderDate = orderDate;
    }

    public Long getId() {
        return id;
    }

    public Instant getOrderDate() {
        return orderDate;
    }

    // 엔터티가 저장되기 전에 JPA에 의해 호출되는 콜백 메서드
    @PrePersist
    protected void onPrePersist() {
        this.orderDate = Instant.now();
    }

    // 주문을 배치할 때 사용할 수 있는 메서드
    public void placeOrder() {
        this.orderDate = Instant.now();
    }
}
```

### 요약:
- **`protected` 생성자**: 외부에서 직접적으로 객체를 인스턴스화하지 않도록 막으면서 JPA는 이를 통해 객체를 생성할 수 있게 함.
- **`protected` setter**: 엔터티의 필드 값을 외부에서 임의로 수정하지 못하게 하고 JPA에서는 필드 값을 설정할 수 있도록 함.
- **`protected` 라이프사이클 콜백 메서드**: 엔터티의 특정 이벤트가 발생할 때 자동으로 호출되지만, 외부에서는 호출되지 않도록 보호.

이러한 설계는 JPA가 내부적으로 엔터티를 관리할 수 있게 하면서도, 외부에서는 불필요한 접근을 제한하는 데 유용합니다.


---
---


JPA에서 `protected` 접근 제어자를 사용하는 이유와 방식에 대해 좀 더 자세한 예제를 통해 설명하겠습니다. `protected`는 주로 JPA 엔티티 클래스에서 생성자 및 setter 메서드, 생명주기 콜백 메서드에서 사용되며, 외부에서의 직접적인 접근을 제한하면서도 JPA가 내부적으로 엔티티를 다룰 수 있도록 합니다.

### 1. **`protected` 생성자**
JPA는 기본 생성자를 필요로 합니다. 하지만 이 기본 생성자가 외부에서 호출되지 않도록 `protected`로 선언하는 것이 일반적입니다. 이렇게 하면 JPA는 리플렉션을 통해 생성자를 사용할 수 있지만, 외부에서 직접 인스턴스를 생성하는 것은 제한할 수 있습니다.

#### 예제: `User` 엔티티 클래스
```java
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    // JPA가 사용하기 위한 기본 생성자
    // 외부에서는 호출할 수 없도록 protected로 선언
    protected User() {
    }

    // 명시적인 생성자: 필수 필드를 받는 생성자
    public User(String username) {
        this.username = username;
    }

    // Getter & Setter
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
```

#### 설명:
- `protected User()`는 JPA가 리플렉션을 통해 엔티티를 로드할 때 사용되며, 외부에서는 직접 사용할 수 없습니다.
- 이 방식은 엔티티의 인스턴스 생성을 제어하고, 엔티티의 일관성을 유지하는 데 도움이 됩니다.

### 2. **`protected` setter 메서드**
일부 필드는 JPA를 통해 데이터베이스에서 값을 로드해야 하지만, 외부에서 값을 변경하는 것은 막고 싶을 수 있습니다. 이 경우 `protected` setter 메서드를 사용하여 필드 변경을 제한할 수 있습니다.

#### 예제: `Product` 엔티티 클래스
```java
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private double price;

    // JPA에서 사용하는 기본 생성자
    protected Product() {
    }

    // 명시적인 생성자: 필수 필드를 받는 생성자
    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    // Getter & Setter
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    // 외부에서 호출하지 못하도록 protected로 설정
    // JPA에서만 호출할 수 있음
    protected void setName(String name) {
        this.name = name;
    }

    protected void setPrice(double price) {
        this.price = price;
    }
}
```

#### 설명:
- `setName()`과 `setPrice()` 메서드를 `protected`로 설정하면 외부에서 값을 변경할 수 없고, JPA가 내부적으로 엔티티 데이터를 불러올 때만 값을 설정할 수 있습니다.
- 이렇게 하면 데이터를 보다 안전하게 보호할 수 있으며, 외부에서 임의로 값을 변경하지 못하도록 막을 수 있습니다.

### 3. **`protected` 생명주기 콜백 메서드**
JPA는 엔티티의 특정 이벤트(예: 저장되기 전, 수정되기 전 등)에 맞춰 콜백 메서드를 호출할 수 있습니다. 이 콜백 메서드들은 외부에서 직접 호출될 필요가 없으므로 `protected`로 선언하는 것이 일반적입니다.

#### 예제: `Order` 엔티티 클래스
```java
import javax.persistence.*;
import java.time.Instant;

@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Instant orderDate;

    // JPA에서 사용하는 기본 생성자
    protected Order() {
    }

    public Order(Instant orderDate) {
        this.orderDate = orderDate;
    }

    // Getter
    public Long getId() {
        return id;
    }

    public Instant getOrderDate() {
        return orderDate;
    }

    // 엔티티가 저장되기 전 호출되는 콜백 메서드
    @PrePersist
    protected void onPrePersist() {
        this.orderDate = Instant.now();  // 주문 생성 시간을 현재 시간으로 설정
    }
}
```

#### 설명:
- `onPrePersist()` 메서드는 `@PrePersist`로 지정된 JPA 생명주기 콜백 메서드입니다.
- `protected`로 선언되어 외부에서 호출할 수 없으며, JPA가 엔티티를 저장하기 전에만 자동으로 호출됩니다.

### 4. **`protected`로 변경된 필드 접근 제어**
일부 필드 자체를 `protected`로 선언하여 외부 접근을 제한할 수 있습니다. 이를 통해 상속된 클래스는 필드에 접근할 수 있지만, 외부에서는 필드에 직접 접근할 수 없습니다.

#### 예제: `Person` 엔티티 클래스
```java
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Person {

    @Id
    private Long id;

    // protected 필드: 상속 클래스는 접근 가능하지만 외부 클래스는 불가능
    protected String name;

    protected Person() {
    }

    public Person(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    protected String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }
}
```

### 요약:
- **`protected` 생성자**는 외부에서의 직접적인 인스턴스 생성을 막고 JPA가 엔티티를 생성할 수 있도록 합니다.
- **`protected` setter 메서드**는 외부에서 임의로 값을 변경하지 못하게 하며, JPA에서만 값을 설정할 수 있습니다.
- **`protected` 생명주기 콜백 메서드**는 엔티티의 특정 이벤트에서만 자동으로 호출되도록 합니다.
- **필드를 `protected`로 선언**하면 상속받은 클래스에서만 접근 가능하고, 외부에서는 접근을 제한할 수 있습니다.

이러한 패턴은 엔티티의 데이터를 안전하게 보호하면서도 JPA에서 필요한 경우에만 데이터 변경을 허용하는 데 유용합니다.
