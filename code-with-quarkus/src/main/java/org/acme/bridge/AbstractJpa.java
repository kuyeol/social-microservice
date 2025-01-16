package org.acme.bridge;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import org.acme.util.GenerateBridge;
import org.acme.util.JpaSort;
import org.acme.util.Parameters;
import org.hibernate.Session;

import static org.acme.bridge.AbstractJpaOperations.implementationInjectionMissing;
import static org.acme.bridge.JpaOperater.INSTANCE;

public interface AbstractJpa<Entity, Id>
{

    // Operations

    /**
     * Returns the {@link EntityManager} for the <Entity> entity class for extra operations (eg.
     * CriteriaQueries)
     *
     * @return the {@link EntityManager} for the <Entity> entity class
     */
    @GenerateBridge
    default EntityManager getEntityManager()
    {

        throw implementationInjectionMissing();
    }
    
    /**
     * Returns the {@link Session} for the <Entity> entity class for extra operations (eg.
     * CriteriaQueries)
     *
     * @return the {@link Session} for the <Entity> entity class
     */
    @GenerateBridge
    default Session getSession()
    {

        throw implementationInjectionMissing();
    }

    /**
     * Persist the given entity in the database, if not already persisted.
     *
     * @param entity the entity to persist.
     *
     * @see #isPersistent(Object)
     * @see #persist(Iterable)
     * @see #persist(Stream)
     * @see #persist(Object, Object...)
     */
    default void persist(Entity entity)
    {

        INSTANCE.persist(entity);
    }

    /**
     * Persist the given entity in the database, if not already persisted. Then flushes all pending
     * changes to the database.
     *
     * @param entity the entity to persist.
     *
     * @see #isPersistent(Object)
     * @see #persist(Iterable)
     * @see #persist(Stream)
     * @see #persist(Object, Object...)
     */
    default void persistAndFlush(Entity entity)
    {

        INSTANCE.persist(entity);
        INSTANCE.flush(entity);
    }

    /**
     * Delete the given entity from the database, if it is already persisted.
     *
     * @param entity the entity to delete.
     *
     * @see #isPersistent(Object)
     * @see #delete(String, Object...)
     * @see #delete(String, Map)
     * @see #delete(String, Parameters)
     * @see #deleteAll()
     */
    default void delete(Entity entity)
    {

        INSTANCE.delete(entity);
    }

    /**
     * Returns true if the given entity is persistent in the database. If yes, all modifications to
     * its persistent fields will be automatically committed to the database at transaction commit
     * time.
     *
     * @param entity the entity to check
     *
     * @return true if the entity is persistent in the database.
     */
    default boolean isPersistent(Entity entity)
    {

        return INSTANCE.isPersistent(entity);
    }

    /**
     * Flushes all pending changes to the database using the EntityManager for the <Entity> entity
     * class.
     */
    default void flush()
    {

        getSession().flush();
    }

    // Queries

    /**
     * Find an entity of this type by ID.
     *
     * @param id the ID of the entity to find.
     *
     * @return the entity found, or <code>null</code> if not found.
     */
    @GenerateBridge(targetReturnTypeErased = true)
    default Entity findById(Id id)
    {

        throw INSTANCE.implementationInjectionMissing();
    }

    /**
     * Find an entity of this type by ID and lock it.
     *
     * @param id the ID of the entity to find.
     * @param lockModeType the locking strategy to be used when retrieving the entity.
     *
     * @return the entity found, or <code>null</code> if not found.
     */
    @GenerateBridge(targetReturnTypeErased = true)
    default Entity findById(Id id, LockModeType lockModeType)
    {

        throw INSTANCE.implementationInjectionMissing();
    }

    /**
     * Find an entity of this type by ID.
     *
     * @param id the ID of the entity to find.
     *
     * @return if found, an optional containing the entity, else <code>Optional.empty()</code>.
     */
    @GenerateBridge
    default Optional<Entity> findByIdOptional(Id id)
    {

        throw INSTANCE.implementationInjectionMissing();
    }

    /**
     * Find an entity of this type by ID.
     *
     * @param id the ID of the entity to find.
     *
     * @return if found, an optional containing the entity, else <code>Optional.empty()</code>.
     */
    @GenerateBridge
    default Optional<Entity> findByIdOptional(Id id, LockModeType lockModeType)
    {

        throw INSTANCE.implementationInjectionMissing();
    }

    /**
     * Find entities using a query, with optional indexed parameters.
     *
     * @param params optional sequence of indexed parameters
     *
     * @return a new {@link UngQuery} instance for the given query
     *
     * @see #find(String, JpaSort, Object...)
     * @see #find(String, Map)
     * @see #find(String, Parameters)
     * @see #list(String, Object...)
     * @see #stream(String, Object...)
     */
    @GenerateBridge
    default UngQuery<Entity> find(String query, Object... params)
    {

        throw INSTANCE.implementationInjectionMissing();
    }

    /**
     * Find entities using a query and the given sort options, with optional indexed parameters.
     *
     * @param sort the sort strategy to use
     * @param params optional sequence of indexed parameters
     *
     * @return a new {@link UngQuery} instance for the given query
     *
     * @see #find(String, Object...)
     * @see #find(String, JpaSort, Map)
     * @see #find(String, JpaSort, Parameters)
     * @see #list(String, JpaSort, Object...)
     * @see #stream(String, JpaSort, Object...)
     */
    @GenerateBridge
    default UngQuery<Entity> find(String query, JpaSort sort, Object... params)
    {

        throw INSTANCE.implementationInjectionMissing();
    }

    /**
     * Find entities using a query, with named parameters.
     *
     * @param params {@link Map} of named parameters
     *
     * @return a new {@link UngQuery} instance for the given query
     *
     * @see #find(String, JpaSort, Map)
     * @see #find(String, Object...)
     * @see #find(String, Parameters)
     * @see #list(String, Map)
     * @see #stream(String, Map)
     */
    @GenerateBridge
    default UngQuery<Entity> find(String query, Map<String, Object> params)
    {

        throw INSTANCE.implementationInjectionMissing();
    }

    /**
     * Find entities using a query and the given sort options, with named parameters.
     *
     * @param sort the sort strategy to use
     * @param params {@link Map} of indexed parameters
     *
     * @return a new {@link UngQuery} instance for the given query
     *
     * @see #find(String, Map)
     * @see #find(String, JpaSort, Object...)
     * @see #find(String, JpaSort, Parameters)
     * @see #list(String, JpaSort, Map)
     * @see #stream(String, JpaSort, Map)
     */
    @GenerateBridge
    default UngQuery<Entity> find(String query, JpaSort sort, Map<String, Object> params)
    {

        throw INSTANCE.implementationInjectionMissing();
    }

    /**
     * Find entities using a query, with named parameters.
     *
     * @param params {@link Parameters} of named parameters
     *
     * @return a new {@link UngQuery} instance for the given query
     *
     * @see #find(String, JpaSort, Parameters)
     * @see #find(String, Map)
     * @see #find(String, Parameters)
     * @see #list(String, Parameters)
     * @see #stream(String, Parameters)
     */
    @GenerateBridge
    default UngQuery<Entity> find(String query, Parameters params)
    {

        throw INSTANCE.implementationInjectionMissing();
    }

    /**
     * Find entities using a query and the given sort options, with named parameters.
     *

     * @param sort the sort strategy to use
     * @param params {@link Parameters} of indexed parameters
     *
     * @return a new {@link UngQuery} instance for the given query
     *
     * @see #find(String, Parameters)
     * @see #find(String, JpaSort, Map)
     * @see #find(String, JpaSort, Parameters)
     * @see #list(String, JpaSort, Parameters)
     * @see #stream(String, JpaSort, Parameters)
     */
    @GenerateBridge
    default UngQuery<Entity> find(String query, JpaSort sort, Parameters params)
    {

        throw INSTANCE.implementationInjectionMissing();
    }

    /**
     * Find all entities of this type.
     *
     * @return a new {@link UngQuery} instance to find all entities of this type.
     *
     * @see #findAll(JpaSort)
     * @see #listAll()
     * @see #streamAll()
     */
    @GenerateBridge
    default UngQuery<Entity> findAll()
    {

        throw INSTANCE.implementationInjectionMissing();
    }

    /**
     * Find all entities of this type, in the given order.
     *
     * @param sort the sort order to use
     *
     * @return a new {@link UngQuery} instance to find all entities of this type.
     *
     * @see #findAll()
     * @see #listAll(JpaSort)
     * @see #streamAll(JpaSort)
     */
    @GenerateBridge
    default UngQuery<Entity> findAll(JpaSort sort)
    {

        throw INSTANCE.implementationInjectionMissing();
    }

  
    @GenerateBridge
    default List<Entity> list(String query, Object... params)
    {

        throw INSTANCE.implementationInjectionMissing();
    }


    @GenerateBridge
    default List<Entity> list(String query, JpaSort sort, Object... params)
    {

        throw INSTANCE.implementationInjectionMissing();
    }

   
    @GenerateBridge
    default List<Entity> list(String query, Map<String, Object> params)
    {

        throw INSTANCE.implementationInjectionMissing();
    }

    
    @GenerateBridge
    default List<Entity> list(String query, JpaSort sort, Map<String, Object> params)
    {

        throw INSTANCE.implementationInjectionMissing();
    }

    /**
     * Find entities matching a query, with named parameters. This method is a shortcut for
     * <code>find(query, params).list()</code>.
     *
     * @param params {@link Parameters} of named parameters
     *
     * @return a {@link List} containing all results, without paging
     *
     * @see #list(String, JpaSort, Parameters)
     * @see #list(String, Object...)
     * @see #list(String, Map)
     * @see #find(String, Parameters)
     * @see #stream(String, Parameters)
     */
    @GenerateBridge
    default List<Entity> list(String query, Parameters params)
    {

        throw INSTANCE.implementationInjectionMissing();
    }

    /**
     * Find entities matching a query and the given sort options, with named parameters. This method
     * is a shortcut for <code>find(query, sort, params).list()</code>.
     *
     * @param sort the sort strategy to use
     * @param params {@link Parameters} of indexed parameters
     *
     * @return a {@link List} containing all results, without paging
     *
     * @see #list(String, Parameters)
     * @see #list(String, JpaSort, Object...)
     * @see #list(String, JpaSort, Map)
     * @see #find(String, JpaSort, Parameters)
     * @see #stream(String, JpaSort, Parameters)
     */
    @GenerateBridge
    default List<Entity> list(String query, JpaSort sort, Parameters params)
    {

        throw INSTANCE.implementationInjectionMissing();
    }

    /**
     * Find all entities of this type. This method is a shortcut for <code>findAll().list()</code>.
     *
     * @return a {@link List} containing all results, without paging
     *
     * @see #listAll(JpaSort)
     * @see #findAll()
     * @see #streamAll()
     */
    @GenerateBridge
    default List<Entity> listAll()
    {

        throw INSTANCE.implementationInjectionMissing();
    }

    /**
     * Find all entities of this type, in the given order. This method is a shortcut for
     * <code>findAll(sort).list()</code>.
     *
     * @param sort the sort order to use
     *
     * @return a {@link List} containing all results, without paging
     *
     * @see #listAll()
     * @see #findAll(JpaSort)
     * @see #streamAll(JpaSort)
     */
    @GenerateBridge
    default List<Entity> listAll(JpaSort sort)
    {

        throw INSTANCE.implementationInjectionMissing();
    }

    /**
     * Find entities matching a query, with optional indexed parameters. This method is a shortcut
     * for <code>find(query, params).stream()</code>. It requires a transaction to work. Without a
     * transaction, the underlying cursor can be closed before the end of the stream.
     *
     * @param params optional sequence of indexed parameters
     *
     * @return a {@link Stream} containing all results, without paging
     *
     * @see #stream(String, JpaSort, Object...)
     * @see #stream(String, Map)
     * @see #stream(String, Parameters)
     * @see #find(String, Object...)
     * @see #list(String, Object...)
     */
    @GenerateBridge
    default Stream<Entity> stream(String query, Object... params)
    {

        throw INSTANCE.implementationInjectionMissing();
    }

    /**
     * Find entities matching a query and the given sort options, with optional indexed parameters.
     * This method is a shortcut for <code>find(query, sort, params).stream()</code>. It requires a
     * transaction to work. Without a transaction, the underlying cursor can be closed before the
     * end of the stream.
     *
     * @param params optional sequence of indexed parameters
     *
     * @return a {@link Stream} containing all results, without paging
     *
     * @see #stream(String, Object...)
     * @see #stream(String, JpaSort, Map)
     * @see #stream(String, JpaSort, Parameters)
     * @see #find(String, JpaSort, Object...)
     * @see #list(String, JpaSort, Object...)
     */
    @GenerateBridge
    default Stream<Entity> stream(String query, JpaSort sort, Object... params)
    {

        throw INSTANCE.implementationInjectionMissing();
    }

    /**
     * Find entities matching a query, with named parameters. This method is a shortcut for
     * <code>find(query, params).stream()</code>. It requires a transaction to work. Without a
     * transaction, the underlying cursor can be closed before the end of the stream.
     *
     * @param params {@link Map} of named parameters
     *
     * @return a {@link Stream} containing all results, without paging
     *
     * @see #stream(String, JpaSort, Map)
     * @see #stream(String, Object...)
     * @see #stream(String, Parameters)
     * @see #find(String, Map)
     * @see #list(String, Map)
     */
    @GenerateBridge
    default Stream<Entity> stream(String query, Map<String, Object> params)
    {

        throw INSTANCE.implementationInjectionMissing();
    }

    /**
     * Find entities matching a query and the given sort options, with named parameters. This method
     * is a shortcut for <code>find(query, sort, params).stream()</code>. It requires a transaction
     * to work. Without a transaction, the underlying cursor can be closed before the end of the
     * stream.
     *
     * @param sort the sort strategy to use
     * @param params {@link Map} of indexed parameters
     *
     * @return a {@link Stream} containing all results, without paging
     *
     * @see #stream(String, Map)
     * @see #stream(String, JpaSort, Object...)
     * @see #stream(String, JpaSort, Parameters)
     * @see #find(String, JpaSort, Map)
     * @see #list(String, JpaSort, Map)
     */
    @GenerateBridge
    default Stream<Entity> stream(String query, JpaSort sort, Map<String, Object> params)
    {

        throw INSTANCE.implementationInjectionMissing();
    }

    /**
     * Find entities matching a query, with named parameters. This method is a shortcut for
     * <code>find(query, params).stream()</code>. It requires a transaction to work. Without a
     * transaction, the underlying cursor can be closed before the end of the stream.
     *
     * @param params {@link Parameters} of named parameters
     *
     * @return a {@link Stream} containing all results, without paging
     *
     * @see #stream(String, JpaSort, Parameters)
     * @see #stream(String, Object...)
     * @see #stream(String, Map)
     * @see #find(String, Parameters)
     * @see #list(String, Parameters)
     */
    @GenerateBridge
    default Stream<Entity> stream(String query, Parameters params)
    {

        throw INSTANCE.implementationInjectionMissing();
    }

    /**
     * Find entities matching a query and the given sort options, with named parameters. This method
     * is a shortcut for <code>find(query, sort, params).stream()</code>. It requires a transaction
     * to work. Without a transaction, the underlying cursor can be closed before the end of the
     * stream.
     *
     * @param sort the sort strategy to use
     * @param params {@link Parameters} of indexed parameters
     *
     * @return a {@link Stream} containing all results, without paging
     *
     * @see #stream(String, Parameters)
     * @see #stream(String, JpaSort, Object...)
     * @see #stream(String, JpaSort, Map)
     * @see #find(String, JpaSort, Parameters)
     * @see #list(String, JpaSort, Parameters)
     */
    @GenerateBridge
    default Stream<Entity> stream(String query, JpaSort sort, Parameters params)
    {

        throw INSTANCE.implementationInjectionMissing();
    }

    /**
     * Find all entities of this type. This method is a shortcut for
     * <code>findAll().stream()</code>. It requires a transaction to work. Without a transaction,
     * the underlying cursor can be closed before the end of the stream.
     *
     * @return a {@link Stream} containing all results, without paging
     *
     * @see #streamAll(JpaSort)
     * @see #findAll()
     * @see #listAll()
     */
    @GenerateBridge
    default Stream<Entity> streamAll(JpaSort sort)
    {

        throw INSTANCE.implementationInjectionMissing();
    }

    /**
     * Find all entities of this type, in the given order. This method is a shortcut for
     * <code>findAll().stream()</code>. It requires a transaction to work. Without a transaction,
     * the underlying cursor can be closed before the end of the stream.
     *
     * @return a {@link Stream} containing all results, without paging
     *
     * @see #streamAll()
     * @see #findAll(JpaSort)
     * @see #listAll(JpaSort)
     */
    @GenerateBridge
    default Stream<Entity> streamAll()
    {

        throw INSTANCE.implementationInjectionMissing();
    }

    /**
     * Counts the number of this type of entity in the database.
     *
     * @return the number of this type of entity in the database.
     *
     * @see #count(String, Object...)
     * @see #count(String, Map)
     * @see #count(String, Parameters)
     */
    @GenerateBridge
    default long count()
    {

        throw INSTANCE.implementationInjectionMissing();
    }

    /**
     * Counts the number of this type of entity matching the given query, with optional indexed
     * parameters.
     *
     * @param params optional sequence of indexed parameters
     *
     * @return the number of entities counted.
     *
     * @see #count()
     * @see #count(String, Map)
     * @see #count(String, Parameters)
     */
    @GenerateBridge
    default long count(String query, Object... params)
    {

        throw INSTANCE.implementationInjectionMissing();
    }

    /**
     * Counts the number of this type of entity matching the given query, with named parameters.
     *
     * @param params {@link Map} of named parameters
     *
     * @return the number of entities counted.
     *
     * @see #count()
     * @see #count(String, Object...)
     * @see #count(String, Parameters)
     */
    @GenerateBridge
    default long count(String query, Map<String, Object> params)
    {

        throw INSTANCE.implementationInjectionMissing();
    }

    /**
     * Counts the number of this type of entity matching the given query, with named parameters.
     *
     * @param params {@link Parameters} of named parameters
     *
     * @return the number of entities counted.
     *
     * @see #count()
     * @see #count(String, Object...)
     * @see #count(String, Map)
     */
    @GenerateBridge
    default long count(String query, Parameters params)
    {

        throw INSTANCE.implementationInjectionMissing();
    }

    /**
     * Delete all entities of this type from the database.
     * <p>
     * WARNING: the default implementation of this method uses a bulk delete query and ignores
     * cascading rules from the JPA model.
     *
     * @return the number of entities deleted.
     *
     * @see #delete(String, Object...)
     * @see #delete(String, Map)
     * @see #delete(String, Parameters)
     */
    @GenerateBridge
    default long deleteAll()
    {

        throw INSTANCE.implementationInjectionMissing();
    }

    /**
     * Delete an entity of this type by ID.
     *
     * @param id the ID of the entity to delete.
     *
     * @return false if the entity was not deleted (not found).
     */
    @GenerateBridge
    default boolean deleteById(Id id)
    {

        throw INSTANCE.implementationInjectionMissing();
    }

    /**
     * Delete all entities of this type matching the given query, with optional indexed parameters.
     * <p>
     * WARNING: the default implementation of this method uses a bulk delete query and ignores
     * cascading rules from the JPA model.
     *
     * @param params optional sequence of indexed parameters
     *
     * @return the number of entities deleted.
     *
     * @see #deleteAll()
     * @see #delete(String, Map)
     * @see #delete(String, Parameters)
     */
    @GenerateBridge
    default long delete(String query, Object... params)
    {

        throw INSTANCE.implementationInjectionMissing();
    }

    /**
     * Delete all entities of this type matching the given query, with named parameters.
     * <p>
     * WARNING: the default implementation of this method uses a bulk delete query and ignores
     * cascading rules from the JPA model.
     *
     * @param params {@link Map} of named parameters
     *
     * @return the number of entities deleted.
     *
     * @see #deleteAll()
     * @see #delete(String, Object...)
     * @see #delete(String, Parameters)
     */
    @GenerateBridge
    default long delete(String query, Map<String, Object> params)
    {

        throw INSTANCE.implementationInjectionMissing();
    }

    /**
     * Delete all entities of this type matching the given query, with named parameters.
     * <p>
     * WARNING: the default implementation of this method uses a bulk delete query and ignores
     * cascading rules from the JPA model.
     *
     * @param params {@link Parameters} of named parameters
     *
     * @return the number of entities deleted.
     *
     * @see #deleteAll()
     * @see #delete(String, Object...)
     * @see #delete(String, Map)
     */
    @GenerateBridge
    default long delete(String query, Parameters params)
    {

        throw INSTANCE.implementationInjectionMissing();
    }

    /**
     * Persist all given entities.
     *
     * @param entities the entities to persist
     *
     * @see #persist(Object)
     * @see #persist(Stream)
     * @see #persist(Object, Object...)
     */
    default void persist(Iterable<Entity> entities)
    {

        INSTANCE.persist(entities);
    }

    /**
     * Persist all given entities.
     *
     * @param entities the entities to persist
     *
     * @see #persist(Object)
     * @see #persist(Iterable)
     * @see #persist(Object, Object...)
     */
    default void persist(Stream<Entity> entities)
    {

        INSTANCE.persist(entities);
    }

    /**
     * Persist all given entities.
     *
     * @param entities the entities to persist
     *
     * @see #persist(Object)
     * @see #persist(Stream)
     * @see #persist(Iterable)
     */
    default void persist(Entity firstEntity, @SuppressWarnings("unchecked") Entity... entities)
    {

        INSTANCE.persist(firstEntity, entities);
    }

    /**
     * Update all entities of this type matching the given query, with optional indexed parameters.
     *
     * @param params optional sequence of indexed parameters
     *
     * @return the number of entities updated.
     *
     * @see #update(String, Map)
     * @see #update(String, Parameters)
     */
    @GenerateBridge
    default int update(String query, Object... params)
    {

        throw INSTANCE.implementationInjectionMissing();
    }

    /**
     * Update all entities of this type matching the given query, with named parameters.
     *
     * @param params {@link Map} of named parameters
     *
     * @return the number of entities updated.
     *
     * @see #update(String, Object...)
     * @see #update(String, Parameters)
     */
    @GenerateBridge
    default int update(String query, Map<String, Object> params)
    {

        throw INSTANCE.implementationInjectionMissing();
    }

    /**
     * Update all entities of this type matching the given query, with named parameters.
     *
     * @param params {@link Parameters} of named parameters
     *
     * @return the number of entities updated.
     *
     * @see #update(String, Object...)
     * @see #update(String, Map)
     */
    @GenerateBridge
    default int update(String query, Parameters params)
    {

        throw INSTANCE.implementationInjectionMissing();
    }


}



