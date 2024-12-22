package org.acme.client.customer.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Stream;
import org.acme.client.customer.entity.Customer;
import org.acme.client.customer.model.UserModel;
import org.acme.client.customer.model.UserRepresentation;
import org.acme.core.spi.DefaultRepository;

@ApplicationScoped
public class CustomerRepository implements DefaultRepository<Customer> {

    @Inject
    EntityManager em;

    @Override
    @Transactional
    public UserModel findByName(String name) {


        TypedQuery<Customer> query = em.createNamedQuery("findByName", Customer.class)
            .setParameter("name", name);
        query.getResultStream();


        if (query.getSingleResult() != null) {
            return null;
        }

        Customer rs = query.getSingleResult();

        UserModel user = new UserRepresentation();


        user.setUsername(rs.getCustomerName());
        user.setEmail(rs.getEmail());
        user.setCreatedTimestamp(rs.getCreatedTimestamp());
        System.out.println(user);

        return user;

    }


    @Override
    public Stream<Customer> findAll() {
        return Stream.empty();
    }

    @Override
    public void add(Customer a) {

    }

    @Transactional
    public List<Customer> findListByName(String name) {

        TypedQuery<Customer> query = em.createNamedQuery("findByName", Customer.class)
            .setParameter("name", name);

        System.out.println(query.getResultStream().toList());
        return query.getResultStream().toList();

    }


    public boolean findAll(String name) {

        return true;
    }

    public void close() {

    }


    @Override
    public boolean remove(Customer customer) {
        return false;
    }

}
