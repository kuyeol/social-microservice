package org.acme.entity.object;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Table;
import javax.naming.Name;


@Entity
@NamedQuery(name="getHallSeatCount", query="select count(u) from Seat u where u.hallId = :hallId")
@Table(name="seat", uniqueConstraints = {
    @UniqueConstraint(columnNames = { "HALL_ID", "seat_no" })
})
public class Seat  {

    @Id
    @Column(name = "ID", length = 36)
    @Access(AccessType.PROPERTY)
    protected String id;



    @Column(name = "seat_no")
    protected int seatNumber;

    protected int seatCapacity;



    @Column(name = "HALL_ID")
    protected String hallId;


    public String getHallId() {
        return hallId;
    }

    public void setHallId(String hallId) {
        this.hallId = hallId;
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
