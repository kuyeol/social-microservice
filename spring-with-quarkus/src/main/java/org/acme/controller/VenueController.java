package org.acme.controller;


import jakarta.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import org.acme.dto.Dto;
import org.acme.entity.location.Venue;
import org.acme.repository.VenueRepository;
import org.acme.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/users")
public class VenueController {


    @Autowired
    private VenueRepository venueRepository;
    @Autowired
    private Service service;

    public VenueController(VenueRepository venueRepository, Service service) {
        this.venueRepository = venueRepository;
        this.service = service;
    }

    @GetMapping("/{id}")
    public Venue getFooWithId(@PathVariable String id) throws Exception {
        return service.getVenueById(id);
    }

    @PostMapping("/users")
    public ResponseEntity<Venue> createUser(@Valid @RequestBody Dto userDTO) throws URISyntaxException {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Baeldung-Example-Header", "Value-ResponseEntityBuilderWithHttpHeaders");
        if (userDTO.getName() == null) {
            throw new RuntimeException();
            // Lowercase the user login before comparing with database
        } else {
            Venue newUser = service.registerUser(userDTO, userDTO.getName());

            return ResponseEntity.created(new URI("/api/admin/users/" + newUser.getVenueName()))
                .headers(responseHeaders)
                .body(newUser);
        }
    }


}
