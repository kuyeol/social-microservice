package org.acme.core.database;

import jakarta.persistence.EntityManager;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.transaction.annotation.Transactional;

public abstract class DataAccess<T>
{

    private final EntityManager em;

    private final AtomicInteger at = new AtomicInteger();

    private Class<T> clazz;

    protected DataAccess(EntityManager em)
    {
        this.em = em;
    }

    @Transactional
    public DataAccess<T> setClazz(Class<T> clazz)
    {
        this.clazz = clazz;

        return this;
    }

    @Transactional(readOnly = true)
    protected List<?> listAll(String query)
    {
        return em.createQuery(query).getResultList();
    }


    @Transactional(readOnly = true)
    public List<?> listAll()
    {
        List<?> rs=null;
        try {
            rs=em.createQuery("from " + clazz.getName()).getResultList();
        }catch (RuntimeException e){
            throw new RuntimeException(e);
        }

        return rs;
    }

    @Transactional(readOnly = true)
    public Object find(int id, Class<T> t)
    {
        tableLog(t);
        return em.find(t, id);
    }

    @Transactional(readOnly = true)
    public T find(int id)
    {
        return em.find(clazz, id);
    }

    @Transactional
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


}
