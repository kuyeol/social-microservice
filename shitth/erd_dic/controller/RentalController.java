package com.packt.cantata.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.packt.cantata.domain.Performance;
import com.packt.cantata.repository.PerformanceRepository;
import com.packt.cantata.domain.Rental;
import com.packt.cantata.repository.RentalRepository;

@RestController
//@RequiredArgsConstructor
@CrossOrigin(origins="http://localhost:3000")
@RequestMapping(value="/rentals")
public class RentalController {

	@Autowired
	private RentalRepository rentalrepository;

	@Autowired
	private PerformanceRepository pfRepository;

//	@RequestMapping(value="/rentallist", method = RequestMethod.GET)
//	public List<Rental> testget(@RequestParam("cp") String cpno){
//		return rentalrepository.findByCpNo(cpno);
//	}
	@GetMapping("/selectrent")
	public List<Rental> getselect(@RequestParam("id") String id){
	    List<Rental> rentals = rentalrepository.findByUserIdQuery(id);
	    return rentals;
	}
	@GetMapping("/allrental")
	public List<Rental> getRental(){
		return rentalrepository.findAll();
	}

	@RequestMapping(value="/rentalapp", method = RequestMethod.POST)
	public 	ResponseEntity<Rental> postrental(@RequestBody Rental rental ){
		Rental tlt = rentalrepository.save(rental);
		return ResponseEntity.status(HttpStatus.OK).body(tlt);
	}
	@DeleteMapping("/delrental")
	public void delRental(@RequestParam("rentNo")Long rentNo){
		 rentalrepository.deleteById(rentNo);
	}
	@Transactional
	@PutMapping("/updateStatus")
    public ResponseEntity<Rental> updateRentStatus(@RequestParam("rentno")Long rentNo, @RequestParam("status") String rent_status) {
				System.out.println(rentNo+rent_status);
		    	Rental tan =rentalrepository.findByRentNo(rentNo);
		    	tan.setRent_status(rent_status);
		    	return  ResponseEntity.ok(tan);
	}
	@GetMapping("/checkRentalDate")
	public Boolean checkPerformDate(@RequestParam Long plantNo,   @RequestParam@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
		    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate){
		// endDate에 23:59를 추가하기 위해 LocalDateTime으로 변환 후 시간 설정
	    LocalDateTime endDateTime = endDate.atTime(23, 59);

	    Iterable<Performance> findPerforms = pfRepository.checkPerform(plantNo, startDate, endDateTime);
		Iterable<Rental> findRental = rentalrepository.checkRental(plantNo, startDate, endDateTime);

		if((findPerforms == null || !findPerforms.iterator().hasNext()) && (findRental == null || !findRental.iterator().hasNext())) {
			return true;
		}else {
			return false;
		}
	}

}
