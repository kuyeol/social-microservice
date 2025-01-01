package fluent;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class ProductsCommandHandler  implements CommandHandler<AbstractCommand>{


    @PersistenceContext
    private EntityManager em;



    @Override
    public void handle(AddNewProduct command) {
        // ...
    }

    @Override
    public void handle(RateProduct command) {
        Product product = em.find(Product.class, command.getProductId()); // JPA를 사용하여 Product 조회
        if (product != null) {
            product.rateProduct(command.getUserId(), command.getRating());
            em.merge(product); // 변경 사항 저장
        }
    }


    @Override
    public void handle(AddToInventory command) {
        // ...
    }


    @Override
    public void handle(ConfirmItemShipped command) {
        // ...
    }

    @Override
    public void handle(AbstractCommand command) {

    }


    @Override
    public void handle(UpdateStockFromInventoryRecount command) {

        System.out.println(command);
        // ...
    }



}
