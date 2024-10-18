package org.springframework.samples.petclinic.perform.entity.location;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.samples.petclinic.model.BaseEntity;


@Entity
public class Venue extends BaseEntity {


    private String venueName;

    private int size;


    public List<Hall> getHallName() {
        return hallName;
    }

    public void setHallName(List<Hall> hallName) {
        this.hallName = hallName;
    }

    @OneToMany
    private List<Hall> hallName;



    @OneToMany(mappedBy = "venue", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @Fetch(FetchMode.SELECT)
    private Set<Hall> halls = new HashSet<>();

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }


    public void registerHall(Hall hall) {
        halls.add(hall);
        hall.setVenue(this);
    }



    public Set<Hall> getHalls() {
        if (halls == null) {
            halls = new HashSet<>();
        }


        return halls;
    }

    public void setHalls(Set<Hall> halls) {
        this.halls = halls;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }
}
