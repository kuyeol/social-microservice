package org.acme.model;


import jakarta.persistence.Graph;

public class VenueModel {

    private String venueName;
    private Long size;






    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }


    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }
}
