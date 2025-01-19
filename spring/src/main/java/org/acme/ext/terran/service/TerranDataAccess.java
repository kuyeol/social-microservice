package org.acme.ext.terran.service;

import jakarta.persistence.EntityManager;
import org.acme.core.database.DataAccess;
import org.springframework.stereotype.Component;

@Component
 class TerranDataAccess<T> extends DataAccess
{


    TerranDataAccess(EntityManager em)
    {
        super(em);
    }


}
