package com.packt.cantata.dto;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PerformanceFormDto {
	private String pfCate;
	private String pfTitle;
	
	private String agency;
	private String agencyTel;
//	private String pfPoster;
//	private String pfEximg;
	private String pfExplan;
	private String pfNotice;
	private Date pfStart;
	private Date pfEnd;
	private int pfRuntime;
	private int r;
	private int s;
	private int a;
	private int b;
	private int c;
	private int d;
	private Long plantNo;
	
	@Override
	public String toString() {
		return "PerformanceFormDto [pfTitle=" + pfTitle + ", pfCate=" + pfCate + ", agency="
				+ agency + ", agencyTel=" + agencyTel + ", pfExplan=" + pfExplan + ", pfNotice=" + pfNotice + ", pfStart=" + pfStart + ", pfEnd=" + pfEnd
				+ ", pfRuntime=" + pfRuntime + ", R=" + r + ", S=" + s + ", A=" + a + ", B=" + b + ", C=" +c + ", D="
				+ d + ", plantNo=" + plantNo + "]";
	}
}
