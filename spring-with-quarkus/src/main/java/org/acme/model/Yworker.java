package org.acme.model;

import org.acme.entity.location.Venue;

public class Yworker<T extends Venue> extends Xworker<T> {


    public String intValue() {

        return venue.getId();
    }

    public  Yworker createYworker() {
        Venue v=new Venue();
        v.setVenueName("aaa");
        add(v);
        return new Yworker();
    }


    public void addHall(String hallName) {

        venue.setHalls(hallName);

    }

    public Venue selectVenue(String name) {


        return venue;
    }


}
