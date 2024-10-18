package org.springframework.samples.petclinic.perform.entity.object;

import jakarta.persistence.Entity;
import org.springframework.samples.petclinic.model.BaseEntity;


@Entity
public class Seat extends BaseEntity {

    private int seatNumber;
    private int seatCapacity;
}
