package com.packt.cantata.temp;


import com.packt.cantata.temp.common.EntityBase;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
//외래키 커스터머로
@Table(name = "Tickets")
public class TicketEntity extends EntityBase {


    /**
     * 이벤트 정보
     * 좌석 등급
     */
    @ManyToOne
    private EventEntity eventEntity;

    @ManyToOne
    private CustomerEntity customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservationId", nullable = false)
    private ReservationEntity reservation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EVENT_ID", nullable = false)
    private EventEntity event;




}
