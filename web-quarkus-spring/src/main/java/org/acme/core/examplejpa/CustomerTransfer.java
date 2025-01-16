package org.acme.core.examplejpa;

public class CustomerTransfer extends EntityTransfer<JpaCustomer>
{



    private String name;

    public CustomerTransfer(JpaCustomer customer)
    {
        super(customer.getId());
        this.name= customer.getCustomerName();
    }








}
