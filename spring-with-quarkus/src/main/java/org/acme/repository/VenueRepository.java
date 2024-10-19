package org.acme.repository;


import org.acme.entity.location.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VenueRepository extends JpaRepository<Venue, Long> {

    Venue findById(String name);

}