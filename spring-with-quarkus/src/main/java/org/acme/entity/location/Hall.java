package org.acme.entity.location;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.Collection;
import java.util.LinkedList;
import org.acme.entity.object.Seat;
import org.acme.utils.ModelUtils;


@Entity
@Table(name = "HALL", uniqueConstraints = {@UniqueConstraint(columnNames = {"VENUE_ID", "HALLNAME"})})
public class Hall {

    @Id
    @Column(name = "ID", length = 36)
    @Access(AccessType.PROPERTY)
    protected String id;

    @Column(name = "HALLNAME")
    protected String hallName;


    @Column(name = "VENUE_ID")
    protected String venueId;

    protected int seatCount;


    @OneToMany
    protected Collection<Seat> seatCapacity = new LinkedList<>();


    public Collection<Seat> getSeatCapacity() {
        if (seatCapacity == null) {
            seatCapacity = new LinkedList<>();
        }
        return seatCapacity;
    }

    public void setSeatCapacity(Collection<Seat> seatCapacity) {
        this.seatCapacity = seatCapacity;
    }

    public String getVenueId() {
        return venueId;
    }

    public void setVenueId(String venueId) {
        this.venueId = venueId;
    }

    public String getId() {
        return id;
    }

    public void setId() {
        this.id = ModelUtils.generateId();
    }


    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public int getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(int seatCount) {
        this.seatCount = seatCount;
    }


}
