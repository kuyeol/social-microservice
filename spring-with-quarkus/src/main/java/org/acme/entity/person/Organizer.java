package org.acme.entity.person;


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
import java.util.Collection;
import java.util.LinkedList;
import javax.persistence.Table;
import org.acme.entity.date.Rental;
import org.acme.entity.date.Schedule;
import org.acme.entity.item.Performance;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table
public class Organizer {
    @Id
    @Column(name="ID", length = 36)
    @Access(AccessType.PROPERTY)
    private String id;

    private String OrganizerName;

    private String OnwnerName;

    private String phoneNumber;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "rental_id",nullable = false)
    private Rental rental;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy= "organizer")
    @Fetch(FetchMode.SELECT)
    @BatchSize(size=20)
    private Collection<Performance> performance = new LinkedList<>();


}
