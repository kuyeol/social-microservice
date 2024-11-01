package org.acme.entity.location;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
//@NamedQuery(name="getHallSeatCount", query="select count(u) from Seat u where u.hallId = :hallId")
@Table(name="seat")
public class Seat  {

    @Id
    @Column(name = "ID", length = 36)
    @Access(AccessType.PROPERTY)
    private String id;



    @Column(name = "seat_no")
    private int seatNumber;

    private int seatCapacity;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HALL_ID")
    private Hall hall;


    public Hall getHallId() {
        return hall;
    }

    public void setHallId(Hall hallId) {
        this.hall = hallId;
    }
    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public int getSeatCapacity() {
        return seatCapacity;
    }

    public void setSeatCapacity(int seatCapacity) {
        this.seatCapacity = seatCapacity;
    }





}
