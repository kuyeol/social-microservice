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
public class CustomerRepository extends CustomerLookup<Customer> implements DefaultRepository<Customer> {

    @Inject
    EntityManager em;


    public CustomerRepository() {

    }

    @Override
    @Transactional
    public UserModel findByName(String name) {

        TypedQuery<Customer> query = searchForName(name);

        if (query.getResultList().isEmpty()) {

            UserRepresentation nullRep = new UserRepresentation();
            nullRep.setId("");
            nullRep.setUsername("");
            return nullRep;
        }


        UserRepresentation rep = new UserRepresentation();
        rep.setUsername(query.getResultList().get(0).getCustomerName());
        rep.setId(query.getResultList().get(0).getId());
        System.out.println(rep.getUsername() + "repo");

        return rep;

    }


    @Transactional
    public Customer findByNam(String name) {


        TypedQuery<Customer> query = searchForName(name);


        return query.getResultStream().findFirst().orElse(null);

    }


    @Override
    public Stream<Customer> findAll() {


        return Stream.empty();
    }

    @Override
    @Transactional
    public void add(Customer customer) {

        Customer entity = new Customer();
        entity.setCustomerName(customer.getCustomerName());
        entity.setEmail(customer.getEmail());
        entity.setCreatedTimestamp(customer.getCreatedTimestamp());


        em.persist(customer);

    }

    @Transactional
    public List<Customer> findListByName(String name) {

        TypedQuery<Customer> query = searchForName(name);

        return query.getResultStream().toList();

    }


    public boolean findAll(String name) {

        return true;
    }

    public void close() {

    }


    private Customer entityContext(String id) {

        Customer customer = em.getReference(Customer.class, id);
        return em.contains(customer) ? customer : null;
    }


    @Override
    public boolean remove(Customer customer) {
        return false;
    }


    @Override
    protected Class<Customer> getEntityClass(Class<Customer> customerClass) {
        return customerClass;
    }
}
