package com.packt.cantata.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import javax.persistence.Table;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name="USER_TABLE")
@NamedQuery(name = "User.findByEmailAddress",
	query = "select u from User u where u.email = ?1")
public class User {

	@Id
	@Column(nullable = false, updatable = false)
	private String id;

	@Column(nullable = false)
	private String password;

  @Column(name="USER_NAME")
  private String username;

  @Column(name="BIRTH_DATE")
  private Long birthDate;

  @Column(name="GENDER")
	private String gender;

  @Column(name="PHONE_NUMBER")
	private String phoneNumber;

  @Column(name="EMAIL")
	private String email;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
  private Set<Address> address;
  //todo set if null new hashmap

  @Column(name = "UPDATED_AT")
	private Date updatedAt;

  @Column(name = "CREATED_TIMESTAMP")
  private Long createdTimestamp;

	private String Auth;



	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "id")
	private List<Corporation> corporations;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "id")
	private List<Reply> replies;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "id")
	private List<Brd_post> brd_posts;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "id")
	private List<Ticket> tickets;






}
