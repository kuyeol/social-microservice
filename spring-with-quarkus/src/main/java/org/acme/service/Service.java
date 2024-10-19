package org.acme.service;


import org.acme.dto.Dto;
import org.acme.entity.location.Venue;
import org.acme.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class Service {

    @Autowired
    private VenueRepository venueRepository;

    public Service(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }


    public Venue getVenueById(String id) {
        return venueRepository.findById(id);
    }

    public Venue registerUser(Dto userDTO, String id) {
        venueRepository.findById(userDTO.getSize()).ifPresent(existingUser -> {
            if (existingUser == null) {
                throw new RuntimeException();
            }
        });

        Venue venue = new Venue();
        venue.setId(id);
        venue.setVenueName(userDTO.getName());
        venue.setSize(userDTO.getSize());


        if (userDTO.getName() != null) {
            venue.setVenueName(userDTO.getName().toLowerCase());
        }
        //newUser.setImageUrl(userDTO.getImageUrl());
        //newUser.setLangKey(userDTO.getLangKey());
        //// new user is not active
        //newUser.setActivated(false);
        //// new user gets registration key
        //newUser.setActivationKey(RandomUtil.generateActivationKey());
        //Set<Authority> authorities = new HashSet<>();
        //authorityRepository.findById(AuthoritiesConstants.USER).ifPresent(authorities::add);
        //newUser.setAuthorities(authorities);
        venueRepository.save(venue);


        return venue;
    }


}
