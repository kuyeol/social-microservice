package org.acme.dto;

public class UserDto {

    private String username;


    public UserDto() {}

    public UserDto(String s) {
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        if(name == null && name.isEmpty()) {
            return;
        }
        this.username = name;
    }


}
