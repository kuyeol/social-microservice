package com.packt.cantata.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.packt.cantata.domain.Corporation;
import com.packt.cantata.repository.CorporationRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/corporations")
@RequiredArgsConstructor
public class CorporationController {
	@Autowired
	private CorporationRepository corepo;
	@RequestMapping("/add")
	public void postCorp(@RequestBody Corporation corp) {
		corepo.save(corp);
	}
	@GetMapping("/getcop")
	public Corporation getCop(@RequestParam("id")String id){
		System.out.println(id);
		return corepo.findByCeo(id);
	}

	@GetMapping("/filtercop")
	public Boolean filteredCop(@RequestParam("id")String id){

		Corporation findCorps = corepo.findByCorp(id);

		if(findCorps != null) {
			return true;
		}else {
			return false;
		}

	}

}
