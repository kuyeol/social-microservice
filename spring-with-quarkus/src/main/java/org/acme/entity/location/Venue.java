package org.acme.entity.location;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import org.acme.utils.ModelUtils;


@Entity
@NamedQuery(name = "Venue.findByName", query = "select u from Venue u where u.venueName = ?1")
public class Venue {

    @Id
    @Column(name = "Id", length = 36)
    @Access(AccessType.PROPERTY)
    protected String id;


    @Column(name = "VENUENAME")
    protected String venueName;

    protected Long size;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "Venue_Id")
    protected Collection<Hall> halls = new LinkedList<>();

    public String getId() {
        return id;
    }

    public void setId() {
        this.id = ModelUtils.generateId();
    }

    public Collection<Hall> getHalls() {
        if (halls == null) {
            halls = new LinkedList<>();
        }
        return halls;
    }

    public void setHalls(Collection<Hall> halls) {
        this.halls = halls;
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
