package com.packt.cantata.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Rental")
public class Rental {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long rentNo;
	@Column(nullable = false)
	
	private Date rent_start;		
	private Date rent_end;	
	private Date rent_regidate;		
	private String rent_status;		
	private String rent_pay;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cpNo")
	private Corporation cpNo;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "plantNo")
	private Plant plantNo;
	
	@Builder
	public Rental(Corporation cpNo, Plant plantNo,
			Date rent_start, Date rent_end, Date rent_regidate, String rent_status, String rent_pay) {
		this.cpNo = cpNo;
		this.plantNo = plantNo;
		this.rent_start = rent_start;
		this.rent_end = rent_end;
		this.rent_regidate = rent_regidate;
		this.rent_status = rent_status;
		this.rent_pay = rent_pay;
	}
	

}