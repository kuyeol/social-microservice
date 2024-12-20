package org.acme.client.customer.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import org.acme.core.model.CredentialModel;
import org.acme.repository.DefaultRepository;
import org.acme.client.customer.entity.Credential;
import org.acme.client.customer.entity.Customer;
import org.acme.core.utils.ModelUtils;


@ApplicationScoped
public class CustomerDefaultRepository implements DefaultRepository<Customer> {

    @Inject
    EntityManager em;


    @Override
    @Transactional
    public Optional<Customer> findByName(String name) {


        return Optional.ofNullable(em.find(Customer.class, name));

        //return Optional.ofNullable(
        //    em.createNamedQuery("findByName", Customer.class).setParameter("name", name).getSingleResultOrNull());

    }


    @Override
    @Transactional
    public void add(Customer customer) {
        CredentialModel cred = new CredentialModel();


        em.persist(customer);

        cred.setCreatedDate(Instant.now().toEpochMilli());
        cred.setCredentialData(customer.getCustomerName());
        cred.setSecretData(customer.getCustomerName());
        createCredential(cred, customer.getId());

    }


    void createCredential(CredentialModel cred, String id) {

        Credential entity = new Credential();

        String credid = ModelUtils.generateId();
        entity.setId(credid);
        entity.setCreatedDate(cred.getCreatedDate());
        entity.setSecretData(cred.getSecretData());
        entity.setCredentialData(cred.getCredentialData());
        Customer customerRef = em.getReference(Customer.class, id);
        entity.setUser(customerRef);
        em.persist(entity);


    }


    @Override
    public boolean remove(Customer customer) {
        return false;
    }


}


