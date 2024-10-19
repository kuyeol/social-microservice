package org.acme.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class TDTO {


    @JsonProperty("FIRST_NAME")
    private final String firstName;

   // @JsonProperty("LAST_NAME")
    private final String lastName;

 //   @JsonProperty("ADDRESS")
    private final String address;




    public TDTO() {
        this(null, null, null);
    }




    // Default constructor for Jackson deserialization


    public TDTO(String firstName, String lastName, String address) {
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



}
