package org.acme.repository;

import java.util.Optional;

public interface DefaultRepository<T>  {

    Optional<T> findByName(String name);


   void add(T a);

    boolean remove(T t);


}
