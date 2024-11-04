package org.acme.entity.item;

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
import org.acme.entity.date.Schedule;
import org.acme.entity.person.Casting;
import org.acme.entity.person.Organizer;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


@Entity
@Table
public class Performance {

    @Id
    @Column(name="ID", length = 36)
    @Access(AccessType.PROPERTY)
    private String id;

    private String performanceName;

    private String performanceDescription;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "organizer_id",nullable = false)
    private Organizer organizer;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy= "performance")
    @Fetch(FetchMode.SELECT)
    @BatchSize(size=20)
    private Collection<Casting> casting = new LinkedList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy= "performance")
    @Fetch(FetchMode.SELECT)
    @BatchSize(size=20)
    private Collection<Schedule> schedule= new LinkedList<>();


}
