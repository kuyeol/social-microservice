package org.acme.dao;

import jakarta.persistence.EntityManager;

import java.io.Closeable;
import java.util.concurrent.atomic.AtomicInteger;

import org.acme.entity.AtomicTable;
import org.springframework.stereotype.Component;


@Component
public class TableAccess<T> implements AutoCloseable {

    private final EntityManager em;

   private AtomicInteger at = new AtomicInteger();

    private  Class<T> t;




    public TableAccess(EntityManager em)
    {
        this.em = em;


    }


    public void save(T o) {

        em.persist(o);
    }


    private void tableLog(Object o) {
        AtomicTable AT = new AtomicTable();
        AT.setId(at.incrementAndGet());
        AT.setName(o.getClass().getName());
        em.persist(AT);
    }


//    public void setProvider(Class<T> t) {
//        this.t = t;
//    }


    public Class<T> getEntity() {

        return t;
    }

    public T find(int id) {
        System.out.println("TableAccess : " + em.find(getEntity(), id));

        return em.find(getEntity(), id);
    }


    @Override
    public void close() throws Exception {

    }


}
