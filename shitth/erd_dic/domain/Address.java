package com.packt.cantata.domain;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "address")
public  class Address {
  @Id
  @Column(name = "id", nullable = false)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  public User user;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
