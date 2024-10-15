package com.packt.cantata.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class File {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long fileNum;
	
	@ManyToOne
//	@JsonManagedReference(value = "performance-files")
	@JoinColumn(name = "pf_code", nullable=true)
	private Performance performance;	
	
	@ManyToOne
	@JoinColumn(name = "postNo", nullable=true)
	private Brd_post brdPost;
	
	@ManyToOne
	@JoinColumn(name = "plantNo", nullable=true)
	private Plant plant;
	
    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String fileOriName;

    @Column(nullable = false)
    private String  fileUri;
}
