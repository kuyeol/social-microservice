package com.packt.cantata.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



public class Brd_division {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long brdNo;

	@Column(nullable = false)
	private String brdName;

	public Brd_division(String brdName) {
		super();
		this.brdName = brdName;
	}
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "brdNo")
	@JsonIgnore
	private List<Brd_post> brdPosts;





}
