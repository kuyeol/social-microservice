package org.acme.springouauth2.client.repository;

import org.acme.springouauth2.client.entities.AuthorizationConsent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorizationConsentRepository extends JpaRepository< AuthorizationConsent, AuthorizationConsent.AuthorizationConsentId > {
    Optional< AuthorizationConsent > findByRegisteredClientIdAndPrincipalName( String registeredClientId ,
            String principalName );

    void deleteByRegisteredClientIdAndPrincipalName( String registeredClientId , String principalName );
}