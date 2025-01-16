package org.acme.core.examplejpa;

import jakarta.inject.Inject;
import org.acme.client.database.PostgresProvider;

public class TestService
{
    @Inject
    PostgresProvider jpa;


    public void newCustomer(JpaCustomer customer){

        jpa.exists(customer);


    }




}
