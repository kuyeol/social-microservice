package org.acme.entity.location;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;


@Entity
//@NamedQuery(name = "Venue.findByName", query = "select u from Venue u where u.venueName = ?1")
public class Venue {


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Hall> getHalls() {
        return halls;
    }

    @Id
    private String id;

    private String venueName;

    private Long size;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "venue_id")
    private final List<Hall> halls = new ArrayList<>();


    public Venue(String venueName, Long size, List<Hall> halls) {
        super();
        this.venueName = venueName;
        this.size = size;
        this.halls.addAll(halls);
    }

    public Venue() {
        super();
    }

    public Venue(String venueName, Long size) {
        super();
        this.venueName = venueName;
        this.size = size;
    }


    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }


    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    @Override
    public String toString() {
        return "Venue{" +
            "venueName='" + venueName + '\'' +
            ", size=" + size +
            ", halls=" + halls +
            '}';
    }
}
