package org.acme.repository;

import java.util.Optional;
import org.acme.service.customer.entity.User;

public interface Repository<T>  {

    Optional<T> findByName(String name);


   void add(T a);

    boolean remove(T t);


}
