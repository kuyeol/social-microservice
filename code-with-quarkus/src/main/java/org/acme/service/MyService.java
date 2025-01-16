package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.object.MyEntity;
import org.acme.object.dto.MyModel;

@ApplicationScoped
public class MyService
{




    public MyModel getModel(MyEntity entity)
    {

        return null;
    }

    //public YouModel getModel(YouEntity entity)
    //{
    //
    //    return YouRepo.find(entity.getId());
    //}
}
