package com.packt.cantata.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.packt.cantata.domain.Brd_division;
import com.packt.cantata.repository.Brd_divisionRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/brd_divisions")
public class Brd_divisionController {
//	private static final Logger logger = LoggerFactory.getLogger(CantataApplication.class);

	@Autowired
	private Brd_divisionRepository BrdRepository;

//	@Autowired
//	public Brd_divisionController(Brd_divisionRepository brd_divisionRepository) {
//		this.BrdRepository = brd_divisionRepository;
//	}

	@GetMapping("/allBrd_divisions")
	public Iterable<Brd_division> getBrd_divisions(){
		return BrdRepository.findAll();
	}


    @GetMapping("/{brdNo}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getBrdName(@PathVariable Long brdNo) {
        Optional<Brd_division> brdDivision = BrdRepository.findById(brdNo);
        Map<String, Object> response = new HashMap<>();

        if (brdDivision.isPresent()) {
            response.put("brdName", brdDivision.get());
            return ResponseEntity.ok(response);
        } else {
            response.put("error", "Board not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

}
