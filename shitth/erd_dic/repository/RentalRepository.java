package com.packt.cantata.repository;

import com.packt.cantata.domain.Rental;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface RentalRepository  extends JpaRepository<Rental, Long>{
//	@Query(value="SELECT * FROM Perform_time WHERE pf_code = :pfCode AND pt_status=1 AND (pt_date BETWEEN :ptDate AND :ptEndtime) OR (pt_endtime BETWEEN  :ptDate AND :ptEndtime) ;", nativeQuery=true)
//	Iterable<Perform_time> findPfCodeAndPtDate(@Param("plantNo") Long plantNo, @Param("ptDate") String ptDate,  @Param("ptEndtime")String ptEndtime);
	@Query(value="SELECT * FROM Rental "
			+ "	WHERE plant_no= :plantNo "
			+ "	AND (rent_status = '결제대기' OR rent_status = '결제완료') "
			+ "	AND ((rent_start BETWEEN :startDate AND :endDate) "
			+ "	OR (rent_end BETWEEN :startDate AND :endDate)"
			+ "	OR (rent_start BETWEEN :startDate AND :endDate) "
			+ "	OR (rent_end BETWEEN :startDate AND :endDate));", nativeQuery=true)
	Iterable<Rental> checkRental(@Param("plantNo") Long plantNo, @Param("startDate") LocalDate startDate,  @Param("endDate")LocalDateTime endDate);
	Rental findByRentNo(Long rentno);
	@Query(value="SELECT * FROM Rental r WHERE r.cp_no IN (SELECT cp.cp_no FROM CORPORATION cp WHERE cp.id = :id);",nativeQuery=true)
	List<Rental> findByUserIdQuery(@Param("id")String id);
}
