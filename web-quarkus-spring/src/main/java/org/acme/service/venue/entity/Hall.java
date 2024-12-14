package org.acme.service.venue.entity;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Collection;
import java.util.LinkedList;
import org.acme.utils.ModelUtils;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


@Entity
@Table(name = "HALL")
public class Hall {

    @Id
    @Column(name = "HALL_ID", length = 36)
    @Access(AccessType.PROPERTY)
    private String id;

    @Column(name = "HALLNAME")
    private String hallName;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "venue_id", nullable = false)
    private Venue venue;

    private int seatCount;


    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true, mappedBy = "hall")
    @Fetch(FetchMode.SELECT)
    private Collection<Seat> seatCapacity = new LinkedList<>();

    public Hall() {
        setId();
    }

    public Collection<Seat> getSeatCapacity() {
        if (seatCapacity == null) {
            seatCapacity = new LinkedList<>();
        }
        return seatCapacity;
    }

    public void setSeatCapacity(Collection<Seat> seatCapacity) {
        this.seatCapacity = seatCapacity;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof Hall)) {
            return false;
        }

        Hall that = (Hall) o;

        if (!id.equals(that.getId())) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }


    @Override
    public String toString() {
        return "\nHall {" +
            "\n" +
            "id='" + id + '\'' +
            ", hallName='" + hallName + '\'' +
            ", venue=" + venue +
            ", seatCount=" + seatCount +
            ", seatCapacity=" + seatCapacity +
            '}';
    }
}
