package org.acme.repo;

import jakarta.persistence.EntityManager;
import java.util.Optional;

public interface Repository<T>
{

    void save(T entity);

    void delete(T entity);

    void update(T entity);

    Class<T> getEntityClass();

}
