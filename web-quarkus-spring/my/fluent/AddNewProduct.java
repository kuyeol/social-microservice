package fluent;

import java.util.UUID;

public class AddNewProduct  extends AbstractCommand{

    private UUID id;


    public AddNewProduct() {
        this.id = UUID.randomUUID();
    }


    @Override
    public UUID getId() {
        return null;
    }
}
