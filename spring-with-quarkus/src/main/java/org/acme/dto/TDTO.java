package org.acme.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class TDTO {



    @JsonProperty("id")
    private final Long id;

    @JsonProperty("firstName")
    private final String firstName;

    @JsonProperty("LAST_NAME")
    private final String lastName;

   @JsonProperty("ADDRESS")
    private final String address;




    public TDTO(Long id) {
        this(id, null, null, null);
    }




    // Default constructor for Jackson deserialization


    public TDTO(Long id, String firstName, String lastName, String address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;

    }



    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }


    public Long getId() {
        return id;
    }
}
