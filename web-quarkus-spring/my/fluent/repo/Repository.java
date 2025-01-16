package fluent.repo;

import jakarta.persistence.Id;
import java.util.Optional;

public interface Repository<T, String>
{

    void save(T entity);

    void delete(T entity);

    void update(T entity);

    Class<T> getEntityClass();

    Optional<T> findById(String id);
}
