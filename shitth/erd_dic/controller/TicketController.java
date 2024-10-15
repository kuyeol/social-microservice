package com.packt.cantata.controller;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.packt.cantata.repository.Perform_timeRepository;
import com.packt.cantata.repository.PerformanceRepository;
import com.packt.cantata.domain.Ticket;
import com.packt.cantata.repository.TicketRepository;
import com.packt.cantata.dto.PerformDto;
import com.packt.cantata.dto.Perform_timeDto;

@RestController
@RequestMapping(value="/ticket")

public class TicketController {
	@Autowired
	private PerformanceRepository pfrepository;
	@Autowired
	private Perform_timeRepository pftrepo;
	@Autowired
	private TicketRepository ticrep;

	@RequestMapping(value="/pftdate")
	public List<Date> getDates(){
		return pftrepo.findBygetpftimes();
	}
	@RequestMapping(value="/pftime")
	public List<Perform_timeDto> getTimes(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate pt_date, @RequestParam("title") String pf_title){
		String tmp_title = String.valueOf(pf_title);
		return pftrepo.findBypftimequery(pt_date, tmp_title);
	}

	@RequestMapping(value="/tickdate")
	public List<String> getTitles(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate pt_date){
		return pfrepository.findBypftitlequery(pt_date);
	}

	@RequestMapping(value="/seat")
	public List<String> getSeat(@RequestParam("num") int pt_no){
		return ticrep.findByptnquery(pt_no);
	}

	@RequestMapping(value="/pfinfo")
	public PerformDto getPerform(@RequestParam("name") String pf_title){
		return pfrepository.findBypfcodequery(pf_title);
	}


	@RequestMapping(value="/ticketing", method = RequestMethod.POST)
	public ResponseEntity<Ticket> pushTicket(@RequestBody Ticket ticket){
		Ticket ticketi = ticrep.save(ticket);
	        return ResponseEntity.status(HttpStatus.OK).body(ticketi);
	}

	@RequestMapping(value="/pftimeDtl")
	public List<Date> getTimesDetail(@RequestParam("pfCode") String pfCode){
		return pftrepo.findByPfCode2(pfCode);
	}
	@Transactional
	@RequestMapping(value="/ticketcheck")
	public void updateTicketCheck(@RequestParam("ticket") Long ticket){
		Ticket tick = ticrep.findByTicno(ticket);
		tick.setTic_status("검표");
	}

	@RequestMapping(value="/ticketfind")
	public List<Ticket> getTicketUser(@RequestParam("id") String id){
		return ticrep.findAllById(id);
	}
	@RequestMapping(value="/ticketcancle")
	public void delTicket(@RequestParam("no") Long id){
		ticrep.deleteById(id);
	}

}
