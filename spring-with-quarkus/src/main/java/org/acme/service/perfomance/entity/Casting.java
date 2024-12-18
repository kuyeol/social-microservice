package org.acme.service.perfomance.entity;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class Casting {

    @Id
    @Column(name="ID", length = 36)
    @Access(AccessType.PROPERTY)
    private String id;
    private String personName;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "performance_id",nullable = false)
    private Performance performance;



}
