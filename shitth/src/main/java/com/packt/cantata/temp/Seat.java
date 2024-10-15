package com.packt.cantata.temp;

import com.packt.cantata.temp.common.EntityBase;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "seat")
public class Seat extends EntityBase {

    @ManyToOne
    @JoinColumn(name = "hall_entity_id")
    private HallEntity hall;

    public HallEntity getHall() {
        return hall;
    }

    public void setHall(HallEntity hall) {
        this.hall = hall;
    }
}