package fluent.repo;

import fluent.object.Entity;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Id;
import java.util.Optional;

public class JpaRepository<T extends Entity, String> implements Repository<T, String>
{

    @Inject
    EntityManager em;

    private final Class<T> entityClass;


    public JpaRepository(Class<T> entityClass)
    {

        this.entityClass = entityClass;
    }


    @Override
    public void save(T entity)
    {

        em.persist(entity);
    }


    @Override
    public void delete(T entity)
    {

    }


    @Override
    public void update(T entity)
    {

    }


    @Override
    public Class<T> getEntityClass()
    {

        return entityClass;
    }


    @Override
    public Optional<T> findById(String id) {
        return Optional.ofNullable(em.find(entityClass, id));
    }


}
