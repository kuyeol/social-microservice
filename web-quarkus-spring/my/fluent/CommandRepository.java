package fluent;

public interface CommandRepository<T> {
    T find(int id);

    void save(T entity);
}