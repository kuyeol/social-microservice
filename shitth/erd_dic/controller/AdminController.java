package com.packt.cantata.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.packt.cantata.domain.Ticket;
import com.packt.cantata.repository.TicketRepository;
import com.packt.cantata.domain.User;
import com.packt.cantata.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value="/admin")
public class AdminController {

	@Autowired
	private UserRepository usrrepo;
	@Autowired
	private TicketRepository ticrep;


	@GetMapping(value="/member")
	public List<User> getMember(){
		return usrrepo.findAll();
	}
	@RequestMapping(value="/remove")
	public void removeMember(@RequestParam("id") String id){
		usrrepo.deleteById(id);
	}
	@RequestMapping(value="/ticket")
	public List<Ticket> getTicket(){
		return ticrep.findAll();
	}
	@RequestMapping(value="/ticket/remove")
	public void removeTicket(@RequestParam("id") Long id){
		ticrep.deleteById(id);
	}
}
