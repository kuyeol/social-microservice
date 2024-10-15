package com.packt.cantata.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Plant")
public class Plant {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(nullable=false, updatable=false)
	private long plantNo;
	private String plantName;
	private String plantUse;
	@Lob
	private String plantDetail;
	@Lob
	private String plantSub;

	private int capacity;
	private String plantMainimg;
	private String plantSubimg1;
	private String plantSubimg2;
	private long plantCharge;
	private Boolean plantStatus;
	private int floor;

	public Plant(String plantName, String plantUse, String plantDetail
			, int capacity, String plantMainimg, String plantSubimg1, String plantSubimg2
			, long plantCharge, String plantSub, Boolean plantStatus, int floor) {
		super();
		this.plantName = plantName;
		this.plantUse = plantUse;
		this.plantDetail = plantDetail;
		this.capacity = capacity;
		this.plantMainimg = plantMainimg;
		this.plantSubimg1 = plantSubimg1;
		this.plantSubimg2 = plantSubimg2;
		this.plantCharge = plantCharge;
		this.plantSub = plantSub;
		this.plantStatus = plantStatus;
		this.floor = floor;
	}


	@Override
	public String toString() {
		return "Plant [plantNo=" + plantNo + ", plantName=" + plantName + ", plantUse=" + plantUse + ", plantDetail="
				+ plantDetail + ", capacity=" + capacity + ", plantMainimg=" + plantMainimg + ", plantSubimg1="
				+ plantSubimg1 + ", plantSubimg2=" + plantSubimg2 + ", plantCharge=" + plantCharge + ", plantSub="
				+ plantSub + ", plantStatus=" + plantStatus + ", floor=" + floor + ", rentals=" + rentals
				+ ", performances=" + performances + "]";
	}
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "plantNo")
	private List<Rental> rentals;

	@JsonIgnore
	@OneToMany(mappedBy = "plantNo", cascade = CascadeType.ALL)
	private List<Performance> performances;

	@JsonIgnore
	@OneToMany(mappedBy = "plant", cascade = CascadeType.ALL)
    private List<File> files = new ArrayList<>();
}
