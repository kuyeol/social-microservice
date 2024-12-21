package org.acme.core.spi;

import java.util.stream.Stream;
import org.acme.core.model.Model;

public interface DefaultRepository<T> {

    Model findByName(String name);

    Stream<T> findAll();

    void add(T a);

    boolean remove(T t);


}
