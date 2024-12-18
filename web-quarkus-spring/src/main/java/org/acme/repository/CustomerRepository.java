package org.acme.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.acme.service.customer.entity.User;


@ApplicationScoped
public class CustomerRepository implements Repository<User> {

    @Inject
    EntityManager em;


    @Override
    @Transactional
    public Optional<User> findByName(String name) {


        em.find(User.class, name);
        List<String> list = new ArrayList<>();
        em.createNamedQuery("findByName", User.class).setParameter("username", name).getResultList().stream()
            .filter(s -> s.equals(name)).map(p -> list.add(
                p.getUsername()));

        System.out.println(list);

        return Optional.ofNullable(
            em.createNamedQuery("findByName", User.class).setParameter("username", name).getSingleResultOrNull());

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


