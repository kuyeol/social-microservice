package com.packt.cantata.temp;


import com.packt.cantata.temp.common.EntityBase;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "EVENT")
public class EventEntity  extends EntityBase {



  @Column(name = "EVENT_TITLE")
  private String eventTitle;

  private String name;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  private HallEntity halls;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "ORGANIGER_ID")
  private OrganizerEntity organizer;;

  @Column(name = "EVENT_START_DATE")
  private Long eventStartDate;

  @Column(name = "EVENT_END_DATE")
  private Long eventEndDate;

  @OneToOne
  private EventDetails eventDetails;


  @OneToMany(mappedBy = "event",cascade = CascadeType.ALL)
  private List<TicketEntity> tickets;

}
