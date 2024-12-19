package org.acme.repository;




public class Validator {


    private String name;


    public Validator(String name) throws ClassNotFoundException {

        if (name == null) {
            throw new IllegalArgumentException("User should not be null");
        }

        this.name = name;
    }

    public String name() {
        return name;
    }





}
