package org.acme.client.customer.repository;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;


public abstract class CustomerLookup<Entity> {

    @Inject
    protected EntityManager em;

    protected Class<Entity> entityClass;

    public TypedQuery<Entity> searchForName(String name) {

        return em.createNamedQuery("findByName", getEntityClass(entityClass)).setParameter("name", name);
    }

    protected abstract Class<Entity> getEntityClass(Class<Entity> entityClass);

}
