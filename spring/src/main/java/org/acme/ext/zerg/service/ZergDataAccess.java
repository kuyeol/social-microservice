package org.acme.ext.zerg.service;

import jakarta.persistence.EntityManager;
import java.util.List;
import org.acme.core.database.DataAccess;
import org.acme.ext.terran.model.TerranModel;
import org.springframework.stereotype.Component;

@Component
class ZergDataAccess<T> extends DataAccess
{
    private static Class       clazz;
    private    TerranModel Model;



    ZergDataAccess(EntityManager em)
    {
        super(em);
    }


    public List<Object> anyList(String select)
    {
        switch (select) {

            case "barracks":
                clazz = Model.BARRACKS.getClazz();
                return setClazz(clazz).listAll();

            case "command":

                return setClazz(clazz).listAll();

            default:
                return listAll();
        }
    }




}
