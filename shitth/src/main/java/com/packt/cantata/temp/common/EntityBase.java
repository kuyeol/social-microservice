package com.packt.cantata.temp.common;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class EntityBase {


    @Id
    @Column(name = "ID", length = 36)
    @Access(AccessType.PROPERTY)
    protected String id;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



}
