package fluent;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Product implements Command{

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Id
    private String id;
    // ... other fields ...

    public void rateProduct(int userId, int rating) {
        // ... rating 로직 구현 ...
    }

    @Override
    public String toString() {
        return "Product{" + "id='" + id + '\'' + '}';
    }


    // ... getter and setter ...
}