package com.packt.cantata.dto;

import org.hibernate.annotations.ColumnDefault;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class dupleDto {
	@ColumnDefault("")
	private String id;
//	@ColumnDefault("")
//	private String email;
//	@ColumnDefault("")
//	private long tel;
	
}
