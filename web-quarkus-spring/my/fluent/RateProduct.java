package fluent;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.UUID;


@Entity
public class RateProduct implements Command {
    @Id
    private String id;
    private int  productId;
    private int  rating;
    private int  userId;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    private String productName;


    public RateProduct() {
        this.id = String.valueOf(UUID.randomUUID());
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "RateProduct{" + "id=" + id + ", productId=" + productName + ", rating=" + rating + ", userId=" + userId +
               '}';
    }
}
