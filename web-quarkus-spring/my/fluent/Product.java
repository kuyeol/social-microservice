package fluent;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Product {

    @Id
    private int id;
    // ... other fields ...

    public void rateProduct(int userId, int rating) {
        // ... rating 로직 구현 ...
    }

    // ... getter and setter ...
}