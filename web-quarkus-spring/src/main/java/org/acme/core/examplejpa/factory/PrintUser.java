package org.acme.core.examplejpa.factory;

import org.acme.core.examplejpa.CustomerTransfer;
import org.acme.core.examplejpa.EntityTransfer;
import org.acme.core.examplejpa.JpaCustomer;
import org.acme.core.model.impl.EntityTyper;

public class PrintUser implements PrintEntity
{


public EntityTransfer aa(JpaCustomer customer){


    return new CustomerTransfer(customer);
}

}
