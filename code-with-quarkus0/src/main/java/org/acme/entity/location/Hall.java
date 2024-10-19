package org.acme.entity.location;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.acme.entity.BaseEntity;


@Entity
public class Hall extends BaseEntity {





    public Venue getName() {
        return name;
    }

    public void setName(Venue name) {
        this.name = name;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    private String hallName;




    @ManyToOne
    @JoinColumn(name = "venue_Name")
    private Venue name;

    @ManyToOne
    private Venue venue;

    private int seatCount;


    public int getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(int seatCount) {
        this.seatCount = seatCount;
    }



    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }





}
