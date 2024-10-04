package org.acme.springouauth2.client.repository;

import org.acme.springouauth2.client.entities.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, String> {
    Optional< ClientEntity > findByClientId(String clientId);
}