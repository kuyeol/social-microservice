package org.acme.controller;


import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import org.acme.dto.TDTO;
import org.acme.dto.UserDto;
import org.acme.entity.User;
import org.acme.mdoel.VenueModel;
import org.acme.service.Service;
import org.acme.service.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/users")
public class VenueResource {


    @Autowired
    private Service service;


    @Autowired
    private VenueService venueService;

    public VenueResource(Service service, VenueService venueService) {
        this.service = service;
        this.venueService = venueService;
    }




    @PostMapping("/create/venue")
    public VenueModel createVenue(VenueModel model) {

        venueService.createArtikel(model);
        return model;
    }



    @PostMapping("/create/user")
    public void create(TDTO dto) {

        service.create(dto);
    }


    @GetMapping("Base/{name}")
    public List<User> getUser(@PathVariable("name") String name) {

        return service.findByName(name);

    }


    @GetMapping("s/{name}")
    public User getUsers(@PathVariable("name") String name) {
        String N = "string";
        return service.findByNameCol(name);

    }

    @GetMapping("getlist/{name}")
    public Stream<TDTO> getUserss(@PathVariable("name") String name) {

        TDTO dto = new TDTO();
        dto.setFirstName(name);

        return service.searchForUserStream(dto, 0, 100);

    }


    @PostMapping("/user1")
    public List<User> getUserSS(TDTO dto) {

        return service.findByName(dto.getFirstName());

    }


    @GetMapping("projecyion")
    public UserDto FIND(String id) throws Exception {

        return service.projectionEntity(id);

    }


    @GetMapping("ALL/projecyion")
    public List<UserDto> allFIND() throws Exception {

        List<UserDto> dtos = service.getAllEntities();




        return dtos;

    }

}
