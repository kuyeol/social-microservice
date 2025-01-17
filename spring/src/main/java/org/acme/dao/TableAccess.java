package org.acme.dao;

import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.acme.entity.AtomicTable;
import org.springframework.stereotype.Component;

/**
 * This class provides access to the database tables. It uses the EntityManager to interact with the
 * database. It also uses the AtomicTable class to log the access to the tables.
 *
 * @param <T> The type of the entity.
 *
 * @version 1.0
 * @see EntityManager
 * @see AtomicTable
 * @since 1.0
 */
@Component
public class TableAccess<T> implements AutoCloseable
{

    private final EntityManager em;

    private final AtomicInteger at = new AtomicInteger();

    private      Class<T>             clazz;


    public TableAccess<T> setClazz(Class<T> clazz)
    {
        this.clazz = clazz;
        return this;
    }


    private Class<?> getClassByModel(TAtest.Model type) {
        return type.getClazz();
    }



    public Class<T> getClazz(Class<T> c)
    {
        return clazz=c;
    }


    public TableAccess(EntityManager em)
    {

        this.em = em;
    }


    public void save(T o)
    {

        em.persist(o);
    }


    private void tableLog(Object o)
    {
        AtomicTable AT = new AtomicTable();
        AT.setId(at.incrementAndGet());
        AT.setName(o.getClass().getName());
        em.persist(AT);
    }

    public List<Object> listAll(){

        return em.createQuery("from "+clazz.getName()).getResultList();
    }

    public <T> Object find(int id, Class<T> t)
    {

        return em.find(t, id);
    }

    public <T> Object find(int id)
    {

        return em.find(clazz, id);
    }

    @Override
    public void close() throws Exception
    {

    }


}
