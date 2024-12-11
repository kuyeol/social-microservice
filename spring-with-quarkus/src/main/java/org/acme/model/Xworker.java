package org.acme.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.acme.entity.location.Venue;

public class Xworker<T> implements X {
    protected T venue;
protected List<Object> venueList= new ArrayList<>();

    @Override
    public void add(Object o) {
        o = new Object();
        venueList.add(o);
    }

    @Override
    public void createList() {

    }



    @Override
    public Stream<Object> getList() {

        Stream<Object> temp =
            venueList.stream().map(venue->venueList.iterator());

        return temp;
        //return List.of();
    }

    @Override
    public Object process(Object input) {
        return null;
    }

    @Override
    public Venue findName(String n) throws Exception {
        return null;
    }
}
