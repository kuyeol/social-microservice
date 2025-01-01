package fluent;

public interface CommandHandler<T extends AbstractCommand> {
    void handle(ConfirmItemShipped command);

    void handle(T command);

    void handle(AddNewProduct command);

    void handle(RateProduct command);

    void handle(AddToInventory command);

    void handle(UpdateStockFromInventoryRecount command);
}