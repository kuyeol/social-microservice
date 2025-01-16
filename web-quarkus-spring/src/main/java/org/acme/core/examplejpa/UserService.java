package org.acme.core.examplejpa;

import org.acme.client.database.PostgresProvider;

public class UserService  {

    JpaCustomer customer;


   public UserService() {
        this.customer = new JpaCustomer();

    }


    PostgresProvider provider;





}
