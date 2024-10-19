package org.acme.entity.object;

import jakarta.persistence.Entity;
import org.acme.entity.BaseEntity;


@Entity
public class Seat extends BaseEntity {

    private int seatNumber;
    private int seatCapacity;
}
