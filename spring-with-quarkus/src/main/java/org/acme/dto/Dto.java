package org.acme.dto;

import org.acme.entity.location.Venue;


public class Dto {



    private  String name;

    private  Long size;

    // Default constructor for Jackson deserialization
    public Dto() {

    }

    public Dto(Venue user) {
        this.name = user.getId();
        this.size = user.getSize();

    }

    public String getName() {
        return name;
    }

    public Long getSize() {
        return size;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setSize(Long size) {
        this.size = size;
    }
}
