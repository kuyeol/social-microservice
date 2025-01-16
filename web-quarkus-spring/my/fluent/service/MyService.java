package fluent.service;

import fluent.bridge.RepositoryConnect;
import fluent.object.MyEntity;
import fluent.object.YouEntity;
import fluent.object.dto.MyModel;
import fluent.object.dto.YouModel;
import jakarta.inject.Inject;

public class MyService
{

    @Inject
    RepositoryConnect<MyModel> repo; // Inject with specific types


    @Inject
    RepositoryConnect<YouModel> YouRepo;

    public MyModel getModel(MyEntity entity)
    {

        return repo.find(entity.getId());
    }


    public YouModel getModel(YouEntity entity)
    {
        return YouRepo.find(entity.getId());
    }


}
