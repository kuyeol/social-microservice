package com.packt.cantata.repository;

import com.packt.cantata.domain.Plant;
import com.packt.cantata.domain.Rental;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PlantRepository extends CrudRepository<Plant, Long>{
	Plant findByPlantNo(Long plantNo);

	@Query(value="SELECT * FROM plant WHERE plant_use='공연장' AND plant_status= true;", nativeQuery=true)
	Iterable<Plant> filteredPlantToPerForm();

	@Query(value="SELECT * FROM plant WHERE plant_status= true;", nativeQuery=true)
	Iterable<Plant> filteredPlant();

	Plant findTopByOrderByPlantNoDesc();

	List<Rental> findByPlantNo(@Param("plantNo") Rental rental);
}
