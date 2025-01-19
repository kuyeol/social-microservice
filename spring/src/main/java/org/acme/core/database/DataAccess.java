package org.acme.core.database;

import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.acme.core.database.LogTable;

public abstract class DataAccess<T> implements AutoCloseable
{

    private final EntityManager em;

    private final AtomicInteger at = new AtomicInteger();

    private Class<T> clazz;

   protected DataAccess(EntityManager em)
    {
        this.em = em;
    }

    public DataAccess<T> setClazz(Class<T> clazz)
    {
        this.clazz = clazz;
        return this;
    }

    private  <T> List<T> listAll(String query)
    {
        return em.createQuery(query).getResultList();
    }

    public <T> List<T> listAll()
    {
        return em.createQuery("from " + clazz.getName()).getResultList();
    }

    public <T extends Object> Object find(int id, Class<T> t)
    {
        tableLog(t);
        return em.find(t, id);
    }

    public T find(int id)
    {
        return em.find(clazz, id);
    }

    public void save(T o)
    {
        em.persist(o);
    }

    //test log method
    private void tableLog(Object o)
    {
        LogTable AT = new LogTable();
        AT.setId(at.incrementAndGet());
        AT.setName(o.getClass().getName());
        em.persist(AT);
    }

    @Override
    public void close() throws Exception
    {

    }


}
