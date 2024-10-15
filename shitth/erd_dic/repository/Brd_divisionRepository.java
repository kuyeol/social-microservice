package com.packt.cantata.repository;

import com.packt.cantata.domain.Brd_division;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface Brd_divisionRepository extends JpaRepository<Brd_division, Long> {

	List<Brd_division> findByBrdName(@Param("brdName") String brdName);
	Brd_division findByBrdNo(@Param("brdNo") Long brdNo);
}
