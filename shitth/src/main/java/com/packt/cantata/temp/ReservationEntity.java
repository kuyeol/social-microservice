package com.packt.cantata.temp;


import com.packt.cantata.temp.common.EntityBase;
import javax.persistence.Entity;

@Entity
public class ReservationEntity extends EntityBase {

    //컬럼 조인
    /**
     *
     */

    EventEntity event;

    //컬럼 조인
    CustomerEntity customer;


}
