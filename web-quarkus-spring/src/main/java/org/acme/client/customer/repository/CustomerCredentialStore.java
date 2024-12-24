package org.acme.client.customer.repository;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.stream.Stream;
import org.acme.core.model.CredentialModel;
import org.acme.client.customer.entity.Credential;
import org.acme.client.customer.entity.Customer;
import org.acme.client.customer.model.CustomerModel;
import org.acme.core.spi.CredentialStore;
import org.acme.core.utils.ModelUtils;



@ApplicationScoped
public class CustomerCredentialStore implements CredentialStore<CustomerModel> {

    @Override
    public void updateCredential(CustomerModel customer, CredentialModel cred) {

    }

    @Override
    public CredentialModel createCredential(CustomerModel customer, CredentialModel cred) {

        Credential entity = createCredentialEntity(customer, cred);

        return toModel(entity);
    }

    CredentialModel toModel(Credential entity) {
        CredentialModel model = new CredentialModel();

        model.setId(entity.getId());
        model.setType(entity.getType());
        model.setCreatedDate(entity.getCreatedDate());
        model.setSecretData(entity.getSecretData());
        model.setCredentialData(entity.getCredentialData());

        return model;
    }


    Credential createCredentialEntity(CustomerModel customer, CredentialModel cred) {

        Credential entity = new Credential();

        String id = cred.getId() == null ? ModelUtils.generateId() : cred.getId();
        entity.setId(id);
        entity.setCreatedDate(cred.getCreatedDate());
        entity.setType(cred.getType());
        entity.setSecretData(cred.getSecretData());
        entity.setCredentialData(cred.getCredentialData());
        Customer userRef = new Customer();
        userRef.setId(customer.getId());
        entity.setUser(userRef);

        return entity;
    }


    @Override
    public boolean removeStoredCredential(CustomerModel customer, String id) {
        return false;
    }

    @Override
    public CredentialModel getStoredCredentialById(CustomerModel customer, String id) {
        return null;
    }

    @Override
    public Stream<CredentialModel> getStoredCredentialsStream(CustomerModel customer) {
        return Stream.empty();
    }

    @Override
    public Stream<CredentialModel> getStoredCredentialsByTypeStream(CustomerModel customer, String type) {
        return Stream.empty();
    }

    @Override
    public CredentialModel getStoredCredentialByNameAndType(CustomerModel customer, String name, String type) {
        return null;
    }

    @Override
    public boolean moveCredentialTo(CustomerModel customer, String id, String newPreviousCredentialId) {
        return false;
    }
}
