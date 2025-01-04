package org.acme.client.ungorithm.jpa;

import jakarta.persistence.EntityManager;

public abstract class DataAccess<T> {

    EntityManager em;


    void persist(final T entity) {

    }




}
