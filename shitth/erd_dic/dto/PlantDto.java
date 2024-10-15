package com.packt.cantata.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PlantDto {
	private String plantName;
	private String plantUse;
	private String plantDetail;
	private int capacity;
	private String plantMainimg;
	private String plantSubimg1;
	private String plantSubimg2;
	private long plantCharge;
	private String plantSub;
	private Boolean plantStatus;
	private int floor;
	@Override
	public String toString() {
		return "PlantDto [plantName=" + plantName + ", plantUse=" + plantUse + ", plantDetail=" + plantDetail
				+ ", capacity=" + capacity + ", plantMainimg=" + plantMainimg + ", plantSubimg1=" + plantSubimg1
				+ ", plantSubimg2=" + plantSubimg2 + ", plantCharge=" + plantCharge + ", plantSub=" + plantSub
				+ ", plantStatus=" + plantStatus + ", floor=" + floor + "]";
	}

}
