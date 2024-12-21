package org.acme.client.customer.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.util.Optional;
import java.util.stream.Stream;
import org.acme.client.customer.entity.Credential;
import org.acme.client.customer.entity.Customer;
import org.acme.client.customer.model.UserModel;
import org.acme.core.spi.DefaultRepository;

@ApplicationScoped
public class CredentialRepository implements DefaultRepository<Optional<Credential>> {


    @Inject
    EntityManager em;


    @Override
    public UserModel findByName(String name) {


        //return em.createNamedQuery("credentialByUser").setParameter("customer",name).getSingleResult();

        return null;

    }

    @Override
    public Stream<Optional<Credential>> findAll() {
        return Stream.empty();
    }

    public Optional<Credential> findByname(String name) {
        String customerId = em.getReference(Customer.class, name).getId();


        long query =
            em.createNamedQuery("credentialByUser").setParameter("customer", customerId).getFirstResult();


        Credential credential = em.getReference(Credential.class, query);

        return Optional.ofNullable(credential);


    }


    @Override
    public void add(Optional<Credential> a) {

        Credential credential = new Credential();
        credential.setUser(a.get().getUser());
        credential.setCredentialData(a.get().getCredentialData());
        credential.setSecretData(a.get().getSecretData());
        credential.setCreatedDate(Instant.now().toEpochMilli());
        em.persist(credential);

    }

    @Override
    public boolean remove(Optional<Credential> credential) {
        return false;
    }
}
