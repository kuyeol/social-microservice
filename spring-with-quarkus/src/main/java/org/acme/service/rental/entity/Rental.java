package org.acme.service.rental.entity;


import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.Collection;
import java.util.LinkedList;
import javax.persistence.Table;

import org.acme.service.perfomance.entity.Organizer;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


@Entity
@Table
public class Rental {

    @Id
    @Column(name = "ID", length = 36)
    @Access(AccessType.PROPERTY)
    private String id;

    private RentalStatus status;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "rental")
    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 20)
    private Collection<Organizer> organizer = new LinkedList<>();


}
