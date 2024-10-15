package com.packt.cantata.dto;

import java.util.Date;

public interface PerformDto {
	Integer getPf_code();
	Integer getR();
	Integer getS();
	Integer getA();
	Integer getB();
	Integer getC();
	Integer getD();
	String getPf_title();
	String getPf_poster();
	Integer getPf_runtime();
	Date getPf_start();
	Date getPf_end();
	
}
