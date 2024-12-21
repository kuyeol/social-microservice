package org.acme.client.customer.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.stream.Stream;
import org.acme.client.customer.entity.Credential;
import org.acme.client.customer.entity.Customer;
import org.acme.client.customer.model.CredentialRepresentation;
import org.acme.client.customer.model.UserModel;
import org.acme.client.customer.model.UserRepresentation;
import org.acme.core.model.CredentialModel;
import org.acme.core.model.PasswordCredentialModel;
import org.acme.core.security.hash.Argon2PasswordHashProvider;
import org.acme.core.spi.DefaultRepository;
import org.acme.core.utils.ModelUtils;

@ApplicationScoped
public class CustomerRepository implements DefaultRepository<Customer> {

    @Inject
    EntityManager em;

    @Override
    @Transactional
    public UserModel findByName(String name) {

        TypedQuery<Customer> query = em.createNamedQuery("findByName", Customer.class)
                .setParameter("name", name);
        query.getResultStream().toList();
        List<Customer> rs = query.getResultList();

        UserRepresentation user = new UserRepresentation();
        List<CredentialRepresentation> cred = new ArrayList<>();

        cred.add(new CredentialRepresentation());

        if (rs.isEmpty()) {
            return null;
        }

        for (Customer customer : rs) {
            user.setUsername(customer.getCustomerName());
            user.setEmail(customer.getEmail());
            user.setId(customer.getId());
            user.setCreatedTimestamp(customer.getCreatedTimestamp());
            user.setCredentials(cred);
        }

        return new UserRepresentation(user);

    }

    @Override
    public Stream<Customer> findAll() {
        return Stream.empty();
    }

    @Transactional
    public List<Customer> findByN(String name) {

        TypedQuery<Customer> query = em.createNamedQuery("findByName", Customer.class)
                .setParameter("name", name);

        System.out.println(query.getResultStream().toList());
        return query.getResultStream().toList();

        // return Optional.ofNullable(em.getReference(Customer.class, name));

    }

    public boolean findAll(String name) {

        return true;
    }

    public void close() {

    }

    @Override
    @Transactional
    public void add(Customer customer) {

        String passw = "mySecurePassword123";
        PasswordCredentialModel encodedPassword = provider.encodedCredential(passw, 5);

        CredentialRepresentation credR = new CredentialRepresentation();

        credR.setSecretData(encodedPassword.getSecretData());
        credR.setCredentialData(encodedPassword.getCredentialData());
        
        Collection<Credential> cred = new ArrayList<Credential>();

        Credential credential = new Credential();
        credential.setUser(customer);
        credential.setSecretData(encodedPassword.getSecretData());
        
   // credential.setCredentialData(credR.getCredentialData());
        
        credential.setCredentialData(encodedPassword.getCredentialData());
          
        customer.setCredentials(credential.getUser().getCredentials());

        System.out.println(credential.getUser().getCredentials());

        CredentialModel model = new CredentialModel();
        model.setCredentialData(credR.getCredentialData());
        model.setSecretData(credR.getSecretData());
        createCredential(model, customer.getId());

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
