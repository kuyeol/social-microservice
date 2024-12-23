package org.acme.client.customer.service;

public interface CustomerService extends Logout{

    boolean login(String username, String password);

    boolean register(String username, String password);



}
