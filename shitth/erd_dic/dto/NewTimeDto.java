package com.packt.cantata.dto;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NewTimeDto {
	private Long pfCode;
    private Date ptDate;
    private Date ptEndtime;
	@Override
	public String toString() {
		return "NewTimeDto [pfCode=" + pfCode + ", ptDate=" + ptDate + ", ptEndtime=" + ptEndtime + "]";
	}
	

}
