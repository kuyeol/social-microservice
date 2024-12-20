package org.acme.client.customer.repository;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.Optional;
import org.acme.repository.DefaultRepository;
import org.acme.client.customer.entity.Credential;

@ApplicationScoped
public class CredentialRepository implements DefaultRepository<Credential> {


    @Override
    public Optional<Credential> findByName(String name) {

        return Optional.empty();
    }

    @Override
    public void add(Credential a) {

    }

    @Override
    public boolean remove(Credential credential) {
        return false;
    }
}
