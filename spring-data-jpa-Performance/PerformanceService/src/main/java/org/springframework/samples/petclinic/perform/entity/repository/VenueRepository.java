package org.springframework.samples.petclinic.perform.entity.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.perform.entity.location.Venue;

public interface VenueRepository extends Repository<Venue, Integer>, JpaSpecificationExecutor<Venue> {
}