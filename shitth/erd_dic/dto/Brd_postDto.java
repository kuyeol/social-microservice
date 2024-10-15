package com.packt.cantata.dto;

import java.time.LocalDate;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Brd_postDto{

	private Long postNo; // 글 번호
	private Long brdNo;
	private String id;
	private long postNum; //BrdNo에 따라 번호를 정렬하는 용도
	private String postTitle; // 글제목
	private String postSub; // 글내용
	private Boolean postStatus;
	private Date postDeadline; // 게시표시일자
	private LocalDate postDate;

	@Override
	public String toString() {
		return "Brd_post [postNo=" + postNo + ", postTitle=" + postTitle + ", postSub="
				+ postSub + ", postDeadline="+ postDeadline + "postDate=" + postDate +", postStatus" + postStatus + ", brdNo =" + brdNo + ", id =" + id + "]";
	}
}
