package org.acme.client.ungorithm;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class Repesentaion {


    private String username;

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Long timestamp;

    public String address;

    //@Column(name = "usernapme")


    public String getUsername() {

        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }



}
