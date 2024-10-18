package org.acme.controller;


import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.acme.entity.location.Venue;
import org.acme.service.VenueService;

@Path("VENUE")
public class VenueController {


    private VenueService venueService;


    @GET
    public void getVenue(Venue venue) {
        venueService.createVenue(venue);
    }

}
