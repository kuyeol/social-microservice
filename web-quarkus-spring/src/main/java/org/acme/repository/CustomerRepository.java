package org.acme.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import org.acme.service.customer.CredentialModel;
import org.acme.service.customer.entity.Credential;
import org.acme.service.customer.entity.User;
import org.acme.utils.ModelUtils;


@ApplicationScoped
public class CustomerRepository implements Repository<User> {

    @Inject
    EntityManager em;


    @Override
    @Transactional
    public Optional<User> findByName(String name) {


        // em.find(User.class, name);

        return Optional.ofNullable(
            em.createNamedQuery("findByName", User.class).setParameter("username", name).getSingleResultOrNull());

    }


    @Override
    @Transactional
    public void add(User user) {
        CredentialModel cred = new CredentialModel();


        em.persist(user);

        cred.setCreatedDate(Instant.now().toEpochMilli());
        cred.setCredentialData(user.getUsername());
        cred.setSecretData(user.getUsername());
        cred.setType("secret");
        cred(cred, user.getId());
    }


    void cred(CredentialModel cred, String id) {

        Credential entity = new Credential();
        String credid = ModelUtils.generateId();
        entity.setId(credid);
        entity.setCreatedDate(cred.getCreatedDate());
        entity.setType(cred.getType());
        entity.setSecretData(cred.getSecretData());
        entity.setCredentialData(cred.getCredentialData());
        User userRef = em.getReference(User.class, id);
        entity.setUser(userRef);
        em.persist(entity);

    }


    @Override
    public boolean remove(User user) {
        return false;
    }


}


