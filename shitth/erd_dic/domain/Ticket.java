package com.packt.cantata.domain;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity 
@JsonIgnoreProperties(ignoreUnknown = true) 
@Getter 
@Setter 
@NoArgsConstructor 
@EntityListeners(AuditingEntityListener.class)
public class Ticket {
	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(nullable=false, updatable=false) 
	private Long tic_no; 	
	 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ptNo")
	private Perform_time pt_no;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id")
	private User id;
	
	private String seat_no;
	@CreatedDate
	private LocalDateTime tic_date;
	@ColumnDefault("1")
	private String tic_status;
	private String tic_pay;
	
	public Ticket(Perform_time pt_no, String seat_no,User id , String tic_pay) { 
			super();
			this.pt_no = pt_no;
			this.id = id;
			this.seat_no = seat_no;
			this.tic_pay = tic_pay;
		}
	@PrePersist
    public void prePersist() {
        this.tic_status = this.tic_status == null ? "예매완료" : this.tic_status;
    }
	
}