package org.acme.ext.zerg.service;

import java.util.List;
import org.acme.ext.terran.model.TerranModel;
import org.acme.ext.zerg.entity.Hatchery;
import org.springframework.stereotype.Component;

@Component
public class ZergService
{

    private static Class clazz;

    private       TerranModel           Model;

    private final ZergDataAccess<Hatchery> zergFactory;


    public ZergService(ZergDataAccess<Hatchery> zergFactory)
    {

        this.zergFactory = zergFactory;
    }


    public List<Object> anyList(String select)
    {
        switch (select) {

            case "barracks":
                clazz = Model.BARRACKS.getClazz();
                return zergFactory.setClazz(clazz).listAll();

            case "command":

                return zergFactory.setClazz(clazz).listAll();

            default:
                return zergFactory.listAll();
        }
    }


}
