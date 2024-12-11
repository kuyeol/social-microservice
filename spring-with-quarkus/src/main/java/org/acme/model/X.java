package org.acme.model;

import java.util.List;
import java.util.stream.Stream;
import org.acme.entity.location.Venue;

public interface X<T> {


    void add(T n);

    void createList();

    Stream<Object> getList();

    T process(Object input);

    Venue findName(String n) throws Exception;


}
