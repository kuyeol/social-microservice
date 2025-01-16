package org.acme.repo;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.acme.object.MyEntity;

@ApplicationScoped
public class JpaRepository
{

    @Inject
    EntityManager entityManager;


    @Transactional
    public void persistRepo(MyEntity entity)
    {

        entity.setId("1");
        entity.setAge(30);
        entityManager.persist(entity);
    }


}







