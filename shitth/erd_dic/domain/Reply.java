package com.packt.cantata.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Reply {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long repNo; //답변번호
	
	@ManyToOne
    @JoinColumn(name = "postNo")
    private Brd_post postNo; //글번호
	
	@ManyToOne
	@JoinColumn(name = "id")
	private User id; //회원ID
	
	@Column(nullable = false)
	@Lob
	private String repSub; //글내용
	
	@Column(nullable = false)
	private LocalDate repDate = LocalDate.now(); //작성일
}
