package org.acme.account.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "new_entity")
public class NewEntity
{

  @Id
  @Column(name = "userId", nullable = false)
  private Long id;


  public Long getId()
  {

    return id;
  }


  public void setId( Long id )
  {

    this.id = id;
  }





}