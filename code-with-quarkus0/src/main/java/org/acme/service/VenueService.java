package org.acme.service;

import jakarta.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import org.acme.entity.location.Venue;
import org.acme.mdoel.VenueModel;
import org.acme.repository.VenueRepository;
import org.springframework.stereotype.Service;

@Service
public class VenueService {


    @PersistenceContext
    private EntityManager entityManager;

    private final VenueRepository venueRepository;


    public VenueService(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }


    public void createVenue(Venue venue) {
        venueRepository.save(venue);
    }


public VenueModel createVenueModel(String venueName) {
        Venue venue = new Venue();
        venue.setVenueName(venueName);
        entityManager.persist(venue);
        entityManager.flush();
        return venue;

}


}
