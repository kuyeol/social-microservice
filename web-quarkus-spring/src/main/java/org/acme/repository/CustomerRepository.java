package org.acme.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.acme.service.customer.entity.User;


@ApplicationScoped
public class CustomerRepository implements Repository<User> {

    @PersistenceContext
    EntityManager em;


    @Override
    @Transactional
    public Optional<User> findByName(String name) {


        em.find(User.class, name);
        Set<User> U =
            em.createNamedQuery("findByName", User.class).setParameter("username", name).getResultList().stream()
                .collect(
                    Collectors.toSet());


        return U.stream().findFirst();

    }


    @Override
    @Transactional
    public void add(User a) {

        em.persist(a);

    }

    @Override
    public boolean remove(User user) {
        return false;
    }


}


