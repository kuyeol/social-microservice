package com.packt.cantata.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.packt.cantata.CantataApplication;
import com.packt.cantata.domain.Plant;
import com.packt.cantata.repository.PlantRepository;
import com.packt.cantata.dto.PlantDto;
import com.packt.cantata.service.FileService;

@RestController
//@RequiredArgsConstructor
@CrossOrigin(origins="http://localhost:3000")
@RequestMapping("/plants")
public class PlantController {
	private static final Logger logger = LoggerFactory.getLogger(CantataApplication.class);

	@Autowired
	private PlantRepository plantrepository;

	@Autowired
	private FileService fileService;

	@GetMapping("/getplant")
	public Iterable<Plant> getplant(){
		return plantrepository.findAll();
	}

	@GetMapping("/lastPlCode")
	public ResponseEntity<Long> getLastPostNum() {
		Plant lastPlantNum = plantrepository.findTopByOrderByPlantNoDesc();
        logger.info("Last Plant No: " + lastPlantNum);
        if (lastPlantNum != null) {
            return ResponseEntity.ok().body(lastPlantNum.getPlantNo());
        }
        return ResponseEntity.ok().body(0L);  // 0을 반환하거나 다른 기본값을 반환할 수 있습니다.
	}

//	@RequestMapping(value="/plantapp", method = RequestMethod.POST)
//	public ResponseEntity<Plant> postplant(@RequestBody Plant plant ){
//		Plant pav = plantrepository.save(plant);
//		return ResponseEntity.status(HttpStatus.OK).body(pav);
//	}
	@PostMapping("/plantapp")
	public ResponseEntity<Plant> postplant(
										@RequestPart("plantMainimgFile") MultipartFile plantMainImgFile,
									    @RequestPart("plantSubimg1File") MultipartFile plantSubImg1File,
									    @RequestPart("plantSubimg2File") MultipartFile plantSubImg2File,
										@RequestPart("plant") String plantJson){
	ObjectMapper objectMapper = new ObjectMapper();
    PlantDto plant = null;
	try {
		plant = objectMapper.readValue(plantJson, PlantDto.class);
	} catch (JsonProcessingException e1) {
		e1.printStackTrace();
	}

	Plant newPlant = new Plant();

	newPlant.setPlantName(plant.getPlantName());
	newPlant.setPlantUse(plant.getPlantUse());
	newPlant.setPlantDetail(plant.getPlantDetail());
	newPlant.setCapacity(plant.getCapacity());
//		newPerform.setPfEximg(postData.getPfEximg());
//	newPlant.setPfStart(plant.getPlantMainimg());
//	newPlant.setPfEnd(plant.getPlantSubimg1());
//	newPlant.setPfRuntime(plant.getPlantSubimg2());
	newPlant.setPlantCharge(plant.getPlantCharge());
	newPlant.setPlantSub(plant.getPlantSub());
	newPlant.setPlantStatus(plant.getPlantStatus());
	newPlant.setFloor(plant.getFloor());


	//첨부파일 처리
	ResponseEntity<Long> number = getLastPostNum();
	Long incrementedNum;
	if (number.getStatusCode().is2xxSuccessful() && number.getBody() != null) {
	    incrementedNum = number.getBody() + 1;
	} else {
		incrementedNum = (long) 1;
	}

	try {
		newPlant.setPlantMainimg(fileService.attachUploadAndGetUri(plantMainImgFile, "plant", incrementedNum));
		newPlant.setPlantSubimg1(fileService.attachUploadAndGetUri(plantSubImg1File, "plant", incrementedNum));
		newPlant.setPlantSubimg2(fileService.attachUploadAndGetUri(plantSubImg2File, "plant", incrementedNum));
	} catch (IOException e) {
		e.printStackTrace();
	}

	//새로운장소 등록
	Plant pav = plantrepository.save(newPlant);
	return ResponseEntity.ok(pav);
	}

	@GetMapping("/filteredPlantToPerForm")
	public Iterable<Plant> filteredPlantToPerForm(){
		return plantrepository.filteredPlantToPerForm();
	}

	@GetMapping("/filteredPlant")
	public Iterable<Plant> filteredPlant(){
		return plantrepository.filteredPlant();
	}
	@GetMapping("/selectedPlant/{plantNo}")
	public Plant getSelectedPlant(Plant plant) {
		Plant returnPlant = plantrepository.findByPlantNo(plant.getPlantNo());
		return returnPlant;
	}
}
