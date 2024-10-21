package org.acme.controller;


import java.util.List;
import java.util.stream.Stream;
import org.acme.dto.TDTO;
import org.acme.entity.User;
import org.acme.service.Service;
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

    public VenueResource(Service service) {
        this.service = service;
    }

    //@GetMapping("/{id}")
    //public Venue getFooWithId(@PathVariable String id) throws Exception {
    //    return service.getVenueById(id);
    //}

    //@PostMapping("/users")
    //public ResponseEntity<Venue> createUser(@Valid @RequestBody Dto userDTO) throws URISyntaxException {
    //    HttpHeaders responseHeaders = new HttpHeaders();
    //    responseHeaders.set("Baeldung-Example-Header", "Value-ResponseEntityBuilderWithHttpHeaders");
    //    if (userDTO.getName() == null) {
    //        throw new RuntimeException();
    //        // Lowercase the user login before comparing with database
    //    } else {
    //        Venue newUser = service.registerUser(userDTO, userDTO.getName());
    //
    //        return ResponseEntity.created(new URI("/api/admin/users/" + newUser.getVenueName()))
    //            .headers(responseHeaders)
    //            .body(newUser);
    //    }
    //}
    //


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
        String N="string";
        return service.findByNameCol(name);

    }

    @GetMapping("getlist")
    public Stream<TDTO> getUserss( ) {

        return service.searchForUserStream();

    }


    @PostMapping("/user1")
    public List<User> getUserSS(TDTO dto) {

        return service.findByName(dto.getFirstName());

    }


}
