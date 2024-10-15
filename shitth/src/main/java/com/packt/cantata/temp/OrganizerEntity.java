package com.packt.cantata.temp;


import com.packt.cantata.temp.common.Account;
import java.util.Collection;
import java.util.LinkedList;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.BatchSize;

@Entity
@Table
public class OrganizerEntity  extends Account {


    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true, mappedBy="organizer")
    @BatchSize(size = 20)
    private Collection<EventEntity> events  = new LinkedList<>();;


    public Collection<EventEntity> getEvent() {
        if (events == null) {
            events = new LinkedList<>();
        }
        return events;
    }

    public void setEvent(Collection<EventEntity> events) {
        this.events = events;
    }


}
