package fluent;

import java.util.List;

public interface ProductsDao {

    ProductDisplay findById(int productId);

    List<ProductDisplay> findByName(String name);

    List<ProductInventory> findOutOfStockProducts();

    List<ProductDisplay> findRelatedProducts(int productId);

}
